package com.tpc.nudj.ui.screens.auth.login

data class LoginUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val toastMessage: String? = null,
    val email: String = "",
    val password: String = ""
)
