package app.tedu.api_generator.generator

import app.tedu.api_generator.lexer.MethodLexer
import app.tedu.api_generator.lexer.ModelLexer
import app.tedu.api_generator.model.files.ModelWrapper
import app.tedu.api_generator.model.files.RequestParamWrapper
import app.tedu.api_generator.model.files.ResponseWrapper
import app.tedu.api_generator.model.pojo.Ref

class PackageGenerator(
    private val methodLexer: MethodLexer,
    private val modelLexer: ModelLexer
) {

    fun definePck(
        models: List<ModelWrapper>,
        responses: List<ResponseWrapper>,
        params: List<RequestParamWrapper>
    ) {
        responses.forEach { rw ->
            rw.methodResponses.values
                .mapNotNull { it.schema?.getClassReferenceOrNull }
                .flatMap { refName ->
                    models.filter { it.name == refName }
                }
                .forEach { mw ->
                    Ref.add(mw.name, rw.tags)

                    mw.model.properties.values.forEach {
                        if (it.getClassReferenceOrNull != null) {
                            Ref.add(it.getClassReference, rw.tags)
                        }

                        if (it.items?.getClassReferenceOrNull != null) {
                            Ref.add(it.items.getClassReference, rw.tags)
                        }
                    }

                }
        }

        params.mapNotNull { w ->
            w.param.schema?.className?.apply {
                Ref.add(this, w.tags)
            }
        }.flatMap { refName ->
            models.filter { it.name == refName }
        }.forEach { mw ->
            val tags = Ref.getTags(mw.name).toList()
            Ref.add(mw.name, tags)

            mw.model.properties.values.forEach {
                if (it.getClassReferenceOrNull != null) {
                    Ref.add(it.getClassReference, tags)
                }

                if (it.items?.getClassReferenceOrNull != null) {
                    Ref.add(it.items.getClassReference, tags)
                }
            }

        }

        models.forEach { mw ->
            val tags = Ref.getTags(mw.name).toList()
            for (it in mw.model.properties.values) {
                if (it.getClassReferenceOrNull != null) {
                    Ref.add(it.getClassReference, tags)
                }

                if (it.items?.getClassReferenceOrNull != null) {
                    Ref.add(it.items.getClassReference, tags)
                }
            }
        }


        models.forEach {
            val set = Ref.getTags(it.name)
            if (set.isEmpty()) {
                println(it.name)
            }
        }

    }
}