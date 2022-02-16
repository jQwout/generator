package app.tedu.api_generator.model.files

import java.io.File

class GeneratedClass(

    val name: String,

    val tag: String,

    val content: String,

    val ext: String
) {

    fun writeToFile(outDir: File) {
        val tagDir = File(outDir, tag)
        if (tagDir.isDirectory.not() || tagDir.exists().not()) {
            tagDir.mkdirs()
        }

        val modelFile =  File(tagDir, name + ext)

        if(modelFile.exists()){
            return
        }

        modelFile.apply {
            createNewFile()
            appendText(content)
        }
    }
}