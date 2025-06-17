package com.altsdrop.feature.airdrop.utils

import com.altsdrop.R

fun resolveDrawableByTitle(hyperLinkTitle: String): Int = when {
    hyperLinkTitle.contains("whitepaper", ignoreCase = true) -> R.drawable.ic_whitepaper
    hyperLinkTitle.contains("documentation", ignoreCase = true) -> R.drawable.ic_documentation
    hyperLinkTitle.contains("website", ignoreCase = true) -> R.drawable.ic_website
    else -> R.drawable.ic_link
}

fun resolveDrawableByUrl(url: String): Int = when {
    url.contains("x.com", ignoreCase = true) ||
            url.contains("twitter.com", ignoreCase = true) -> R.drawable.ic_whitepaper
    url.contains("discord.com", ignoreCase = true) -> R.drawable.ic_documentation
    url.contains("telegram.org", ignoreCase = true) -> R.drawable.ic_website
    else -> R.drawable.ic_link
}