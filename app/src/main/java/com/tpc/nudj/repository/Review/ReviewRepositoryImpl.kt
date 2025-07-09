package com.tpc.nudj.repository.Review

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tpc.nudj.model.Review
import jakarta.inject.Inject
import kotlinx.coroutines.tasks.await

class ReviewRepositoryImpl @Inject constructor() : ReviewRepository {
    private val firestore = Firebase.firestore

    private val reviewsCollection = firestore.collection("reviews")

    override suspend fun addReview(review: Review) {
        reviewsCollection.document(review.reviewId).set(review).await()
    }

    override suspend fun updateReview(review: Review) {
        reviewsCollection.document(review.reviewId).set(review).await()
    }

    override suspend fun getReviewbyUser(userId: String): List<Review> {
        val querySnapshot = reviewsCollection.whereEqualTo("userId", userId).get().await()
        return querySnapshot.toObjects(Review::class.java)
    }

    override suspend fun getReviewbyClub(clubId: String): List<Review> {
        val querySnapShot = reviewsCollection.whereEqualTo("clubId", clubId).get().await()
        return querySnapShot.toObjects(Review::class.java)
    }

    override suspend fun deleteReview(reviewId: String) {
        reviewsCollection.document(reviewId).delete().await()
    }

    override suspend fun getReviewbyEventandUser(eventId: String, userId: String): List<Review> {
        val querySnapshot =
            reviewsCollection.whereEqualTo("eventId", eventId).whereEqualTo("userId", userId).get()
                .await()
        return querySnapshot.toObjects(Review::class.java)
    }

    override suspend fun getReviewsbyEvent(eventId: String): List<Review> {
        val querySnapshot = reviewsCollection.whereEqualTo("eventId", eventId).get().await()
        return querySnapshot.toObjects(Review::class.java)
    }
}