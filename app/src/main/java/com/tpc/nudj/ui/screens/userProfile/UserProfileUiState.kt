package com.tpc.nudj.ui.screens.userProfile

import android.net.Uri
import com.tpc.nudj.model.Event
import com.tpc.nudj.model.enums.Branch

data class UserProfileUiState(
    val isLoading: Boolean = false,
    val firstName: String = "",
    val lastName: String = "",
    val photoUrl: String = "",
    val batch: Int = 2024,
    val pastEventList: List<Event> = emptyList(),
    val feedback: String = "",
    val rating: Int = 0,
    val branch: Branch = Branch.CSE
)
