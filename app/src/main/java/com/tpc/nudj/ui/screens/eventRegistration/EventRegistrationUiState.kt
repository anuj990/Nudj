package com.tpc.nudj.ui.screens.eventRegistration

import com.google.firebase.Timestamp

data class EventRegistrationUiState(
    val eventName: String = "",
    val organizerName: String = "",
    val organizerContactNumber: String = "",
    val eventDescription: String = "",
    val eventVenue: String = "",
    val eventBanner: String? =null,
    val eventDates: List<EventRegistrationDate> = emptyList(),
    val faqs: List<EventRegistrationFAQ> = emptyList(),
    val selectedBatches: Set<String> = emptySet(),
    val isLoading: Boolean = false,
    val toastMessage: String? = null
)


data class EventRegistrationDate(
    val startDateTime: Timestamp = Timestamp.now(),
    val estimatedDuration: String = ""
)


data class EventRegistrationFAQ(
    val question: String = "",
    val answer: String = ""
)