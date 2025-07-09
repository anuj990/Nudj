package com.tpc.nudj.repository.Review

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tpc.nudj.model.Review
import com.tpc.nudj.utils.FirestoreCollections
import com.tpc.nudj.utils.FirestoreUtils
import jakarta.inject.Inject
import kotlinx.coroutines.tasks.await

class ReviewRepositoryImpl @Inject constructor() : ReviewRepository {
    private val firestore = Firebase.firestore

    private val reviewsCollection = firestore.collection(FirestoreCollections.REVIEWS.path)

    override suspend fun addReview(review: Review) {
        val data = FirestoreUtils.toMap(review)
        reviewsCollection.document(review.reviewId).set(data).await()
    }

    override suspend fun updateReview(review: Review) {
        val data = FirestoreUtils.toMap(review)
        reviewsCollection.document(review.reviewId).set(data).await()
    }

    override suspend fun getReviewbyUser(userId: String): List<Review> {
        return try {
            val snapshot = reviewsCollection.whereEqualTo("userId", userId).get().await()
            snapshot.toObjects(Review::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getReviewbyClub(clubId: String): List<Review> {
        return try {
            val snapshot = reviewsCollection.whereEqualTo("clubId", clubId).get().await()
            snapshot.toObjects(Review::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun deleteReview(reviewId: String) {
        try {
            reviewsCollection.document(reviewId).delete().await()
        } catch (e: Exception) {

        }
    }

    override suspend fun getReviewbyEventandUser(eventId: String, userId: String): List<Review> {
        return try {
            val snapshot = reviewsCollection
                .whereEqualTo("eventId", eventId)
                .whereEqualTo("userId", userId)
                .get()
                .await()
            snapshot.toObjects(Review::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getReviewsbyEvent(eventId: String): List<Review> {
        return try {
            val snapshot = reviewsCollection.whereEqualTo("eventId", eventId).get().await()
            snapshot.toObjects(Review::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }
}