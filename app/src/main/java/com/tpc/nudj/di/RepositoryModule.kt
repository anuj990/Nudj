package com.tpc.nudj.di

import com.tpc.nudj.repository.review.ReviewRepository
import com.tpc.nudj.repository.review.ReviewRepositoryImpl
import com.tpc.nudj.repository.events.EventsRepository
import com.tpc.nudj.repository.events.EventsRepositoryImpl
import com.tpc.nudj.repository.follow.FollowRepository
import com.tpc.nudj.repository.follow.FollowRepositoryImpl
import com.tpc.nudj.repository.rsvp.RsvpRepository
import com.tpc.nudj.repository.rsvp.RsvpRepositoryImpl
import com.tpc.nudj.repository.user.UserRepository
import com.tpc.nudj.repository.user.UserRepositoryImpl
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

    @Provides
    @Singleton
    fun provideRsvpRepository(): RsvpRepository = RsvpRepositoryImpl()

    @Provides
    @Singleton
    fun provideReviewRepository(): ReviewRepository = ReviewRepositoryImpl()
    @Provides
    @Singleton
    fun provideFollowRepository(): FollowRepository = FollowRepositoryImpl()
}