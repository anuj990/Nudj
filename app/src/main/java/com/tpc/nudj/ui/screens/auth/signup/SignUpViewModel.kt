package com.tpc.nudj.ui.screens.auth.signup

import androidx.lifecycle.ViewModel
import com.tpc.nudj.repository.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    val signUpUiState = MutableStateFlow(SignUpUiState())

    fun onEmailChange(email: String) {
        signUpUiState.value = signUpUiState.value.copy(email = email)
    }

    fun onPasswordChange(password: String) {
        signUpUiState.value = signUpUiState.value.copy(password = password)
    }

    fun onConfirmPasswordChange(confirmPassword: String) {
        signUpUiState.value = signUpUiState.value.copy(confirmPassword = confirmPassword)
    }
}