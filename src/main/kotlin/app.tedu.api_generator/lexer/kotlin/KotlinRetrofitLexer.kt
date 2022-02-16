package app.tedu.api_generator.lexer.kotlin

import app.tedu.api_generator.lexer.ApiAccessObject
import app.tedu.api_generator.model.files.ApiFunWrapper
import app.tedu.api_generator.model.pojo.Ref

class KotlinRetrofitLexer(val methodLexer: KotlinMethodLexer) : ApiAccessObject {

    override fun objectName(list: List<ApiFunWrapper>): String {
        return list.first().method.tags.joinToString()
    }

    override fun wrappers(list: List<ApiFunWrapper>): String {
        checkTagsEq(list)

        return kotlinClass(
            imports = list.imports().toList(),
            classContent = apiInterface(list) {
                methods()
            }
        )
    }

    private fun checkTagsEq(list: List<ApiFunWrapper>) {
        check(list.all { it.method.tags == list.first().method.tags })
    }

    private fun List<ApiFunWrapper>.imports(): Set<String> {
        val responses = mapNotNull { it.method.responses.get("200")?.schema?.getClassReferenceOrNull }.map {
                convertToImportPackage(it)
            }

        val params = flatMap { w ->
            w.method.parameters
                ?.mapNotNull { it.schema?.className ?: it.items?.getClassReferenceOrNull }
                ?.map { convertToImportPackage(it) } ?: emptyList()
        }

        return (responses + params).toSet()
    }

    private fun apiInterface(list: List<ApiFunWrapper>, defineMethods: List<ApiFunWrapper>.() -> String): String {
        return buildString {
            append("interface ${objectName(list)}")
            appendLine(" {")
            appendLine(defineMethods(list))
            appendLine("}")
        }
    }

    private fun List<ApiFunWrapper>.methods(): String {
        return joinToString("\n\n") {
            methodLexer.method(it)
        }
    }

    private fun convertToImportPackage(it: String): String {
        return Ref.getCommonOrTag(it) + "." + it
    }
}
