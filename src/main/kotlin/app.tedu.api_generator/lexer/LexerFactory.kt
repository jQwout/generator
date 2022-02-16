package app.tedu.api_generator.lexer

import app.tedu.api_generator.Cfg
import app.tedu.api_generator.lexer.kotlin.*

class LexerFactory(private val cfg: Cfg) {

    operator fun invoke(): LexerBundle {
        return if (cfg.lang.name == "Kotlin") {
            val ktl = KotlinTypeLexer()
            val ktml = KotlinMethodLexer(ktl)
            LexerBundle(
                type = ktl,
                model = KotlinModelLexer(cfg, cfg.lang, ktl),
                enum = KotlinEnumLexer(),
                method = ktml,
                api = KotlinRetrofitLexer(ktml, cfg)
            )
        } else {
            throw IllegalArgumentException("cannot support lang : ${cfg.lang.name}")
        }
    }

}

class LexerBundle(
    val type: TypeLexer,
    val model: ModelLexer,
    val enum: EnumLexer,
    val method: MethodLexer,
    val api: ApiAccessObject
)