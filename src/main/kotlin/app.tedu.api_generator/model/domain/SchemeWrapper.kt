package app.tedu.api_generator.model.domain

import app.tedu.api_generator.model.files.*

class SchemeWrapper(
    val apiFunWrapper: List<ApiFunWrapper>,
    val paramsWrapper: List<RequestParamWrapper>,
    val responseWrapper: List<ResponseWrapper>,
    val modelWrapper: List<ModelWrapper>,
    val enumWrapper: List<EnumWrapper>
)