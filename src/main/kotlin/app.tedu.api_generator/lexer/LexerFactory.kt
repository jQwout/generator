package app.tedu.api_generator.lexer

import app.tedu.api_generator.Cfg
import app.tedu.api_generator.lexer.kotlin.KotlinEnumLexer
import app.tedu.api_generator.lexer.kotlin.KotlinMethodLexer
import app.tedu.api_generator.lexer.kotlin.KotlinModelLexer
import app.tedu.api_generator.lexer.kotlin.KotlinTypeLexer

class LexerFactory(private val cfg: Cfg) {

    operator fun invoke(): LexerBundle {
        return if (cfg.lang.name == "Kotlin") {
            val ktl = KotlinTypeLexer()
            LexerBundle(
                type = ktl,
                model = KotlinModelLexer(cfg.lang, ktl),
                enum = KotlinEnumLexer(),
                method = KotlinMethodLexer(ktl, KotlinModelLexer(cfg.lang, ktl))
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
    val method: MethodLexer
)