package app.tedu.api_generator.parser

import app.tedu.api_generator.model.pojo.Method

class MethodParses() {

    val paramParser = MethodParamParser()

    /**
     *  @HTTP(method = "", path = "", body = false)
     *  suspend fun pathCamelCase
     *
     */

    fun parse(
        path: String,
        httpMethod: String,
        method: Method
    ): String {
        val response = method.responses.getValue("200")
        val params = method.parameters?.map {
            paramParser.parse(it)
        }
        val hasBody = method.parameters?.any {
            it.place == "body"
        }

        return buildString {
            appendLine("@HTTP(method = \"${httpMethod.toUpperCase()}\", path = \"$path\", body = $hasBody)")
            appendLine("suspend %path(")

            params?.forEachIndexed { index, paramStr ->
                appendLine(paramStr)
                if (index == method.responses.size - 1) {
                    append(",")
                }
            }

            appendLine(")")
            appendLine(": ${response}")
        }
    }

}