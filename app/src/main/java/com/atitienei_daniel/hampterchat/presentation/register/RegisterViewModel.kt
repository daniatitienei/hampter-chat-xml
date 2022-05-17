package com.atitienei_daniel.hampterchat.presentation.register

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atitienei_daniel.hampterchat.R
import com.atitienei_daniel.hampterchat.domain.use_case.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val validateEmail: ValidateEmail,
    private val validatePassword: ValidatePassword,
    private val validateUsername: ValidateUsername,
    private val validateName: ValidateName,
    private val validateGender: ValidateGender,
    private val application: Application
) : ViewModel() {

    val email = MutableLiveData<String>()
    val emailError = MutableLiveData<String?>(null)

    val username = MutableLiveData<String>()
    val usernameError = MutableLiveData<String?>(null)

    val name = MutableLiveData<String>()
    val nameError = MutableLiveData<String?>(null)

    val password = MutableLiveData<String>()
    val passwordError = MutableLiveData<String?>(null)

    val gender = MutableLiveData<String>()
    val genderError = MutableLiveData<String?>(null)

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvent = validationEventChannel.receiveAsFlow()

    var title = MutableLiveData<String>()

    init {
        title.value =
            application.getString(R.string.register_screen_title_requesting_email)
    }

    fun onEvent(event: RegisterScreenEvents) {
        when (event) {
            is RegisterScreenEvents.OnEmailChanged -> {
                email.value = event.email
            }
            is RegisterScreenEvents.OnGenderSelected -> {
                gender.value = event.gender
            }
            is RegisterScreenEvents.OnNameChanged -> {
                name.value = event.name
            }
            is RegisterScreenEvents.OnPasswordChanged -> {
                password.value = event.password
            }
            is RegisterScreenEvents.OnUsernameChanged -> {
                username.value = event.username
            }
            is RegisterScreenEvents.OnNextImeClickEmailTextField -> {
                title.value = application.getString(
                    R.string.register_screen_title_requesting_password
                )
            }
            is RegisterScreenEvents.OnNextImeClickPasswordTextField -> {
                title.value =
                    application.getString(R.string.register_screen_title_requesting_name_username_gender)
            }
            is RegisterScreenEvents.OnValidateFields -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val emailResult = validateEmail(email = email.value)
        val passwordResult = validatePassword(password = password.value)
        val usernameResult = validateUsername(username = username.value)
        val nameResult = validateName(name = name.value)
        val genderResult = validateGender(gender = gender.value)

        val hasError = listOf(
            emailResult,
            passwordResult,
            passwordResult,
            usernameResult,
            nameResult,
            genderResult
        ).any { !it.successful }

        if (hasError) {
            emailError.value = emailResult.errorMessage
            passwordError.value = passwordResult.errorMessage
            usernameError.value = usernameResult.errorMessage
            nameError.value = nameResult.errorMessage
            genderError.value = genderResult.errorMessage

            return
        }

        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
    }

    sealed class ValidationEvent {
        object Success : ValidationEvent()
    }
}