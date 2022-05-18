package com.atitienei_daniel.hampterchat.domain.model

import com.google.firebase.Timestamp

data class Message(
    val text: String = "",
    val sentAt: Timestamp = Timestamp.now(),
)
