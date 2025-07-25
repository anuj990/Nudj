package com.tpc.nudj.repository.review

import com.tpc.nudj.model.Review

interface ReviewRepository {
    suspend fun addReview(review: Review)
    suspend fun updateReview(review: Review)
    suspend fun getReviewsbyEvent(eventId: String): List<Review>
    suspend fun getReviewbyEventandUser(eventId: String,userId: String): List<Review>
    suspend fun getReviewbyUser(userId: String): List<Review>
    suspend fun getReviewbyClub(clubId: String): List<Review>
    suspend fun deleteReview(reviewId: String)
}