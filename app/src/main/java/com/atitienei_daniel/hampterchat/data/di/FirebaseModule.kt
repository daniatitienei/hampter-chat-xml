package com.atitienei_daniel.hampterchat.data.di

import com.atitienei_daniel.hampterchat.data.repository.RegisterRepositoryImpl
import com.atitienei_daniel.hampterchat.data.repository.UserDataRepositoryImpl
import com.atitienei_daniel.hampterchat.domain.repository.RegisterRepository
import com.atitienei_daniel.hampterchat.domain.repository.UserDataRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    fun providesAuth(): FirebaseAuth = Firebase.auth

    @Provides
    @Singleton
    fun providesFirestore(): FirebaseFirestore = Firebase.firestore

    @Provides
    @Singleton
    fun providesUserDataRepository(
        auth: FirebaseAuth,
        firestore: FirebaseFirestore
    ): UserDataRepository = UserDataRepositoryImpl(
        auth = auth, firestore = firestore
    )
}