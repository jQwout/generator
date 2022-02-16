package app.tedu.api_generator.model.pojo

import com.google.gson.annotations.SerializedName
import java.lang.IllegalStateException

class Model(

    val required: Array<String>?,

    val properties: Map<String, Property>,

    val enum: List<String>?,

    val type: Type,
)

class Property(

    val format: String?,

    val type: Type?,

    val items: Property?,

    @SerializedName("\$ref")
    val reference: String?
) {
    val getClassReference: String
        get() {
            return if (reference == null) {
                throw IllegalStateException()
            } else {
                Ref(reference).className
            }
        }

    val getClassReferenceOrNull: String?
        get() {
            return reference?.let { Ref(it) }?.className
        }
}

class Ref(
    private val path: String
) {

    val className: String
        get() {
            val last = path.split("/").lastOrNull()
            return checkNotNull(last) {
                "cannot parse path :$path"
            }
        }

    companion object {

        private val refModelCounter = mutableMapOf<String, Set<String>>()

        fun add(ref: Ref, tags: List<String>) {
            val set = refModelCounter.getOrDefault(ref.className, emptySet())
            val newSet = mutableSetOf<String>()
            newSet.addAll(tags)
            newSet.addAll(set)

            refModelCounter.put(ref.className, newSet)
        }

        fun add(name: String, tags: List<String>) {
            val set = refModelCounter.getOrDefault(name, emptySet())
            val newSet = mutableSetOf<String>()
            newSet.addAll(tags)
            newSet.addAll(set)

            refModelCounter.put(name, newSet)
        }

        fun getTags(ref: Ref): Set<String> {
            return refModelCounter.getOrDefault(ref.className, emptySet())
        }

        fun getTags(className: String): Set<String> {
            return refModelCounter.getOrDefault(className, emptySet())
        }

        fun getCommonOrTag(className: String): String {
            val tags = getTags(className)

            return when {
                tags.size > 1 -> "common"
                tags.size == 1 -> tags.first()
                else -> "common"
            }
        }

        fun getImportTag(className: String, targetPkg: String?): String {
            val pref = if (targetPkg != null) "$targetPkg." else null

            return pref + getCommonOrTag(className) + "." + className
        }
    }
}
