package app.tedu.api_generator.lexer.kotlin

import app.tedu.api_generator.lexer.EnumLexer
import app.tedu.api_generator.model.files.EnumWrapper

class KotlinEnumLexer : EnumLexer {
    override fun enum(enumWrapper: EnumWrapper): String {
        return enumClass(enumWrapper) {
            defineMembers { member -> member }
        }
    }

    private fun enumClass(wrapper: EnumWrapper, block: EnumWrapper.() -> String): String {
        return "enum class ${wrapper.name} {\n" +
                block(wrapper) +
                "\n}"
    }

    private fun EnumWrapper.defineMembers(block: (String) -> String): String {
        return values.joinToString(",\n") {
            "   " + block(it)
        }
    }
}