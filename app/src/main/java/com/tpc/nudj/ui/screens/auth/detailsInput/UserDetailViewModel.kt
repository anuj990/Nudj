package com.tpc.nudj.ui.screens.auth.detailsInput

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.tpc.nudj.model.NormalUser
import com.tpc.nudj.model.enums.Branch
import com.tpc.nudj.model.enums.Gender
import com.tpc.nudj.repository.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _userDetailsUIState = MutableStateFlow(UserDetailsUIState())
    val userDetailsUIState: StateFlow<UserDetailsUIState> = _userDetailsUIState
    private val auth = FirebaseAuth.getInstance()

    fun updateFirstName(firstName: String) {
        _userDetailsUIState.value = _userDetailsUIState.value.copy(
            firstName = firstName,
            errorMessage = null
        )
    }

    fun updateLastName(lastName: String) {
        _userDetailsUIState.value = _userDetailsUIState.value.copy(
            lastName = lastName,
            errorMessage = null
        )
    }

    fun updateBranch(branch: Branch) {
        _userDetailsUIState.value = _userDetailsUIState.value.copy(
            branch = branch,
            errorMessage = null
        )
    }

    fun updateBatch(batch: Int) {
        _userDetailsUIState.value = _userDetailsUIState.value.copy(
            batch = batch,
            errorMessage = null
        )
    }

    fun updateGender(gender: Gender) {
        _userDetailsUIState.value = _userDetailsUIState.value.copy(
            gender = gender,
            errorMessage = null
        )
    }

    fun validateInputs(): Boolean {
        val currentState = _userDetailsUIState.value

        return when {
            currentState.firstName.isBlank() -> {
                _userDetailsUIState.value = currentState.copy(
                    errorMessage = "First name cannot be empty"
                )
                false
            }
            currentState.lastName.isBlank() -> {
                _userDetailsUIState.value = currentState.copy(
                    errorMessage = "Last name cannot be empty"
                )
                false
            }
            else -> true
        }
    }

    fun saveUserDetails(onSuccess: () -> Unit) {
        if (!validateInputs()) {
            return
        }

        val currentState = _userDetailsUIState.value
        _userDetailsUIState.value = currentState.copy(isLoading = true)

        viewModelScope.launch {
            try {
                val userId = auth.currentUser?.uid ?: throw Exception("User not logged in")

                val user = NormalUser(
                    userid = userId,
                    firstName = currentState.firstName,
                    lastname = currentState.lastName,
                    email = auth.currentUser?.email ?: "",
                    gender = currentState.gender,
                    branch = currentState.branch,
                    batch = currentState.batch,
                    profilePictureUrl = null,
                    bio = ""
                )

                val success = userRepository.saveUser(user)

                if (success) {
                    onSuccess()
                } else {
                    _userDetailsUIState.value = currentState.copy(
                        isLoading = false,
                        errorMessage = "Failed to save user details"
                    )
                }
            } catch (e: Exception) {
                _userDetailsUIState.value = currentState.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "An unknown error occurred"
                )
            }
        }
    }

    fun loadUserDetailsFromArgs(firstName: String, lastName: String, branch: Branch, batch: Int, gender: Gender) {
        _userDetailsUIState.value = UserDetailsUIState(
            firstName = firstName,
            lastName = lastName,
            branch = branch,
            batch = batch,
            gender = gender
        )
    }
}
