package com.atitienei_daniel.hampterchat.domain.model

data class User(
    val uid: String = "",
    val name: String = "",
    val username: String = "",
    val gender: String = "",
    val friendList: List<Person> = listOf(),
    val requestList: List<ChatRequest> = listOf()
)
