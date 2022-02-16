package app.tedu.api_generator.lexer

import app.tedu.api_generator.model.files.EnumWrapper
import app.tedu.api_generator.model.files.ModelWrapper
import app.tedu.api_generator.model.files.ResponseWrapper
import app.tedu.api_generator.model.pojo.Method
import app.tedu.api_generator.model.pojo.Property

interface MethodLexer {
    fun defineMethodScope(method: ResponseWrapper)
}

interface ModelLexer {
    fun model(model: ModelWrapper): String
}

interface TypeLexer {

    fun type(property: Property): String

    fun isModel(property: Property) : Boolean
}

interface EnumLexer {

    fun enum(enumWrapper: EnumWrapper): String
}
