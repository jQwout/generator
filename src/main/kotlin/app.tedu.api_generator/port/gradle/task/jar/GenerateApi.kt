package app.tedu.api_generator.port.gradle.task.jar

import app.tedu.api_generator.port.gradle.task.base.GenerateApi

import java.io.File

fun main() {

    val cfg = File(".", "cfg.json")
    val out = File(".", "out")

    GenerateApi(configPath = cfg.canonicalPath)(outputFile = out.canonicalPath)
}