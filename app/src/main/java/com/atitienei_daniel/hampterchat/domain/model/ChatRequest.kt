package com.atitienei_daniel.hampterchat.domain.model

data class ChatRequest(
    val person: Person = Person(),
    val isAccepted: Boolean? = null
)
