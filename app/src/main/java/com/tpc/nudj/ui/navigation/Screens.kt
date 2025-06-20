package com.tpc.nudj.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface Screens: NavKey {
    @Serializable
    data object LoginScreen: Screens

    @Serializable
    data object DashboardScreen: Screens

}