package app.tedu.api_generator.lexer.kotlin

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
    imports: Set<String>,
    comments: List<String> = emptyList(),
    classContent: String
) = buildString {
    imports.forEach { appendLine("import $it") }
    appendLine("\n\n")
    comments.forEach { appendLine("// $it") }

    appendLine(classContent)
}
