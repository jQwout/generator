package app.tedu.api_generator.model.pojo

class Scheme(
    val paths: Map<String, Map<String, Method>>,
    val definitions: Map<String, Model>
)