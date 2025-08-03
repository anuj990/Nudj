package com.tpc.nudj.ui.screens.ClubDashBoard.Data

import androidx.annotation.DrawableRes
import com.tpc.nudj.R

sealed class Screens(
    val route: String,
    val label: String,
    @DrawableRes val iconRes: Int
) {
    object Home : Screens("home", "HOME", iconRes = R.drawable.home_image)
    object Event : Screens("event", "EVENT", iconRes = R.drawable.event)
    object Profile : Screens("profile", "PROFILE", iconRes = R.drawable.profile_image)

    companion object {
        val items = listOf(Home, Event, Profile)
    }
}
val items = Screens.items.map {
    BottomNavItem(label = it.label, iconRes = it.iconRes, route = it.route)
}
