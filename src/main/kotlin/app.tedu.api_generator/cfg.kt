package app.tedu.api_generator

class Cfg(

    val schemePath: String,

    val commonPkgName: String?,

    val lang: LangCfg,

    val apiClientLib: String?
) {
    val commonPackage: String get() = commonPkgName ?: "common"
}


open class LangCfg(
    val name: String,
    val ext: String,

    val mutables: Boolean?,
    val nullability: Boolean?,
    val dataClass: Boolean?,
    val serializable: Boolean?
)

class KotlinLangCfg() : LangCfg(
    name = "Kotlin",
    ext = ".kt",
    mutables = false,
    nullability = true,
    dataClass = false,
    serializable = false
)
