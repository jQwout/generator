package app.tedu.api_generator.parser

import app.tedu.api_generator.model.pojo.MethodParam
import app.tedu.api_generator.model.pojo.Type
import app.tedu.api_generator.model.pojo.name

class MethodParamParser {

    /**
     *   @Query("StudySessionId") studySessionId: Long,
     *   @Body zoomSettings: ZoomSettings
     */

    fun parse(param: MethodParam): String {
        val place = if (param.place == "query") {
            "@Query(\"${param.name}\") ${param.name}"
        } else {
            "@Body ${param.name}"
        }

        val type = parseType(param)
        val nil = if (param.required) "" else "?"

        return buildString {
            append(place)
            append(": ")
            append(type)
            append(nil)
        }
    }


    private fun parseType(param: MethodParam): String {
        if (param.schema != null) {
            return param.schema.className
        }

        return when (param.type) {
            Type.STRING -> {
                if (param.format == "date-time") {
                    "Date"
                } else {
                    param.type.name()
                }
            }
            Type.INT, Type.BOOLEAN, Type.NUMBER -> {
                param.type.name()
            }
            Type.ARRAY -> {
                val items = checkNotNull(param.items)

                if (items.reference != null) {
                    "List<${items.getClassReference}>"
                } else {
                    val type = checkNotNull(items.type)
                    "List<${items.type.name()}>"
                }

            }
            else -> throw IllegalArgumentException("unknow type ${param.type}")
        }
    }
}