package app.tedu.api_generator.parser

import app.tedu.api_generator.model.domain.SchemeWrapper
import app.tedu.api_generator.model.files.*
import app.tedu.api_generator.model.pojo.Ref
import app.tedu.api_generator.model.pojo.Scheme

class SchemeParser() {

    fun parse(scheme: Scheme): SchemeWrapper {
        val funs = scheme.paths.flatMap { kv ->

            kv.value.map {
                ApiFunWrapper(
                    path = kv.key,
                    httpMethod = it.key,
                    method = it.value
                )
            }
        }

        val params = funs.flatMap {
            it.method.parameters?.map { param ->
                RequestParamWrapper(it.method.tags, param)
            } ?: emptyList()
        }



        val responses = funs.mapNotNull {
            if (it.method.success != null)
                ResponseWrapper(
                    it.method.tags,
                    it.method.responses
                )
            else
                null
        }

        return SchemeWrapper(
            params,
            responses,
            scheme.definitions.filter { it.value.enum == null }
                .map { ModelWrapper(it.key, it.value, Ref.getTags(it.key)) },
            scheme.definitions.filter { it.value.enum != null }
                .map { EnumWrapper(it.key, it.value.enum?.toSet()!!) },
        )
    }
}