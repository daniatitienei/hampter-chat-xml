package com.atitienei_daniel.hampterchat.data.di

import com.atitienei_daniel.hampterchat.data.repository.LoginRepositoryImpl
import com.atitienei_daniel.hampterchat.data.repository.RegisterRepositoryImpl
import com.atitienei_daniel.hampterchat.domain.repository.LoginRepository
import com.atitienei_daniel.hampterchat.domain.repository.RegisterRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthenticationModule {

    @Provides
    @Singleton
    fun providesRegisterRepository(
        auth: FirebaseAuth,
        firestore: FirebaseFirestore
    ): RegisterRepository = RegisterRepositoryImpl(auth, firestore)

    @Provides
    @Singleton
    fun providesLoginRepository(
        auth: FirebaseAuth
    ): LoginRepository = LoginRepositoryImpl(auth)
}