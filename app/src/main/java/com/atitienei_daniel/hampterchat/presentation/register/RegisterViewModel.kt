package com.atitienei_daniel.hampterchat.presentation.register

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atitienei_daniel.hampterchat.R
import com.atitienei_daniel.hampterchat.domain.model.ValidationEvent
import com.atitienei_daniel.hampterchat.domain.repository.RegisterRepository
import com.atitienei_daniel.hampterchat.domain.use_case.*
import com.atitienei_daniel.hampterchat.util.Resource
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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
    private val application: Application,
    private val repository: RegisterRepository
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

    fun onEvent(event: RegisterEvents) {
        when (event) {
            is RegisterEvents.OnEmailChanged -> {
                email.value = event.email
            }
            is RegisterEvents.OnGenderSelected -> {
                gender.value = event.gender
            }
            is RegisterEvents.OnNameChanged -> {
                name.value = event.name
            }
            is RegisterEvents.OnPasswordChanged -> {
                password.value = event.password
            }
            is RegisterEvents.OnUsernameChanged -> {
                username.value = event.username
            }
            is RegisterEvents.OnNextImeClickEmailTextField -> {
                title.value = application.getString(
                    R.string.register_screen_title_requesting_password
                )
            }
            is RegisterEvents.OnNextImeClickPasswordTextField -> {
                title.value =
                    application.getString(R.string.register_screen_title_requesting_name_username_gender)
            }
            is RegisterEvents.OnValidateFields -> {
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

        repository.createUserWithEmailAndPassword(
            email = email.value!!,
            password = password.value!!,
            username = username.value!!,
            name = name.value!!,
            gender = gender.value!!
        ).onEach { resource ->
            when (resource) {
                is Resource.Success -> {
                    validationEventChannel.send(ValidationEvent.Success)
                }
                is Resource.Error -> {
                    when (resource.error) {
                        is FirebaseAuthWeakPasswordException -> {
                            application.getString(R.string.error_weak_password)
                        }
                        is FirebaseAuthUserCollisionException -> {
                            application.getString(R.string.error_user_exists)
                        }
                        else -> {}
                    }
                }
                else -> {}
            }
        }.launchIn(viewModelScope)

    }

    sealed class RegisterEvents {
        data class OnPasswordChanged(val password: String) : RegisterEvents()
        data class OnEmailChanged(val email: String) : RegisterEvents()
        data class OnUsernameChanged(val username: String) : RegisterEvents()
        data class OnNameChanged(val name: String) : RegisterEvents()
        data class OnGenderSelected(val gender: String) : RegisterEvents()

        object OnNextImeClickPasswordTextField : RegisterEvents()
        object OnNextImeClickEmailTextField : RegisterEvents()

        object OnValidateFields : RegisterEvents()
    }
}