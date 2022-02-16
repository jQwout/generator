package app.tedu.api_generator.lexer.kotlin

import app.tedu.api_generator.lexer.MethodLexer
import app.tedu.api_generator.lexer.TypeLexer
import app.tedu.api_generator.model.files.ApiFunWrapper
import app.tedu.api_generator.model.files.ResponseWrapper
import app.tedu.api_generator.model.pojo.*
import org.gradle.plugin.devel.tasks.internal.ValidateAction
import java.lang.StringBuilder

class KotlinMethodLexer(
    private val typeLexer: TypeLexer
) : MethodLexer {

    private val funPrefix: String get() = "suspend"

    override fun method(apiFunWrapper: ApiFunWrapper): String {
        return buildString {
            header(
                method = apiFunWrapper.httpMethod,
                path = apiFunWrapper.path,
                hasBody = apiFunWrapper.method.parameters?.any { it.place == "body" } ?: false
            )
            contentHeader(method = apiFunWrapper.method)
            func(prefix = funPrefix, name = apiFunWrapper.name)
            body(apiFunWrapper.method)
            returnValue(apiFunWrapper.method)
        }
    }

    private fun StringBuilder.header(method: String, path: String, hasBody: Boolean) {
        appendLine("\t@HTTP(method = \"$method\", path = \"$path\", hasBody = $hasBody)")
    }

    private fun StringBuilder.contentHeader(method: Method) {
        if (method.hasUrlEncoded)
            appendLine("\t@FormUrlEncoded")
        else if (method.hasMultipart)
            appendLine("\t@Multipart")
    }

    private fun StringBuilder.func(prefix: String?, name: String) {
        append("\t$prefix fun $name")
    }

    private fun StringBuilder.body(method: Method) {
        val params = method.parameters?.mapNotNull {
            param(it, method.hasMultipart, method.hasUrlEncoded)
        }

        if (params.isNullOrEmpty()) {
            append("()")
        } else {
            append("(\n")
            append(params?.joinToString(",\n"))
            append("\n\t)")
        }
    }

    private fun StringBuilder.returnValue(method: Method) {
        val sch = method.responses.get("200")?.schema
        if (sch != null) {
            append(": ")
            append(typeLexer.type(sch))
        }
    }

    private fun param(param: MethodParam, isMultipart: Boolean, isFormUrlEncoded: Boolean): String? {
        val nil = if (param.required) "" else "?"
        val ann = when (param.place) {
            "body" -> "@Body"
            "query" -> "@Query(\"${param.name}\")"
            "path" -> "@Path(\"${param.name}\")"
            "formData" -> when {
                isFormUrlEncoded -> "@Field(\"${param.name}\")"
                isMultipart -> "@Part"
                else -> IllegalArgumentException("not found type to ${param.place}")
            }
            else -> throw IllegalArgumentException("not found annotation to ${param.place}")
        }

        if (param.schema?.className != null) {
            return "\t\t$ann ${param.name}: ${param.schema.className}$nil"
        }

        if (param.type == null) return null

        val type = if (isMultipart) "MultipartBody.Part" else typeLexer.type(param.type, param.format, param.items)

        return "\t\t$ann ${param.name}: $type$nil"
    }
}