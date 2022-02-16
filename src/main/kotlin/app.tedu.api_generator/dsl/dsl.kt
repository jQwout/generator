package app.tedu.api_generator.dsl

import app.tedu.api_generator.model.pojo.Model
import app.tedu.api_generator.model.pojo.Property


fun String.required(model: Model, name: String): String {
    return this + " " + if (model.required?.contains(name) == true) "" else "?"
}

fun String.required(isRequired: Boolean): String {
    return this + " " + if (isRequired) "" else "?"
}

fun Model.defineProperties(
    block: (Map.Entry<String, Property>) -> String
): String {
    return properties.map { "\t" + block(it) }.joinToString(",\n")
}

infix fun String.newLine(str: String?) = "$this\n"

infix fun String.extends(str: String?) = if (str == null) this else "$this : $str"

infix fun String.define(str: String) = "$this: $str"

infix fun String?.withName(name: String) = if (this == null) name else "$this $name"

const val one: Int = 1

const val two: Int = 2

infix fun Int.tab(name: String) = List(this) { '\t' }.joinToString()

