package com.atitienei_daniel.hampterchat.data.repository

import com.atitienei_daniel.hampterchat.domain.model.User
import com.atitienei_daniel.hampterchat.domain.repository.UserDataRepository
import com.atitienei_daniel.hampterchat.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class UserDataRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth,
) : UserDataRepository {

    override fun getUserData(): Flow<Resource<User>> = callbackFlow {

        trySend(Resource.Loading<User>())

        val listener = firestore.collection("users").document(auth.uid!!)
            .addSnapshotListener { snapshot, error ->
                val result = if (error == null) {
                    Resource.Success<User>(data = snapshot?.toObject())
                } else {
                    Resource.Error<User>(error = error)
                }

                trySend(result).isSuccess
            }

        awaitClose { listener.remove() }
    }
}