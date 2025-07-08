package com.tpc.nudj.model

import com.google.firebase.Timestamp
import java.util.Date


data class Event(
    val eventId: String = "",
    val clubId: String = "",
    val eventName: String = "",
    val eventDescription: String = "",
    val eventBannerUrl: String? = null,
    val organizerName: String = "",
    val organizerContactNumber: String = "",
    val eventDates: List<EventDate> = emptyList(),
    val faqs: List<EventFAQ> = emptyList(),
    //Empty list means no filter applied, if filter is applied, it will be a non-empty list of batch numbers
    val filterBatch: List<Int> = emptyList(),
    val isDeleted: Boolean = false,
    val creationTimestamp: Timestamp = Timestamp.now(),
    val lastUpdatedTimestamp: Timestamp = Timestamp.now()
)

data class EventDate(
    val startDateTime: Timestamp = Timestamp.now(),
    val estimatedDuration: String = ""
)


data class EventFAQ(
    val question: String = "",
    val answer: String = ""
)
