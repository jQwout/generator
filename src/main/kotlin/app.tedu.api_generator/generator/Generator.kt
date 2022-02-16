package app.tedu.api_generator.generator

import app.tedu.api_generator.model.pojo.Scheme
import app.tedu.api_generator.parser.SchemeParser
import com.google.gson.Gson
import java.io.File

class Generator(
    private val schemeParser: SchemeParser,
    private val gson: Gson,
    private val modelDelegate: ModelGenerator,
    private val packageGenerator: PackageGenerator,
    private val outPutDir: File
) {

    fun generate(schemeRaw: String) {
        generate(
            gson.fromJson(schemeRaw, Scheme::class.java)
        )
    }

    fun generate(scheme: Scheme) {
        val wr = schemeParser.parse(scheme)

        packageGenerator.definePck(models = wr.modelWrapper, params = wr.paramsWrapper, responses = wr.responseWrapper)

        wr.modelWrapper.forEach {
            modelDelegate.generate(it).writeToFile(outPutDir)
        }

        wr.enumWrapper.forEach {
            modelDelegate.generate(it).writeToFile(outPutDir)
        }

    }

}