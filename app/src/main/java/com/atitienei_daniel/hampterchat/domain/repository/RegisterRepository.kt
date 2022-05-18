package com.atitienei_daniel.hampterchat.domain.repository

import com.atitienei_daniel.hampterchat.util.Resource
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface RegisterRepository {

    fun createUserWithEmailAndPassword(
        email: String,
        password: String,
    ): Flow<Resource<FirebaseUser>>

    fun storeUserData(
        uid: String,
        name: String,
        username: String,
        gender: String
    ) : Flow<Resource<Nothing>>
}