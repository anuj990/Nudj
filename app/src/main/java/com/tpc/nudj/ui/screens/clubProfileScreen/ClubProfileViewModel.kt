package com.tpc.nudj.ui.screens.clubProfileScreen

import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.tpc.nudj.repository.events.EventsRepository
import com.tpc.nudj.repository.user.UserRepository
import com.tpc.nudj.ui.BaseViewModel
import com.tpc.nudj.utils.FirestoreCollections
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class ClubProfileViewModel @Inject constructor(
    val userRepository: UserRepository,
    val eventRepository: EventsRepository,
) : BaseViewModel<ClubProfileUIState>() {
    private val _uiState = MutableStateFlow(ClubProfileUIState())
    val profileUiState: StateFlow<ClubProfileUIState> = _uiState.asStateFlow()

    override val uiState: StateFlow<ClubProfileUIState>
        get() = profileUiState

    override fun createUiStateFlow(): StateFlow<ClubProfileUIState> {
        return _uiState.asStateFlow()
    }

    init {
        _uiState.update {
            it.copy(
                isLoading = true
            )
        }
        viewModelScope.launch {
            errorFlow.collect { error ->
                _uiState.update {
                    it.copy(toastMessage = error)
                }
            }
        }

        viewModelScope.launch {
            successMsgFlow.collect { msg ->
                _uiState.update {
                    it.copy(toastMessage = msg)
                }
            }
        }
    }

    fun clearToastMessage() {
        clearMsgFlow()
        clearErrorFlow()
    }

    fun fetchCurrentClub() {
        viewModelScope.launch {
            val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
            if (currentUserId == null) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                    )
                }
                emitError("No user found!!")
                return@launch
            }
            val clubDetails = userRepository.fetchCurrentClub()
            if (clubDetails == null) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                    )
                }
                emitError("No user details found!!")
                return@launch
            }
            val pastEvents = fetchPastEvents(currentUserId)
            _uiState.update {
                it.copy(
                    clubName = clubDetails.clubName,
                    clubCategory = clubDetails.clubCategory,
                    description = clubDetails.description,
                    achievementsList = clubDetails.achievementsList,
                    clubLogo = clubDetails.clubLogo,
                    tempClubLogo = clubDetails.clubLogo,
                    additionalDetails = clubDetails.additionalDetails,
                    clubEvents = pastEvents,
                    isLoading = false
                )
            }
        }
    }

    fun emptyClubLogo() {
        _uiState.update {
            it.copy(
                clubLogo = null
            )
        }
    }


    suspend fun fetchPastEvents(currentUserId: String): List<PastEventsDetails> {
        val firestore = FirebaseFirestore.getInstance()
        val allEvents = eventRepository.fetchAllEvents().filter {
            it.clubId == currentUserId &&
                    it.eventDates.firstOrNull()?.startDateTime?.toDate()
                        ?.before(Timestamp.now().toDate()) == true
        }

        return allEvents.map { event ->
            val clubEvent = firestore.collection(FirestoreCollections.RSVP.path)
                .whereEqualTo("eventId", event.eventId)
                .get()
                .await()
            val rsvpCount = clubEvent.size()
            PastEventsDetails(
                eventId = event.eventId,
                eventName = event.eventName,
                eventDate = event.eventDates.first().startDateTime,
                rsvp = rsvpCount
            )
        }
    }

}