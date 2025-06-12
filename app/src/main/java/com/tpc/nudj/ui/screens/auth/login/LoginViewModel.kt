package com.tpc.nudj.ui.screens.auth.login

import androidx.lifecycle.ViewModel
import com.tpc.nudj.repository.auth.AuthRepository
import com.tpc.nudj.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    val loginUiState = MutableStateFlow(LoginUiState())

    fun onEmailChange(email: String) {
        loginUiState.value = loginUiState.value.copy(email = email)
    }

    fun onPasswordChange(password: String) {
        loginUiState.value = loginUiState.value.copy(password = password)
    }
}