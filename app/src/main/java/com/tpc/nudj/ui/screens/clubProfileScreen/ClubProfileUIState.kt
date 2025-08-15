package com.tpc.nudj.ui.screens.clubProfileScreen

import com.google.firebase.Timestamp
import com.tpc.nudj.model.RSVP
import com.tpc.nudj.model.enums.ClubCategory

data class ClubProfileUIState(
    val clubName: String = "",
    val clubCategory: ClubCategory = ClubCategory.MISCELLANEOUS,
    val description: String = "",
    val achievementsList: List<String> = emptyList(),
    val clubLogo:String?=null,
    val tempClubLogo:String?=null,
    val clubEvents:List<PastEventsDetails> = emptyList(),
    val additionalDetails:String = "",
    val isLoading:Boolean = false,
    val toastMessage:String?=null,
)


data class PastEventsDetails(
    val eventId:String="",
    val eventDate: Timestamp = Timestamp.now(),
    val eventName:String = "",
    val rsvp: Int = 0
)