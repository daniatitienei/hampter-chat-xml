package com.atitienei_daniel.hampterchat.presentation.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atitienei_daniel.hampterchat.domain.model.ValidationEvent
import com.atitienei_daniel.hampterchat.domain.repository.LoginRepository
import com.atitienei_daniel.hampterchat.domain.use_case.ValidateEmail
import com.atitienei_daniel.hampterchat.domain.use_case.ValidatePassword
import com.atitienei_daniel.hampterchat.util.Resource
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val validateEmail: ValidateEmail,
    private val validatePassword: ValidatePassword,
    private val repository: LoginRepository
) : ViewModel() {

    var email = MutableLiveData<String>()
    var emailError = MutableLiveData<String?>()

    var password = MutableLiveData<String>()
    var passwordError = MutableLiveData<String?>()

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvent = validationEventChannel.receiveAsFlow()

    fun onEvent(event: LoginEvents) {
        when (event) {
            is LoginEvents.OnEmailChanged -> {
                email.value = event.email
            }
            is LoginEvents.OnPasswordChanged -> {
                password.value = event.password
            }
            is LoginEvents.OnForgotPasswordClick -> {

            }
            is LoginEvents.OnValidateFields -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val emailResult = validateEmail(email = email.value)
        val passwordResult = validatePassword(password = password.value)

        val hasError = listOf(
            emailResult,
            passwordResult
        ).any { !it.successful }

        if (hasError) {
            emailError.value = emailResult.errorMessage
            passwordError.value = passwordResult.errorMessage

            return
        }

        repository.loginWithEmailAndPassword(email = email.value!!, password = password.value!!)
            .onEach { resource ->
                when (resource) {
                    is Resource.Success -> {
                        validationEventChannel.send(ValidationEvent.Success)
                    }
                    is Resource.Error -> {
                        when (resource.error) {
                            is FirebaseAuthInvalidCredentialsException -> {
                                passwordError.value = resource.error.localizedMessage
                            }
                            is FirebaseAuthInvalidUserException -> {
                                emailError.value = resource.error.localizedMessage
                            }
                            else -> {}
                        }
                    }
                    else -> {}
                }
            }.launchIn(viewModelScope)
    }

    sealed class LoginEvents {
        data class OnEmailChanged(val email: String) : LoginEvents()
        data class OnPasswordChanged(val password: String) : LoginEvents()

        object OnValidateFields : LoginEvents()

        object OnForgotPasswordClick : LoginEvents()
    }
}