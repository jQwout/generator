package app.tedu.api_generator.lexer.kotlin

import app.tedu.api_generator.Cfg
import app.tedu.api_generator.LangCfg
import app.tedu.api_generator.dsl.*
import app.tedu.api_generator.lexer.ModelLexer
import app.tedu.api_generator.lexer.TypeLexer
import app.tedu.api_generator.model.files.ModelWrapper
import app.tedu.api_generator.model.pojo.Model
import app.tedu.api_generator.model.pojo.Property
import app.tedu.api_generator.model.pojo.Ref


class KotlinModelLexer(
    private val cfg: Cfg,
    private val langCfg: LangCfg,
    private val typeLexer: TypeLexer
) : ModelLexer {

    private val dataOrSimpleClass = if (langCfg.dataClass == true) {
        "data"
    } else {
        null
    }

    private val serializableOrNull = if (langCfg.serializable == true) {
        "Serializable"
    } else {
        null
    }

    private val variable: String = if (langCfg.mutables == true) "var" else "val"

    override fun model(model: ModelWrapper): String {
        return kotlinClass(
            imports = model.getImports(cfg.targetPkg),
            comments = listOf(model.getComment()),
            classContent = dataOrSimpleClass withName clazz(model) {

                defineProperties {
                    variable withName it.key define type(it)
                }

            } extends serializableOrNull
        )
    }

    private fun clazz(
        model: ModelWrapper,
        fieldsBlock: Model.() -> String
    ): String {
        return "class ${model.name}(\n" +
                fieldsBlock(model.model) +
                "\n)"
    }

    private fun Model.type(it: Map.Entry<String, Property>): String {
        return typeLexer.type(it.value).required(this, it.key)
    }

    private fun ModelWrapper.getImports(targetPkg: String) = model.properties.values
        .mapNotNull {
            it.getClassReferenceOrNull ?: it.items?.getClassReferenceOrNull
        }
        .map { targetPkg + "." + Ref.getCommonOrTag(it) + "." + it }
        .toSet()

    private fun ModelWrapper.getComment() = Ref.getTags(this.name).joinToString()
}
