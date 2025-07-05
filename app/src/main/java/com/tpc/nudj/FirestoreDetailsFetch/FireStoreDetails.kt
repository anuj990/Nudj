package com.tpc.nudj.FirestoreDetailsFetch

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FireStoreDetails {
    suspend fun checkusertypeandnavigate(
        onNormalUser: () -> Unit,
        onClubUser: () -> Unit,
        onUserNotFound: () -> Unit
    ) {
        var currentUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser
        if (currentUser == null) {
            onUserNotFound()
            return
        }
        val firestore = FirebaseFirestore.getInstance()
        try {
            val userDoc = firestore.collection("users").document(currentUser.uid).get().await()
            if (userDoc.exists()) {
                onNormalUser()
                return
            }
            val clubDoc = firestore.collection("clubs").document(currentUser.uid).get().await()
            if (clubDoc.exists()) {
                onClubUser()
                return
            }
            onUserNotFound()
        } catch (e: Exception) {
            onUserNotFound()
        }
    }

}