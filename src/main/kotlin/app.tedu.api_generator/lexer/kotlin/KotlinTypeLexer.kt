package app.tedu.api_generator.lexer.kotlin

import app.tedu.api_generator.lexer.TypeLexer
import app.tedu.api_generator.model.pojo.Property
import app.tedu.api_generator.model.pojo.Ref
import app.tedu.api_generator.model.pojo.Type

class KotlinTypeLexer : TypeLexer {

    override fun isModel(property: Property): Boolean {
        return property.type === Type.OBJECT
    }

    override fun type(property: Property): String {
        if (property.reference != null) {
            return property.getClassReference
        }

        return when (property.type) {
            Type.STRING -> {
                if (property.format == "date-time") {
                    "Date"
                } else {
                    property.type.name()
                }
            }
            Type.INT, Type.BOOLEAN, Type.NUMBER -> {
                property.type.name()
            }
            Type.ARRAY -> {
                val items = checkNotNull(property.items)

                if (items.reference != null) {
                    "List<${items.getClassReference}>"
                } else {
                    val type = checkNotNull(items.type)
                    "List<${type.name()}>"
                }
            }
            else -> throw IllegalArgumentException("unknown type ${property.type}")
        }
    }

    fun Type.name() = when (this) {
        Type.STRING -> "String"
        Type.INT -> "Integer"
        Type.BOOLEAN -> "Boolean"
        Type.NUMBER -> "Number"
        else -> ""
    }
}