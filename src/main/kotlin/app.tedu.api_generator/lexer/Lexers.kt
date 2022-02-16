package app.tedu.api_generator.lexer

import app.tedu.api_generator.model.files.ApiFunWrapper
import app.tedu.api_generator.model.files.EnumWrapper
import app.tedu.api_generator.model.files.ModelWrapper
import app.tedu.api_generator.model.pojo.Method
import app.tedu.api_generator.model.pojo.Property
import app.tedu.api_generator.model.pojo.Type

interface ApiAccessObject {
    fun objectName(list: List<ApiFunWrapper>): String
    fun wrappers(list: List<ApiFunWrapper>): String
}

interface MethodLexer {
    fun method(apiFunWrapper: ApiFunWrapper): String
}

interface ModelLexer {
    fun model(model: ModelWrapper): String
}

interface TypeLexer {

    fun type(property: Property): String

    fun type(type: Type, format: String?, items: Property?): String

    fun isModel(property: Property): Boolean
}

interface EnumLexer {

    fun enum(enumWrapper: EnumWrapper): String
}


