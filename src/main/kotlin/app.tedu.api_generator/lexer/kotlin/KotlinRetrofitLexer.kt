package app.tedu.api_generator.lexer.kotlin

import app.tedu.api_generator.Cfg
import app.tedu.api_generator.lexer.ApiAccessObject
import app.tedu.api_generator.model.files.ApiFunWrapper
import app.tedu.api_generator.model.pojo.Ref.Companion.getImportTag
import app.tedu.api_generator.model.pojo.hasMultipart

class KotlinRetrofitLexer(
    val methodLexer: KotlinMethodLexer,
    val cfg: Cfg
) : ApiAccessObject {

    override fun objectName(list: List<ApiFunWrapper>): String {
        return list.first().method.tags.joinToString() + "ApiService"
    }

    override fun wrappers(list: List<ApiFunWrapper>): String {
        checkTagsEq(list)

        return kotlinClass(
            imports = list.imports(),
            classContent = apiInterface(list) {
                methods()
            }
        )
    }

    private fun checkTagsEq(list: List<ApiFunWrapper>) {
        check(list.all { it.method.tags == list.first().method.tags })
    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun List<ApiFunWrapper>.imports(): Set<String> {
        val responses = mapNotNull { it.method.responses.get("200")?.schema?.getClassReferenceOrNull }.map {
            getImportTag(it, cfg.targetPkg)
        }

        val params = flatMap { w ->
            w.method.parameters
                ?.mapNotNull { it.schema?.className ?: it.items?.getClassReferenceOrNull }
                ?.map { getImportTag(it, cfg.targetPkg) } ?: emptyList()
        }

        val retrofit = buildSet<String> {
            add("retrofit2.http.*")

            if (this@imports.any { it.method.hasMultipart }) {
                add("okhttp3.MultipartBody")
            }
        }

        return (responses + params + retrofit).toSet()
    }

    private fun apiInterface(list: List<ApiFunWrapper>, defineMethods: List<ApiFunWrapper>.() -> String): String {
        return buildString {
            append("interface ${objectName(list)} ")
            appendLine("{")
            appendLine(defineMethods(list))
            appendLine("}")
        }
    }

    private fun List<ApiFunWrapper>.methods(): String {
        return joinToString("\n\n") {
            methodLexer.method(it)
        }
    }
}
