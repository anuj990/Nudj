package com.tpc.nudj.utils

/**
 * Enum class that contains all Firestore collection paths used in the application.
 * This centralizes the collection paths to avoid typos and makes it easier to update them if needed.
 */
enum class FirestoreCollections(val path: String) {
    USERS("users"),
    CLUBS("clubs"),
    EVENTS("events"),
    RSVP("rsvp"),
    FOLLOWS("follows"),
    REVIEWS("reviews"),
    RATINGS("ratings"),
    QUESTIONNAIRES("questionnaires");
}


