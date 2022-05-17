package com.atitienei_daniel.hampterchat.data.di

import android.content.Context
import com.atitienei_daniel.hampterchat.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ValidationModule {

    @Provides
    @Singleton
    fun provideEmailValidation(@ApplicationContext context: Context) = ValidateEmail(context)

    @Provides
    @Singleton
    fun providePasswordValidation(@ApplicationContext context: Context) = ValidatePassword(context)

    @Provides
    @Singleton
    fun provideUsernameValidation(@ApplicationContext context: Context) = ValidateUsername(context)

    @Provides
    @Singleton
    fun provideNameValidation(@ApplicationContext context: Context) = ValidateName(context)

    @Provides
    @Singleton
    fun provideGenderValidation(@ApplicationContext context: Context) = ValidateGender(context)
}