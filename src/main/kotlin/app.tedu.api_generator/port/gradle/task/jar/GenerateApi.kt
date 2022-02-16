package app.tedu.api_generator.port.gradle.task.jar

import app.tedu.api_generator.port.gradle.task.base.GenerateApi
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.io.File

fun main() {

    val cfg = File(".", "cfg.json")
    val out = File(".", "out")

    GenerateApi(configPath = cfg.canonicalPath)(outputFile = out.canonicalPath)
}


fun test() {
    val state = MutableSharedFlow<String>(extraBufferCapacity = 1)

    CoroutineScope(Dispatchers.IO).launch {
        state.collect {
            println(it)
        }
    }

    CoroutineScope(Dispatchers.IO).launch {
        println(state.tryEmit("2"))
        state.emit("1")
        state.emit("3")
        state.emit("4")
    }

    Thread.sleep(5000)
}