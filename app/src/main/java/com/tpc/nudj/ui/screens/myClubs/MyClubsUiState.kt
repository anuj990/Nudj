package com.tpc.nudj.ui.screens.myClubs

data class MyClubsUiState(
    val isLoading: Boolean = false,
    val isSaved: Boolean = false,
    val toastMessage: String? = null
)
