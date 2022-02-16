package app.tedu.api_generator.model.pojo

import com.google.gson.annotations.SerializedName

enum class Type {

    @SerializedName("string")
    STRING,

    @SerializedName("integer")
    INT,

    @SerializedName("object")
    OBJECT,

    @SerializedName("array")
    ARRAY,

    @SerializedName("boolean")
    BOOLEAN,

    @SerializedName("number")
    NUMBER
}

//todo use only lexer
fun Type.name() = when (this) {
    Type.STRING -> "String"
    Type.INT -> "Integer"
    Type.BOOLEAN -> "Boolean"
    Type.NUMBER -> "Number"
    else -> ""
}