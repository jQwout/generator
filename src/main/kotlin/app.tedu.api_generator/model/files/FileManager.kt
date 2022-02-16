package app.tedu.api_generator.model.files

import java.io.File

class FileManager(private val path: String) {

    private val dir = File(path).apply {
        mkdir()
    }

    fun createClass(tag: String, classContent: String) {
        val f = File(dir, tag)
        f.writeText(classContent)
    }
}