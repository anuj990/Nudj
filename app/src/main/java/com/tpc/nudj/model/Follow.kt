package com.tpc.nudj.model

import com.google.firebase.Timestamp


data class Follow(
    val followId: String = "",
    val userId: String = "",
    val clubId: String = "",
    val followTimestamp: Timestamp = Timestamp.now()
)
