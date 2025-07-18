package com.tpc.nudj.ui.screens.auth.clubRegistration

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.tpc.nudj.model.ClubUser
import com.tpc.nudj.model.enums.ClubCategory
import com.tpc.nudj.repository.user.UserRepository
import com.tpc.nudj.ui.BaseViewModel
import com.tpc.nudj.utils.FirestoreCollections
import com.tpc.nudj.utils.FirestoreUtils
import com.tpc.nudj.utils.Validator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ClubRegistrationViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel<ClubRegistrationUIState>() {


    private val _clubRegistrationUiState = MutableStateFlow(ClubRegistrationUIState())
    val clubRegistrationUiState: StateFlow<ClubRegistrationUIState> =
        _clubRegistrationUiState.asStateFlow()

    override val uiState: StateFlow<ClubRegistrationUIState>
        get() = clubRegistrationUiState

    override fun createUiStateFlow(): StateFlow<ClubRegistrationUIState> {
        return _clubRegistrationUiState.asStateFlow()
    }

    init {
        viewModelScope.launch {
            errorFlow.collect { error ->
                _clubRegistrationUiState.update {
                    it.copy(toastMessage = error)
                }
            }
        }

        viewModelScope.launch {
            successMsgFlow.collect { msg ->
                _clubRegistrationUiState.update {
                    it.copy(toastMessage = msg)
                }
            }
        }
    }

    fun clubNameInput(input: String) {
        _clubRegistrationUiState.update {
            it.copy(
                clubName = input
            )
        }
    }

//    fun clubEmailInput(input: String) {
//        _clubRegistrationUiState.update {
//            it.copy(
//                clubEmail = input
//            )
//        }
//    }

    fun clubCategoryInput(input: ClubCategory) {
        _clubRegistrationUiState.update {
            it.copy(
                clubCategory = input
            )
        }
    }

    fun clubDescriptionInput(input: String) {
        _clubRegistrationUiState.update {
            it.copy(
                clubDescription = input
            )
        }
    }

    fun addAchievement(input: String) {
        if (input.isNotBlank()) {
            _clubRegistrationUiState.update {
                it.copy(clubAchievementsList = it.clubAchievementsList + input.trim())
            }
        }
    }

    fun removeAchievement(index: Int) {
        _clubRegistrationUiState.update {
            val updatedList = it.clubAchievementsList.toMutableList().apply {
                removeAt(index)
            }
            it.copy(
                clubAchievementsList = updatedList
            )
        }
    }

    fun clubLogoImageInput(uri: Uri?) {
        _clubRegistrationUiState.update {
            it.copy(
                clubLogo = uri.toString()  //  For now just storing the uri in string format in the ClubLogo
            )
        }
    }

    fun clubAdditionalDetailsInput(input: String) {
        _clubRegistrationUiState.update {
            it.copy(
                clubAdditionalDetails = input
            )
        }
    }

    fun clearToastMessage() {
        clearMsgFlow()
        clearErrorFlow()
    }

    fun onSaveClicked(toDashboardScreen: () -> Unit) {
        val clubName = _clubRegistrationUiState.value.clubName
        val clubEmail = _clubRegistrationUiState.value.clubEmail
        val clubCategory = _clubRegistrationUiState.value.clubCategory
        val clubLogo = _clubRegistrationUiState.value.clubLogo
        val clubDescription = _clubRegistrationUiState.value.clubDescription
        val clubAchievementsList = _clubRegistrationUiState.value.clubAchievementsList
        val clubAdditionalDetails = _clubRegistrationUiState.value.clubAdditionalDetails

        when {
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

            clubAchievementsList.isEmpty() -> {
                emitError("Please enter at least one achievement.")
                return
            }
        }

        _clubRegistrationUiState.update {
            it.copy(
                isLoading = true,
            )
        }
        clearErrorFlow()
        clearMsgFlow()
        Validator.isValidEmail(email = clubEmail, onSuccess = {
            viewModelScope.launch {
                try {
                    val clubId = clubName
                        .trim()
                        .replace(" ", "").lowercase() + "_id"
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
                    _clubRegistrationUiState.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                    viewModelScope.launch {
                        emitMsg("Club saved Successfully, Welcome to Nudj")
                        delay(1000)
                        toDashboardScreen()
                    }
                } catch (e: Exception) {
                    emitError("Failed to save club details, please try again")
                }
            }
        }, onFailure = {
            _clubRegistrationUiState.update {
                it.copy(
                    isLoading = false,
                )
            }
            emitError("Please enter a valid college email id")
        })

    }
}