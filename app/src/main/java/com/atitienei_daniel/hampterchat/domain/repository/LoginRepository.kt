package com.atitienei_daniel.hampterchat.domain.repository

import com.atitienei_daniel.hampterchat.util.Resource
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    fun loginWithEmailAndPassword(email: String, password: String): Flow<Resource<FirebaseUser>>
}