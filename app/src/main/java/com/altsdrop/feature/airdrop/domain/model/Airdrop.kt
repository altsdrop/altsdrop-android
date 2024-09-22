package com.altsdrop.feature.airdrop.domain.model

import java.util.UUID

data class Airdrop(
    val airdropId: String = UUID.randomUUID().toString(),
    val title: String = "",
    val categories: String = "",
    val chain: String = "",
    val description: String = "",
    val endDate: String = "",
    val networks: List<String> = listOf(""),
    val officialLinks: OfficialLinks = OfficialLinks(),
    val shortDescription: String = "",
    val slug: String = "",
    val socialLinks: SocialLinks = SocialLinks(),
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
    "",
    listOf(""),
    OfficialLinks(
        documentation = "https://docs.blast.io/about-blast",
        website = "https://blast.io/en",
        whitePaper = "https://docs.blast.io/about-blast"
    ),
    "Blast is the only Ethereum L2 with native yield for ETH and stablecoins.",
    "blast-l2",
    SocialLinks(
        discord = "https://x.com/blast",
        x = "https://x.com/blast"
    ),
    "",
    listOf(
        "Create an account at Bitget.",
        "Follow them on their Russian Twitter handle.",
        "Join their Russian Telegram group and Telegram channel.",
        "Join the Bitget Vkontakte page.",
        "Submit your Bitget UID to @Anri_nap on Telegram.",
        "The first 500 Russian participants will get 100 BGB each."
    ),
    listOf("ETH", "Arbitrum", "Blast", "Polygon", "Matic"),
    "https://blog.bitfinex.com/wp-content/uploads/2024/03/BFX_ARB.png"
)