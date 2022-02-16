package app.tedu.api_generator.generator

import app.tedu.api_generator.lexer.ApiAccessObject
import app.tedu.api_generator.model.files.ApiFunWrapper
import app.tedu.api_generator.model.files.GeneratedClass

class ApiGenerator(
    private val classExt: String,
    private val commonPackageName: String,
    private val apiAccessObject: ApiAccessObject
) {

    fun generate(list: List<ApiFunWrapper>): List<GeneratedClass> {
        return list.groupBy { it.method.tags }.map {
            generate(it.key, it.value)
        }
    }

    fun generate(tags: List<String>, w: List<ApiFunWrapper>): GeneratedClass {

        val tag = if (tags.size == 1) {
            tags.first()
        } else {
            commonPackageName
        }

        return GeneratedClass(
            apiAccessObject.objectName(w),
            tag,
            apiAccessObject.wrappers(w),
            classExt
        )
    }

}