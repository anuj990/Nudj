package com.tpc.nudj.model

import com.google.firebase.Timestamp


data class RSVP(
    val rsvpId: String = "",
    val userId: String = "",
    val eventId: String = "",
    val rsvpTimestamp: Timestamp = Timestamp.now()
)
