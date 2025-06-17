package com.altsdrop.feature.airdrop.domain.model

import java.util.UUID

data class Airdrop(
    val airdropId: String = UUID.randomUUID().toString(),
    val title: String = "",
    val categories: String = "",
    val chain: String = "",
    val description: String = "",
    val isHighlyRated: Boolean = false,
    val isFeatured: Boolean = false,
    val dateAdded: String = "",
    val endDate: String = "",
    val networks: List<String> = listOf(""),
    val officialLinks: List<HyperLink> = listOf(),
    val shortDescription: String = "",
    val slug: String = "",
    val socialLinks: List<HyperLink> = listOf(),
    val startDate: String = "",
    val steps: List<String> = listOf(),
    val tags: List<String> = listOf(),
    val thumbnail: String = ""
)

val previewAirdrop = Airdrop(
    "",
    "Blast L2",
    "",
    "Blast",
    "Blast yield comes from ETH staking and RWA protocols. The yield from these decentralized protocols is passed back to Blast users automatically. The default interest rate on other L2s is 0%. On Blast, it’s 4% for ETH and 5% for stablecoins.",
    false,
    false,
    "",
    "",
    listOf(""),
    listOf(
        HyperLink(
            title = "Website",
            url = "https://blast.io/en"
        ),
        HyperLink(
            title = "Whitepaper",
            url = "https://docs.blast.io/about-blast"
        ),
        HyperLink(
            title = "Documentation",
            url = "https://docs.blast.io/about-blast"
        )
    ),
    "Blast is the only Ethereum L2 with native yield for ETH and stablecoins.",
    "blast-l2",
    listOf(
        HyperLink(
            title = "Discord",
            url = "https://discord.gg/blast"
        ),
        HyperLink(
            title = "X",
            url = "https://x.com/blast"
        )
    ),
    "",
    listOf(
        "Join our <a href=\"https://t.me/officialchannel\">Telegram channel</a> to stay updated. Follow us on <a href=\"https://twitter.com/officialprofile\">Twitter</a>",
        "Follow us on <a href=\"https://twitter.com/officialprofile\">Twitter</a> and like our pinned post.",
        "Retweet the announcement using <b>#ProjectAirdrop</b> hashtag.",
        "Complete KYC verification on our <a href=\"https://project.com/kyc\">official portal</a>.",
        "Submit your <i>Ethereum wallet address</i> to receive tokens.",
        "Refer friends using your unique link and <b>earn bonus tokens</b>!"
    ),
    listOf("ETH", "Arbitrum", "Blast", "Polygon", "Matic"),
    "https://blog.bitfinex.com/wp-content/uploads/2024/03/BFX_ARB.png"
)