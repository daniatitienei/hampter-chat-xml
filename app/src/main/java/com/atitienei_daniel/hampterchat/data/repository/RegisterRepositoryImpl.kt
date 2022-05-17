package com.atitienei_daniel.hampterchat.data.repository

import com.atitienei_daniel.hampterchat.domain.repository.RegisterRepository
import com.atitienei_daniel.hampterchat.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : RegisterRepository {

    override fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        name: String,
        username: String,
        gender: String
    ): Flow<Resource<FirebaseUser>> = callbackFlow {

        trySend(Resource.Loading<FirebaseUser>())

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                val result = if (task.isSuccessful) {
                    Resource.Success<FirebaseUser>(data = task.result.user)
                } else {
                    Resource.Error<FirebaseUser>(error = task.exception)
                }

                trySend(result).isSuccess
            }

        awaitClose { cancel() }
    }.flowOn(Dispatchers.IO)
}