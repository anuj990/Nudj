package com.tpc.nudj.repository.Follow

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.tpc.nudj.model.Follow
import com.tpc.nudj.utils.FirestoreCollections
import jakarta.inject.Inject
import kotlinx.coroutines.tasks.await


class FollowRepositoryImpl @Inject constructor() : FollowRepository {
    private val firestore = Firebase.firestore
    private val followsCollection = firestore.collection(FirestoreCollections.FOLLOWS.path)
    override suspend fun FollowClub(userId: String, clubId: String): Boolean {
        val follow = Follow(userId = userId, clubId = clubId)
        return try {
            followsCollection.document("$userId-$clubId").set(follow).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun UnfollowClub(userId: String, clubId: String): Boolean {
        return try {
            followsCollection.document("$userId-$clubId").delete().await()
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun FetchFollowingClubs(userId: String): List<Follow> {
        return try {
            val snapshot = followsCollection.whereEqualTo("userId", userId).get().await()
            snapshot.documents.mapNotNull { doc ->
                val clubId = doc.getString("clubId")
                val uid = doc.getString("userId")
                if (clubId != null && uid != null) {
                    Follow(clubId = clubId, userId = uid)
                } else null
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

}