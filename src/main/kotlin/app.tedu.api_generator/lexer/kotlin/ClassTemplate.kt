package app.tedu.api_generator.lexer.kotlin

import app.tedu.api_generator.dsl.newLine

/**
 *
 * import <package_name>.<tag>.<className>
 * ...
 *
 * // <comment>
 * // <comment>
 *
 * class Content {...}
 */


fun kotlinClass(
    imports: List<String>,
    comments: List<String>,
    classContent: String
) = buildString {
    imports.forEach { appendLine("import $it") }

    comments.forEach { appendLine("// $it") }

    appendLine(classContent)
}


private fun import(imp: List<String>): String {
    return imp.joinToString("\n") { "import $it" }
}

private infix fun String.comment(comment: List<String>): String {
    return "${this}\n${comment.joinToString() { "// $it" }}"
}

private fun String.spaces(count: Int): String {
    val space = (1..count).map { "\n" }
    return "$this$space"
}

private infix fun String.classContent(content: String): String {
    return "$this\n$content"
}

