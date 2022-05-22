package com.atitienei_daniel.hampterchat.domain.model

data class Person(
    val name: String = "",
    val username: String = "",
    val chat: List<Message> = listOf(),
    val imageUrl: String = ""
)
