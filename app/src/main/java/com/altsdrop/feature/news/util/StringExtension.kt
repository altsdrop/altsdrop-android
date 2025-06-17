package com.altsdrop.feature.news.util

import android.os.Build
import android.text.Html
import android.text.Spanned

sealed class ContentItem {
    data class Text(val text: String) : ContentItem()
    data class Link(val text: String, val url: String) : ContentItem()
    data class Image(val url: String) : ContentItem()
    data class Heading(val level: Int, val text: String) : ContentItem()
}

fun String.parseHtmlContent(): List<ContentItem> {
    val contentItems = mutableListOf<ContentItem>()

    // Define regex patterns for different HTML elements
    val headingRegex = Regex("""<h([1-6])>(.*?)</h\1>""", RegexOption.IGNORE_CASE)
    val paragraphRegex = Regex("""<p>(.*?)</p>""", RegexOption.IGNORE_CASE)
    val linkRegex = Regex("""<a href=["']([^"']+)["']>(.*?)</a>""", RegexOption.IGNORE_CASE)
    val imageRegex = Regex("""<img[^>]+src=["']([^"']+)["'][^>]*/?>""", RegexOption.IGNORE_CASE)

    // Match all paragraphs, headings, images, and links
    // We are now capturing entire paragraphs and processing them for links as well.
    val combinedRegex = Regex(
            """${headingRegex.pattern}|${paragraphRegex.pattern}|${imageRegex.pattern}|${linkRegex.pattern}""".trimMargin(),
        RegexOption.IGNORE_CASE
    )

    // Find all elements
    combinedRegex.findAll(this).forEach { match ->
        val matchedText = match.value

        // Check for heading tags
        val headingMatch = headingRegex.find(matchedText)
        if (headingMatch != null) {
            val level = headingMatch.groups[1]?.value?.toInt() ?: 1
            val text = headingMatch.groups[2]?.value ?: ""
            contentItems.add(ContentItem.Heading(level, text))
            return@forEach
        }

        // Check for image tags
        val imageMatch = imageRegex.find(matchedText)
        if (imageMatch != null) {
            val url = imageMatch.groups[1]?.value ?: ""
            contentItems.add(ContentItem.Image(url))
            return@forEach
        }

        // Check for paragraph tags
        val paragraphMatch = paragraphRegex.find(matchedText)
        if (paragraphMatch != null) {
            val paragraphContent = paragraphMatch.groups[1]?.value ?: ""
            val linksInParagraph = linkRegex.findAll(paragraphContent)

            // Split paragraph content by links
            var lastPos = 0
            linksInParagraph.forEach { link ->
                val linkText = link.groups[2]?.value ?: ""
                val linkUrl = link.groups[1]?.value ?: ""

                // Add any text before the link
                if (lastPos < link.range.first) {
                    val textBeforeLink = paragraphContent.substring(lastPos, link.range.first).trim()
                    if (textBeforeLink.isNotEmpty()) {
                        contentItems.add(ContentItem.Text(textBeforeLink))
                    }
                }

                // Add the link
                contentItems.add(ContentItem.Link(linkText, linkUrl))
                lastPos = link.range.last + 1
            }

            // Add remaining text after the last link
            if (lastPos < paragraphContent.length) {
                val remainingText = paragraphContent.substring(lastPos).trim()
                if (remainingText.isNotEmpty()) {
                    contentItems.add(ContentItem.Text(remainingText))
                }
            }

            return@forEach
        }
    }

    return contentItems
}
