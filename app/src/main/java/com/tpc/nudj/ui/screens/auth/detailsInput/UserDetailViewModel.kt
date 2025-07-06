package com.tpc.nudj.ui.screens.auth.detailsInput

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.tpc.nudj.model.NormalUser
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

    fun updateFirstName(firstName: String) {
        _userDetailsUIState.value = _userDetailsUIState.value.copy(firstName = firstName)
    }

    fun updateLastName(lastName: String) {
        _userDetailsUIState.value = _userDetailsUIState.value.copy(lastName = lastName)
    }
    fun updateBranch(branch: String) {
        _userDetailsUIState.value = _userDetailsUIState.value.copy(branch = branch)
    }

    fun updateBatch(batch: String) {
        _userDetailsUIState.value = _userDetailsUIState.value.copy(batch = batch)
    }

    fun saveUserToFirestore(email: String) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val state = userDetailsUIState.value

        val newUser = NormalUser(
            userid = uid,
            firstName = state.firstName,
            lastname = state.lastName,
            email = email,
//            branch = state.branch,
//            batch = state.batch,
            profilePictureUrl = "",
            bio = "Hey there! I'm new"
        )

        viewModelScope.launch {
            val success = userRepository.saveUser(newUser)
        }
    }
}
