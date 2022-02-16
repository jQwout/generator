package app.tedu.api_generator.model.files

import app.tedu.api_generator.model.pojo.*
import com.android.utils.usLocaleCapitalize

class ApiFunWrapper(
    val path: String,

    val httpMethod: String,

    val method: Method
) {
    val name get() = httpMethod + path.split("/").last { it.all { it.isLetter() } }.usLocaleCapitalize()
}

class RequestParamWrapper(
    val tags: List<String>,

    val param: MethodParam
)

class ResponseWrapper(
    val tags: List<String>,

    val methodResponses: Map<String, MethodResponse>
)

class ModelWrapper(

    val name: String,

    val model: Model,

    val tag: Set<String>
)

class EnumWrapper(
    val name: String,

    val values: Set<String>
)