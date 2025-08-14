package com.tpc.nudj.ui.screens.userProfile

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.tpc.nudj.model.Event
import com.tpc.nudj.model.Review
import com.tpc.nudj.model.enums.Branch
import com.tpc.nudj.repository.review.ReviewRepository
import com.tpc.nudj.repository.rsvp.RsvpRepository
import com.tpc.nudj.repository.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileScreenViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val rsvpRepository: RsvpRepository,
    private val reviewRepository: ReviewRepository
): ViewModel() {
    val firebaseAuth = FirebaseAuth.getInstance()
    private val currentUser = firebaseAuth.currentUser

    private val _uiState = MutableStateFlow(UserProfileUiState())
    val uiState = _uiState.asStateFlow()
    val uid = currentUser?.uid


    init {
        initialiseProfile()
    }

    fun initialiseProfile() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    )
            }
            val normalUser = userRepository.fetchUserById(uid?: "")
            try{
                _uiState.update {
                    it.copy(
                        firstName = normalUser?.firstName ?: "",
                        lastName = normalUser?.lastname ?: "",
                        photoUrl = (normalUser?.profilePictureUrl ?: "").toString(),
                        batch = normalUser?.batch ?: 2024,
                        branch = normalUser?.branch ?: Branch.CSE,
                    )
                }
                val pastEvents = rsvpRepository.fetchPastRsvpEvents(uid?: "") // Assuming this is suspend
                _uiState.update {
                    it.copy(
                        pastEventList = pastEvents
                    )
                }
            } catch (e: Exception) {
                Log.e("UserProfileScreenViewModel", "Error fetching user profile", e)
            } finally {
                _uiState.update {
                    it.copy(isLoading = false)
                }
            }
        }
    }



    fun onFirstNameChange(input: String) {
        viewModelScope.launch {
            _uiState.update{
                it.copy(
                    firstName = input
                )
            }
        }
    }

    fun onRatingChange(rating: Int) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    rating = rating
                )
            }
        }
    }

    fun onLastNameChange(input: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    lastName = input
                )
            }
        }
    }

    fun onBatchChange(input: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    batch = input.toInt()
                )
            }

        }
    }

    fun onBranchChange(input: Branch) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    branch = input
                )
            }
        }
    }
    fun onDismissFeedback() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    feedback = "",
                    rating = 0
                )
            }
        }
    }

    suspend fun isRateItEnabled(event: Event): Boolean {
        return reviewRepository.getReviewbyEventandUser(eventId = event.eventId, userId = uid!!).isEmpty()
    }
    fun onFeedbackChange(input: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                     feedback = input
                )
            }
        }

    }
    fun onClickSubmit(clubId: String, eventId: String) {
        viewModelScope.launch {
            currentUser?.uid?.let { userId ->
                reviewRepository.addReview(
                    review = Review(
                        userId = userId,
                        clubId = clubId,
                        eventId = eventId,
                        rating = _uiState.value.rating,
                        review = _uiState.value.feedback
                    )
                )
            }
            initialiseProfile()
        }
    }

    fun onPhotoUriChanged(uri: Uri?) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    photoUrl = uri.toString()
                )
            }
        }
    }

    fun onClickSave() {
        viewModelScope.launch {
            val normalUser = userRepository.fetchUserById(uid?: "")
            userRepository.saveUser(
                user = normalUser!!.copy(
                    userid = currentUser?.uid ?: "",
                    firstName = _uiState.value.firstName,
                    lastname = _uiState.value.lastName,
                    batch = _uiState.value.batch,
                    branch = _uiState.value.branch,
                    profilePictureUrl = _uiState.value.photoUrl
                )
            )
        }
    }
}