package com.tpc.nudj

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface Screens: NavKey {

    @Serializable
    data object BlueScreen: Screens

    @Serializable
    data object GreenScreen: Screens

    @Serializable
    data object RedScreen: Screens
}