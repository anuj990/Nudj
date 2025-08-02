package com.tpc.nudj.ui.screens.auth.detailsInput

import com.tpc.nudj.model.enums.Branch
import com.tpc.nudj.model.enums.Gender
import java.time.LocalDateTime
import java.time.Month

data class UserDetailsUIState(
    val firstName: String = "",
    val lastName: String = "",
    val branch: Branch = Branch.CSE,
    val batch: Int = if (LocalDateTime.now().month >= Month.AUGUST) LocalDateTime.now().year else LocalDateTime.now().year - 1,
    val gender: Gender = Gender.PREFER_NOT_TO_DISCLOSE,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)