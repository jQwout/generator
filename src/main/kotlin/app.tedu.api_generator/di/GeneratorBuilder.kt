package app.tedu.api_generator.di

import app.tedu.api_generator.Cfg
import app.tedu.api_generator.generator.ModelGenerator
import app.tedu.api_generator.generator.PackageGenerator
import app.tedu.api_generator.lexer.LexerBundle
import app.tedu.api_generator.lexer.LexerFactory
import app.tedu.api_generator.lexer.MethodLexer
import app.tedu.api_generator.parser.SchemeParser
import com.google.gson.Gson
import java.io.File

class GeneratorDeps(
    private val gson: Gson,
    private val cfgFile: File,
) {

    val config: Cfg by lazy {
        gson.fromJson(cfgFile.readText(), Cfg::class.java)
    }

    private val lexers: LexerBundle by lazy {
        LexerFactory(config)()
    }

    fun provideGson(): Gson {
        return gson
    }

    fun provideModelGenerator(): ModelGenerator {
        return ModelGenerator(config.lang.ext, config.commonPackage, lexers.model, lexers.enum)
    }

    val schemeParser: SchemeParser by lazy {
        SchemeParser()
    }

    val methodDelegate: MethodLexer by lazy {
        lexers.method
    }

    val packageGenerator by lazy {
        PackageGenerator(lexers.method, lexers.model)
    }
}