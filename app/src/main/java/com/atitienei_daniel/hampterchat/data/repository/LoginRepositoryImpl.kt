package com.atitienei_daniel.hampterchat.data.repository

import com.atitienei_daniel.hampterchat.domain.repository.LoginRepository
import com.atitienei_daniel.hampterchat.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class LoginRepositoryImpl(
    private val auth: FirebaseAuth
) : LoginRepository {
    override fun loginWithEmailAndPassword(
        email: String,
        password: String
    ): Flow<Resource<FirebaseUser>> = callbackFlow {

        trySend(Resource.Loading<FirebaseUser>())

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                val result = if (task.isSuccessful) {
                    Resource.Success<FirebaseUser>(data = task.result.user)
                } else {
                    Resource.Error<FirebaseUser>(error = task.exception)
                }

                trySend(result).isSuccess
            }

        awaitClose { close() }
    }
}