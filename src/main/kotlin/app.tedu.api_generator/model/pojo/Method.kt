package app.tedu.api_generator.model.pojo

import com.google.gson.annotations.SerializedName

class Method(
    val tags: List<String>,

    val consumes: List<String>?,

    val parameters: List<MethodParam>?,

    val responses: Map<String, MethodResponse>
) {
    val success: MethodResponse?
        get() {
            return responses.getSuccess()
        }
}

fun Map<String, MethodResponse>.getSuccess() = get("200")

class MethodResponse(
    val schema: Property?
)

class MethodParam(

    @SerializedName("in")
    val place: String,

    val name: String,

    val required: Boolean = false,

    val type: Type?,

    val format: String?,

    val schema: MethodSchema?,

    val items: Property?
)


class MethodSchema(
    @SerializedName("\$ref")
    val ref: String
) {
    val className: String
        get() = Ref(ref).className
}
