package app.tedu.api_generator.port.gradle.task.base

import app.tedu.api_generator.di.GeneratorDeps
import app.tedu.api_generator.generator.Generator
import com.google.gson.Gson
import java.io.File

class GenerateApi(
    private val configPath: String
) {

    operator fun invoke(outputFile: String) {
        val dir = dir(outputFile)
        dir.deleteRecursively()
        dir.mkdir()
        GeneratorDeps(Gson(), file(configPath)).apply {
            Generator(
                schemeParser,
                provideGson(),
                provideModelGenerator(),
                packageGenerator,
                dir(outputFile)
            )
                .generate(
                    file(config.schemePath).readText()
                )
        }
    }

    operator fun invoke(outputFile: File) {
        outputFile.deleteRecursively()
        outputFile.mkdir()
        GeneratorDeps(Gson(), file(configPath)).apply {
            Generator(
                schemeParser,
                provideGson(),
                provideModelGenerator(),
                packageGenerator,
                outputFile
            )
                .generate(
                    file(config.schemePath).readText()
                )
        }
    }


    private fun file(path: String): File {
        return File(path).apply {
            if (exists().not() || isFile().not()) throw IllegalArgumentException("file by $path not found")
        }
    }

    private fun dir(path: String): File {
        return File(path).apply {
            mkdirs()
        }
    }

}