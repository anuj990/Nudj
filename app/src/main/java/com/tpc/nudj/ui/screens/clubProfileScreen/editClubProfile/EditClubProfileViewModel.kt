package com.tpc.nudj.ui.screens.clubProfileScreen.editClubProfile

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.tpc.nudj.model.ClubUser
import com.tpc.nudj.model.enums.ClubCategory
import com.tpc.nudj.repository.user.UserRepository
import com.tpc.nudj.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditClubProfileViewModel @Inject constructor(
    val userRepository: UserRepository
) : BaseViewModel<EditClubProfileUIState>() {
    private val _uiState = MutableStateFlow(EditClubProfileUIState())
    val profileUiState: StateFlow<EditClubProfileUIState> = _uiState.asStateFlow()

    override val uiState: StateFlow<EditClubProfileUIState>
        get() = profileUiState

    override fun createUiStateFlow(): StateFlow<EditClubProfileUIState> {
        return _uiState.asStateFlow()
    }

    init {
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

    fun clubNameInput(input: String) {
        _uiState.update {
            it.copy(
                tempClubName = input
            )
        }
    }


    fun clubCategoryInput(input: ClubCategory) {
        _uiState.update {
            it.copy(
                tempClubCategory = input
            )
        }
    }

    fun clubDescriptionInput(input: String) {

        _uiState.update {
            it.copy(
                tempDescription = input
            )
        }
    }

    fun addAchievement(input: String) {
        if (input.isNotBlank()) {
            _uiState.update {
                it.copy(tempAchievementsList = it.tempAchievementsList + input.trim())
            }
        }
    }

    fun removeAchievement(index: Int) {
        _uiState.update {
            val updatedList = it.tempAchievementsList.toMutableList().apply {
                removeAt(index)
            }
            it.copy(
                tempAchievementsList = updatedList
            )
        }
    }

    fun clubLogoImageInput(uri: Uri?) {
        _uiState.update {
            it.copy(
                tempClubLogo = uri.toString()  //  For now just storing the uri in string format in the ClubLogo
            )
        }
    }

    fun clubAdditionalDetailsInput(input: String) {
        _uiState.update {
            it.copy(
                tempAdditionalDetails = input
            )
        }
    }

    fun emptyTempClubLogo() {
        _uiState.update {
            it.copy(
                tempClubLogo = null
            )
        }
    }

    suspend fun fetchCurrentClub() {
        _uiState.update {
            it.copy(
                isLoading = true
            )
        }
        val clubDetails = userRepository.fetchCurrentClub()
        if (clubDetails == null) {
            _uiState.update {
                it.copy(
                    isLoading = false,
                )
            }
            emitError("No user found!!")
            return
        }
        _uiState.update {
            it.copy(
                tempClubName = clubDetails.clubName,
                tempClubCategory = clubDetails.clubCategory,
                tempDescription = clubDetails.description,
                tempAchievementsList = clubDetails.achievementsList,
                tempClubLogo = clubDetails.clubLogo,
                tempAdditionalDetails = clubDetails.additionalDetails
            )
        }
        _uiState.update {
            it.copy(
                isLoading = false
            )
        }
    }


    fun onSaveClicked(toClubProfileScreen: () -> Unit) {
        val firebase = FirebaseAuth.getInstance()
        val clubName = _uiState.value.tempClubName
        val clubEmail = firebase.currentUser?.email
        val clubCategory = _uiState.value.tempClubCategory
        val clubLogo = _uiState.value.tempClubLogo
        val clubDescription = _uiState.value.tempDescription
        val clubAchievementsList = _uiState.value.tempAchievementsList
        val clubAdditionalDetails = _uiState.value.tempAdditionalDetails

        when {
            clubEmail.isNullOrBlank() -> {
                emitError("No user email found.")
                return
            }

            clubName.isBlank() -> {
                emitError("Club name cannot be empty.")
                return
            }

            clubLogo.isNullOrBlank() -> {
                emitError("Please upload a club logo.")
                return
            }

            clubDescription.isBlank() -> {
                emitError("Club description cannot be empty.")
                return
            }
        }
        _uiState.update {
            it.copy(
                isLoading = true,
            )
        }
        clearErrorFlow()
        clearMsgFlow()
        viewModelScope.launch {
            try {
                val clubId = firebase.currentUser?.uid ?: throw IllegalStateException("User not logged in")
                val clubDetails = ClubUser(
                    clubId = clubId,
                    clubName = clubName,
                    clubEmail = clubEmail,
                    clubCategory = clubCategory,
                    clubLogo = clubLogo,
                    description = clubDescription,
                    achievementsList = clubAchievementsList,
                    additionalDetails = clubAdditionalDetails
                )
                userRepository.saveClub(clubDetails)
                _uiState.update {
                    it.copy(
                        isLoading = false
                    )
                }
                viewModelScope.launch {
                    emitMsg("Club details saved Successfully!!")
                    delay(500)
                    toClubProfileScreen()
                }
            } catch (e: Exception) {
                emitError("Failed to save club details, please try again")
            }
        }
    }

}