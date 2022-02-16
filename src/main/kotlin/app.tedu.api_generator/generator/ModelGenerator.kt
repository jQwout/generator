package app.tedu.api_generator.generator

import app.tedu.api_generator.lexer.EnumLexer
import app.tedu.api_generator.lexer.ModelLexer
import app.tedu.api_generator.model.files.EnumWrapper
import app.tedu.api_generator.model.files.GeneratedClass
import app.tedu.api_generator.model.files.ModelWrapper
import app.tedu.api_generator.model.pojo.Ref

class ModelGenerator(
    private val classExt: String,
    private val commonPackageName: String,
    private val modelLexer: ModelLexer,
    private val enumLexer: EnumLexer
) {

    fun generate(modelWrapper: ModelWrapper): GeneratedClass {
        return GeneratedClass(
            modelWrapper.name,
            resolveTags(modelWrapper.name),
            modelLexer.model(modelWrapper),
            classExt
        )
    }

    fun generate(modelWrapper: EnumWrapper): GeneratedClass {
        return GeneratedClass(
            modelWrapper.name,
            resolveTags(modelWrapper.name),
            enumLexer.enum(modelWrapper),
            classExt
        )
    }

    private fun resolveTags(modelName: String): String {
        return Ref.getTags(modelName).takeIf { it.size == 1 }?.first() ?: commonPackageName
    }
}