package com.tpc.nudj

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class UserDetailViewModel : ViewModel() {
    private val _userDetails = MutableStateFlow(UserDetails()) //we using this instead of mutablestateof because we have to manage a complex input not a single input data.
    val userDetails: StateFlow<UserDetails> = _userDetails

    fun updateFirstName(firstName: String) {
        _userDetails.value = _userDetails.value.copy(firstName = firstName)
    }

    fun updateLastName(lastName: String) {
        _userDetails.value = _userDetails.value.copy(lastName = lastName)
    }
    fun updateBranch(branch: String) {
        _userDetails.value = _userDetails.value.copy(branch = branch)
    }

    fun updateBatch(batch: String) {
        _userDetails.value = _userDetails.value.copy(batch = batch)
    }


}