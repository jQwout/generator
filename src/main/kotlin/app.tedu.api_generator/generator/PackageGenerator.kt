package app.tedu.api_generator.generator

import app.tedu.api_generator.model.files.ModelWrapper
import app.tedu.api_generator.model.files.RequestParamWrapper
import app.tedu.api_generator.model.files.ResponseWrapper
import app.tedu.api_generator.model.pojo.Property
import app.tedu.api_generator.model.pojo.Ref

class PackageGenerator() {

    fun definePck(
        models: List<ModelWrapper>,
        responses: List<ResponseWrapper>,
        params: List<RequestParamWrapper>
    ) {
        responses.forEach { rw ->
            rw.methodResponses.values
                .mapNotNull { it.schema?.getClassReferenceOrNull }
                .findFromModels(models)
                .forEach { mw ->
                    Ref.add(mw.name, rw.tags)
                    mw.model.properties.values.addTagsToRefs(rw.tags)
                }
        }

        params.mapNotNull { w ->
            w.param.schema?.className?.apply {
                Ref.add(this, w.tags)
            }
        }
            .findFromModels(models)
            .forEach { mw ->
                val tags = Ref.getTags(mw.name).toList()
                Ref.add(mw.name, tags)
                mw.model.properties.values.addTagsToRefs(tags)
            }

        models.forEach { mw ->
            mw.model.properties.values.addTagsToRefs(Ref.getTags(mw.name).toList())
        }

        models.check()
    }

    private fun List<String>.findFromModels(models: List<ModelWrapper>) = map {
        models.findByName(it)
    }

    private fun List<ModelWrapper>.findByName(name: String) = first { it.name == name }

    private fun Collection<Property>.addTagsToRefs(tags: List<String>) {
        forEach {
            if (it.getClassReferenceOrNull != null) {
                Ref.add(it.getClassReference, tags)
            }

            if (it.items?.getClassReferenceOrNull != null) {
                Ref.add(it.items.getClassReference, tags)
            }
        }
    }

    private fun List<ModelWrapper>.check() {
        forEach {
            val set = Ref.getTags(it.name)
            if (set.isEmpty())
                println(it.name)
        }
    }
}