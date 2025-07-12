package com.tpc.nudj.ui.screens.auth.detailsInput

import com.tpc.nudj.model.enums.Branch
import com.tpc.nudj.model.enums.Gender

data class UserDetailsUIState(
    val firstName: String = "",
    val lastName: String = "",
    val branch: Branch = Branch.CSE,
    val batch: Int = 2025,
    val gender: Gender = Gender.PREFER_NOT_TO_DISCLOSE,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)