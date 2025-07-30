package com.tpc.nudj.ui.screens.eventRegistration

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.tpc.nudj.model.Event
import com.tpc.nudj.model.EventDate
import com.tpc.nudj.model.EventFAQ
import com.tpc.nudj.repository.events.EventsRepository
import com.tpc.nudj.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class EventRegistrationViewModel @Inject constructor(
    private val eventsRepository: EventsRepository
) : BaseViewModel<EventRegistrationUiState>() {

    private val _eventRegistrationUiState = MutableStateFlow(EventRegistrationUiState())
    val eventRegistrationUiState: StateFlow<EventRegistrationUiState> =
        _eventRegistrationUiState.asStateFlow()

    override val uiState: StateFlow<EventRegistrationUiState>
        get() = eventRegistrationUiState

    override fun createUiStateFlow(): StateFlow<EventRegistrationUiState> {
        return _eventRegistrationUiState.asStateFlow()
    }


    init {
        viewModelScope.launch {
            errorFlow.collect { error ->
                _eventRegistrationUiState.update {
                    it.copy(
                        toastMessage = error
                    )
                }
            }
        }

        viewModelScope.launch {
            successMsgFlow.collect { msg ->
                _eventRegistrationUiState.update {
                    it.copy(
                        toastMessage = msg
                    )
                }
            }
        }

    }

    fun eventNameInput(input: String) {
        _eventRegistrationUiState.update {
            it.copy(
                eventName = input
            )
        }
    }

    fun organizerNameInput(input: String) {
        _eventRegistrationUiState.update {
            it.copy(
                organizerName = input
            )
        }
    }

    fun organizerContactNumberInput(input: String) {
        _eventRegistrationUiState.update {
            it.copy(
                organizerContactNumber = input
            )
        }
    }

    fun eventDescriptionInput(input: String) {
        _eventRegistrationUiState.update {
            it.copy(
                eventDescription = input
            )
        }
    }

    fun eventVenueInput(input: String) {
        _eventRegistrationUiState.update {
            it.copy(
                eventVenue = input
            )
        }
    }

    fun eventBannerInput(uri: Uri?) {
        _eventRegistrationUiState.update {
            it.copy(
                eventBanner = uri.toString()  //  For now just storing the uri in string format in the EventBanner
            )
        }
    }

    fun addEventDate(input: EventRegistrationDate) {
        _eventRegistrationUiState.update {
            it.copy(
                eventDates = it.eventDates + input
            )
        }
    }

    fun removeEventDate(index: Int) {
        _eventRegistrationUiState.update {
            val updatedList = it.eventDates.toMutableList().apply {
                removeAt(index)
            }
            it.copy(
                eventDates = updatedList
            )
        }
    }

    fun changeBatchSelection(batch: String, batchList: Set<String>) {
        _eventRegistrationUiState.update {
            val currentSet = it.selectedBatches.toMutableSet()
            if (batch == "All Batches") {
                if (currentSet.contains(batch)) {
                    currentSet.clear()
                } else {
                    currentSet.clear()
                    currentSet.addAll(batchList)
                }
            } else if (currentSet.contains(batch)) {
                currentSet.remove(batch)
                currentSet.remove("All Batches")
            } else {
                currentSet.add(batch)
            }
            val nonAll = batchList.filter { it != "All Batches" }.toSet()
            if (currentSet.containsAll(nonAll)) {
                currentSet.add("All Batches")
            }
            it.copy(
                selectedBatches = currentSet
            )
        }
    }

    fun convertSelectedBatchesToInt(setOfBatches: Set<String>): List<Int> {
        val batchesList = setOfBatches.filter {
            it != "All Batches"
        }.mapNotNull {
            it.toIntOrNull()
        }
            .toMutableList()
        if (batchesList.isEmpty()) {
            val currentYear = LocalDateTime.now().year
            val yearList = listOf(
                currentYear,
                currentYear - 1,
                currentYear - 2,
                currentYear - 3
            )
            batchesList.addAll(yearList)
        }
        return batchesList
    }

    fun addFaqs(input: EventRegistrationFAQ) {
        _eventRegistrationUiState.update {
            it.copy(
                faqs = it.faqs + input
            )
        }
    }

    fun removeFaqs(index: Int) {
        _eventRegistrationUiState.update {
            val updatedList = it.faqs.toMutableList().apply {
                removeAt(index)
            }
            it.copy(
                faqs = updatedList
            )
        }
    }

    fun clearToastMessage() {
        _eventRegistrationUiState.update {
            it.copy(
                toastMessage = null
            )
        }
    }

    fun onSaveClicked(toPreviousScreen:()->Unit) {
        val firebase = FirebaseAuth.getInstance()
        val clubId = firebase.currentUser?.uid
        val eventName = _eventRegistrationUiState.value.eventName
        val organizerName = _eventRegistrationUiState.value.organizerName
        val organizerContactNumber = _eventRegistrationUiState.value.organizerContactNumber
        val eventDescription = _eventRegistrationUiState.value.eventDescription
        val eventVenue = _eventRegistrationUiState.value.eventVenue
        val eventBanner = _eventRegistrationUiState.value.eventBanner
        val eventDatesList = _eventRegistrationUiState.value.eventDates
        val filterBatch =
            convertSelectedBatchesToInt(_eventRegistrationUiState.value.selectedBatches)
        val faqs = _eventRegistrationUiState.value.faqs

        when {
            clubId.isNullOrBlank() -> {
                emitError("No user found.")
                return
            }

            eventName.isBlank() -> {
                emitError("Event name cannot be empty.")
                return
            }

            organizerName.isBlank() -> {
                emitError("Organizer name cannot be empty.")
                return
            }

            eventDescription.isBlank() -> {
                emitError("Please give some description about the event.")
                return
            }

            eventVenue.isBlank() -> {
                emitError("Please enter the event venue.")
                return
            }

            eventDatesList.isEmpty() -> {
                emitError("Please enter at least one event date.")
                return
            }

        }

        _eventRegistrationUiState.update {
            it.copy(
                isLoading = true
            )
        }
        clearErrorFlow()
        clearMsgFlow()
        viewModelScope.launch {
            try {
                val eventDetails = Event(
                    clubId = clubId,
                    eventName = eventName,
                    eventDescription = eventDescription,
                    eventBannerUrl = eventBanner,
                    eventVenue = eventVenue,
                    eventDates = eventDatesList.map {
                        EventDate(
                            startDateTime = it.startDateTime,
                            estimatedDuration = it.estimatedDuration
                        )
                    },
                    filterBatch = filterBatch,
                    faqs = faqs.map {
                        EventFAQ(
                            question = it.question,
                            answer = it.answer
                        )
                    },
                    organizerName = organizerName,
                    organizerContactNumber = "+91-${organizerContactNumber}",
                    creationTimestamp = Timestamp.now(),
                    lastUpdatedTimestamp = Timestamp.now()
                )
                eventsRepository.addEvent(eventDetails)
                _eventRegistrationUiState.update {
                    it.copy(
                        isLoading = false
                    )
                }
                viewModelScope.launch {
                    emitMsg("Event saved successfully!")
                    delay(1000)
                    toPreviousScreen()
                }
            } catch (_: Exception) {
                emitError("Failed to save event details, please try again")
            }
        }

    }

}