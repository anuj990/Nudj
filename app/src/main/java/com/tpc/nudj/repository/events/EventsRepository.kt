package com.tpc.nudj.repository.events

import com.google.firebase.Timestamp
import com.tpc.nudj.model.Event

interface EventsRepository {

    suspend fun addEvent(event: Event) : Boolean

    suspend fun deleteEvent(eventId: String): Boolean

    suspend fun fetchAllUpcomingEvents(): List<Event>

    suspend fun fetchEventByID(eventId: String): Event?

    suspend fun fetchAllEvents(): List<Event>

    suspend fun updateEvent(event: Event) : Boolean

    suspend fun fetchUpcomingEventsWithFilterBatch(
        filterBatch: List<Int>,
    ): List<Event>

}