package com.tpc.nudj.ui.screens.auth.login

import androidx.lifecycle.ViewModel
import com.tpc.nudj.repository.auth.AuthRepository
import com.tpc.nudj.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _loginUiState = MutableStateFlow(LoginUiState())
    val loginUiState: StateFlow<LoginUiState> = _loginUiState.asStateFlow()
    fun emailInput(input: String) {
        _loginUiState.update {
            it.copy(
                email = input,
            )
        }
    }

    fun passwordInput(input: String) {
        _loginUiState.update {
            it.copy(
                password = input,
            )
        }
    }

    fun clearError(){
        _loginUiState.update {
            it.copy(
                toastMessage = null
            )
        }
    }
    fun onForgetPasswordClicked() {

    }

    fun onLoginClicked() {
        val email = _loginUiState.value.email
        val password = _loginUiState.value.password

        if (email.isBlank() || password.isBlank()) {
            _loginUiState.update {
                it.copy(toastMessage = "Email and password cannot be empty")
            }
            return
        }
        _loginUiState.update { it.copy(isLoading = true, toastMessage = null) }
    }

    fun onGoogleClicked() {

    }
}