package com.tpc.nudj.repository.events

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.tpc.nudj.model.Event
import com.tpc.nudj.utils.FirestoreCollections
import com.tpc.nudj.utils.FirestoreUtils
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class EventsRepositoryImpl @Inject constructor() : EventsRepository {

    private val firestore = FirebaseFirestore.getInstance()

    override suspend fun addEvent(event: Event): Boolean {
        return try {
            val eventCollection = firestore.collection(FirestoreCollections.EVENTS.path)

            val document = eventCollection.document()
            val setEventId = event.copy(eventId = document.id)

            document.set(FirestoreUtils.toMap(setEventId)).await()
            true
        } catch (e: Exception) {
            println("Failed to add the event: ${e.message}")
            false
        }
    }

    override suspend fun deleteEvent(eventId: String): Boolean {
        return try {
            val eventCollection =
                firestore.collection(FirestoreCollections.EVENTS.path).document(eventId)
                    .get()
                    .await()
            if (eventCollection.exists()) {
                val data = eventCollection.data
                if (data != null) {
                    val event = FirestoreUtils.toEvent(data).copy(isDeleted = true)
                    return updateEvent(event)
                }
            }
            return false
        } catch (e: Exception) {
            println("Failed to delete the event: ${e.message}")
            false
        }
    }

    override suspend fun fetchAllUpcomingEvents(): List<Event> {
        return try {
            val eventCollection = firestore.collection(FirestoreCollections.EVENTS.path)
                .get()
                .await()

            eventCollection.documents.mapNotNull {
                val data = it.data
                if (data != null) {
                    FirestoreUtils.toEvent(data)
                } else {
                    null
                }
            }.filter { event ->
                !event.isDeleted && event.eventDates.any {
                    it.startDateTime.toDate().after(Timestamp.now().toDate())
                }
            }

        } catch (e: Exception) {
            emptyList()
        }
    }


    override suspend fun fetchEventByID(eventId: String): Event? {
        return try {
            val snapshot = firestore.collection(FirestoreCollections.EVENTS.path).document(eventId)
                .get()
                .await()
            if (snapshot.exists()) {
                val data = snapshot.data
                if (data != null) {
                    val event = FirestoreUtils.toEvent(data)
                    if (!event.isDeleted) {
                        return event
                    }
                }
            }
            return null
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun fetchAllEvents(): List<Event> {
        return try {
            val eventCollection = firestore.collection(FirestoreCollections.EVENTS.path)
                .get()
                .await()

            eventCollection.documents.mapNotNull {
                val data = it.data
                if (data != null) {
                    FirestoreUtils.toEvent(data)
                } else {
                    null
                }
            }.filter { event ->
                !event.isDeleted
            }

        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun updateEvent(event: Event): Boolean {
        return try {
            firestore.collection(FirestoreCollections.EVENTS.path)
                .document(event.eventId)
                .set(FirestoreUtils.toMap(event))
                .await()
            true
        } catch (e: Exception) {
            println("Failed to update the event: ${e.message}")
            false
        }
    }

    override suspend fun fetchUpcomingEventsWithFilterBatch(
        filterBatch: List<Int>,
    ): List<Event> {
        return try {
            val eventCollection = firestore.collection(FirestoreCollections.EVENTS.path)
                .get()
                .await()

            eventCollection.documents.mapNotNull {
                val data = it.data
                if (data != null) {
                    FirestoreUtils.toEvent(data)
                } else {
                    null
                }
            }.filter { event ->
                !event.isDeleted && event.eventDates.any {
                    it.startDateTime.toDate().after(Timestamp.now().toDate())
                } && (filterBatch.isEmpty() || event.filterBatch.any { it in filterBatch })
            }

        } catch (e: Exception) {
            emptyList()
        }
    }

}