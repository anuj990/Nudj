package com.tpc.nudj.ui.screens.ClubDashBoard.Data

import androidx.annotation.DrawableRes
import com.tpc.nudj.R

sealed class ClubDashBoardScreens(
    val route: String,
    val label: String,
    @DrawableRes val iconRes: Int
) {
    object Home : ClubDashBoardScreens("home", "HOME", iconRes = R.drawable.home_image)
    object Event : ClubDashBoardScreens("event", "EVENT", iconRes = R.drawable.event)
    object Profile : ClubDashBoardScreens("profile", "PROFILE", iconRes = R.drawable.profile_image)

    companion object {
        val items = listOf(Home, Event, Profile)
    }
}


