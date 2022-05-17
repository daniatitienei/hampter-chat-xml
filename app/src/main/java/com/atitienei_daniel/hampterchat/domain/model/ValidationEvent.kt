package com.atitienei_daniel.hampterchat.domain.model

sealed class ValidationEvent {
    object Success : ValidationEvent()
}