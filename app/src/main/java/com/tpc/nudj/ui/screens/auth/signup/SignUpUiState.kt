package com.tpc.nudj.ui.screens.auth.signup

data class SignUpUiState (
    val isLoading: Boolean = false,
    val error: String? = null,
    val toastMessage: String? = null,

    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
)