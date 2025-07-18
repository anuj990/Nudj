package com.tpc.nudj.ui.screens.auth.clubRegistration

import com.tpc.nudj.model.enums.ClubCategory

data class ClubRegistrationUIState(
    val clubName: String = "",
    val clubEmail: String = "@iiitdmj.ac.in",
    val clubCategory: ClubCategory = ClubCategory.MISCELLANEOUS,
    val clubDescription: String = "",
    val clubAchievementsList: List<String> = emptyList(),
    val clubLogo: String? = null,
    val clubAdditionalDetails:String = "",
    val isLoading: Boolean = false,
    val toastMessage: String? = null,
)
