package com.tpc.nudj.ui.screens.clubProfileScreen.editClubProfile

import com.google.firebase.Timestamp
import com.tpc.nudj.model.enums.ClubCategory
import com.tpc.nudj.ui.screens.clubProfileScreen.PastEventsDetails

data class EditClubProfileUIState(
    val tempClubName: String = "",
    val tempClubCategory: ClubCategory = ClubCategory.MISCELLANEOUS,
    val tempDescription: String = "",
    val tempAchievementsList: List<String> = emptyList(),
    val tempClubLogo:String?=null,
    val tempClubEvents:List<PastEventsDetails> = emptyList(),
    val tempAdditionalDetails:String = "",
    val isLoading:Boolean = false,
    val toastMessage:String?=null,
)
