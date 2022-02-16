package app.tedu.api_generator.port.gradle.task

import app.tedu.api_generator.port.gradle.task.base.GenerateApi
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import java.io.File
import javax.inject.Inject

open class GenerateApiTask @Inject constructor(
    private val configPath: String,
    @OutputDirectory
    private val outputDirectory: File
) : DefaultTask() {

    init {
        group = "api-generator" // This will be the group name for your task.
        description = "generate api models and methods"
    }

    @TaskAction
    fun run() {
        GenerateApi(configPath)(outputDirectory)
    }
}