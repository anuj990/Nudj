package com.tpc.nudj.ui.screens.homeScreen

import com.google.firebase.Timestamp
import java.util.Date

data class HomeUiState(
    val eventId: String,
    val clubId: String = "Club Name",
    val eventName: String = "Event Name",
    val eventDescription: String = "",
    val eventVenue: String = "Venue",
    val eventBannerUrl: String = "",
    val organizerName: String = "Organizer Name",
    val organizerContactNumber: String = "+91 123456XXXX",
    val eventDates: List<EventTime> = emptyList(),
    val faqs: List<FAQS> = emptyList(),
    val isDeleted: Boolean = false,
    val creationTimestamp: Timestamp,
    val lastUpdatedTimestamp: Timestamp
)

data class EventTime(
    val startDateTime: Timestamp = Timestamp(
        Date(
            2024,
            0,
            1,
            12,
            0
        )
    ),
    val estimatedDuration: String
)

data class FAQS(
    val question: String,
    val answer: String
)