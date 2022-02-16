package app.tedu.api_generator.model.files

import app.tedu.api_generator.model.pojo.*

class ApiFunWrapper(
    val path: String,

    val httpMethod: String,

    val method: Method
)

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