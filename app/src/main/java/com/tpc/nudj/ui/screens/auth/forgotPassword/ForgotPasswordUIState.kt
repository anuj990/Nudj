package com.tpc.nudj.ui.screens.auth.forgotPassword

data class ForgetPasswordUiState(
    val email:String = "",
    val isLoading:Boolean = false,
    val toastMessage:String? = null,
)