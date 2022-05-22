package com.atitienei_daniel.hampterchat.presentation.conversation_list

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atitienei_daniel.hampterchat.data.repository.UserDataRepositoryImpl
import com.atitienei_daniel.hampterchat.domain.model.User
import com.atitienei_daniel.hampterchat.domain.repository.UserDataRepository
import com.atitienei_daniel.hampterchat.util.Resource
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ConversationListViewModel @Inject constructor(
    private val repository: UserDataRepository
) : ViewModel() {

    var user = MutableLiveData<User>()
        private set

    var isLoading = MutableLiveData<Boolean>(true)
        private set

    var error = MutableLiveData<String?>(null)
        private set

    init {
        repository.getUserData().onEach { resource ->
            when (resource) {
                is Resource.Success -> {
                    user.value = resource.data!!
                    isLoading.value = false
                    Log.d("user", user.value.toString())
                }
                is Resource.Loading -> {
                    isLoading.value = true
                }
                is Resource.Error -> {
                    isLoading.value = false
                    error.value = resource.error?.localizedMessage
                }
            }
        }.launchIn(viewModelScope)
    }
}