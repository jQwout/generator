package app.tedu.api_generator.model.domain

import app.tedu.api_generator.model.files.EnumWrapper
import app.tedu.api_generator.model.files.ModelWrapper
import app.tedu.api_generator.model.files.RequestParamWrapper
import app.tedu.api_generator.model.files.ResponseWrapper

class SchemeWrapper(
    val paramsWrapper: List<RequestParamWrapper>,
    val responseWrapper: List<ResponseWrapper>,
    val modelWrapper: List<ModelWrapper>,
    val enumWrapper: List<EnumWrapper>
)