package com.atitienei_daniel.hampterchat.domain.model

import com.google.firebase.Timestamp

data class Message(
    val sentBy: String = "",
    val text: String = "",
    val sentAt: Timestamp = Timestamp.now(),
)
