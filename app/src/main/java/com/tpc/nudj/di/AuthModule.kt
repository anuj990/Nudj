package com.tpc.nudj.di

import android.content.Context
import com.tpc.nudj.repository.auth.AuthRepository
import com.tpc.nudj.repository.auth.FirebaseAuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
    @Provides
    @Singleton
    fun provideAuthRepository(): AuthRepository = FirebaseAuthRepository()

}

