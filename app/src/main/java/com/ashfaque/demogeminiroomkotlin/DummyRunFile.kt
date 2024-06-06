package com.ashfaque.demogeminiroomkotlin

fun convertMarkdownToHtml(text: String): String {
    val boldRegex = "\\*\\*(.*?)\\*\\*".toRegex()
    val listItemRegex = "\\* (.*?)\\n".toRegex()
    val headingRegex = "^\\*\\*(.*?)\\*\\*".toRegex(RegexOption.MULTILINE)

    var htmlText = text
    htmlText = boldRegex.replace(htmlText) { matchResult -> "<b>${matchResult.groupValues[1]}</b>" }
    htmlText = listItemRegex.replace(htmlText) { matchResult -> "<li>${matchResult.groupValues[1]}</li>" }
    htmlText = headingRegex.replace(htmlText) { matchResult -> "<h2>${matchResult.groupValues[1]}</h2>" }

    // Handle paragraphs
    htmlText = htmlText.replace("\n\n", "<p>")

    return htmlText
}

fun main() {

    val htmlText = convertMarkdownToHtml(markdownText)
    println(htmlText)
}