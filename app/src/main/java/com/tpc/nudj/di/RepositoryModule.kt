package com.tpc.nudj.di

import com.tpc.nudj.repository.events.EventsRepository
import com.tpc.nudj.repository.events.EventsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideEventRepository(): EventsRepository = EventsRepositoryImpl()
}