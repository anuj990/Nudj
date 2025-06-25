package com.tpc.nudj.ui.screens.auth.detailsInput

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor() : ViewModel() {
    private val _userDetailsUIState =
        MutableStateFlow(UserDetailsUIState()) //we using this instead of mutablestateof because we have to manage a complex input not a single input data.
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

}