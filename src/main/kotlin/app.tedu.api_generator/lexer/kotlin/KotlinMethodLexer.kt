package app.tedu.api_generator.lexer.kotlin

import app.tedu.api_generator.lexer.MethodLexer
import app.tedu.api_generator.lexer.TypeLexer
import app.tedu.api_generator.model.files.ResponseWrapper
import app.tedu.api_generator.model.pojo.Ref

class KotlinMethodLexer(
    private val typeLexer: TypeLexer,
    private val modelLexer: KotlinModelLexer,
) : MethodLexer {

    override fun defineMethodScope(method: ResponseWrapper) {
        method.methodResponses.values.forEach {
            if (it.schema != null) {

                if(typeLexer.isModel(it.schema)){
                    println("!!!")
                }

                Ref.add(
                    typeLexer.type(it.schema), method.tags
                )
            }
        }
    }
}