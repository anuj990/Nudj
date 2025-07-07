package com.tpc.nudj.model

import com.google.firebase.Timestamp


data class Review(
    val reviewId: String = "",
    val userId: String = "",
    val clubId: String = "",
    val eventId: String = "",
    val rating: Int = 0,
    val review: String? = null,
    val createdAt: Timestamp = Timestamp.now()
)
