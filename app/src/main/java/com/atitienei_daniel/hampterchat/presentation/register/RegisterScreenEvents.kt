package com.atitienei_daniel.hampterchat.presentation.register

sealed class RegisterScreenEvents {
    data class OnPasswordChanged(val password: String) : RegisterScreenEvents()
    data class OnEmailChanged(val email: String) : RegisterScreenEvents()
    data class OnUsernameChanged(val username: String) : RegisterScreenEvents()
    data class OnNameChanged(val name: String) : RegisterScreenEvents()
    data class OnGenderSelected(val gender: String) : RegisterScreenEvents()

    object OnNextImeClickPasswordTextField : RegisterScreenEvents()
    object OnNextImeClickEmailTextField : RegisterScreenEvents()

    object OnValidateFields : RegisterScreenEvents()
}
