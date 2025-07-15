package com.tpc.nudj.ui.screens.auth.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tpc.nudj.model.AuthResult
import com.tpc.nudj.repository.auth.AuthRepository
import com.tpc.nudj.repository.auth.GoogleSignInClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _loginUiState = MutableStateFlow(LoginUiState())
    val loginUiState: StateFlow<LoginUiState> = _loginUiState.asStateFlow()

    fun emailInput(input: String) {
        _loginUiState.update { it.copy(email = input) }
    }

    fun passwordInput(input: String) {
        _loginUiState.update { it.copy(password = input) }
    }

    fun clearError() {
        _loginUiState.update { it.copy(toastMessage = null) }
    }

    fun onForgetPasswordClicked(onNavigateToForgotPassword: () -> Unit) {
        onNavigateToForgotPassword()
    }

    fun onLoginClicked(
        onNavigateToUserDetailsFetchLoadingScreen: () -> Unit,
        goToEmailVerificationScreen: () -> Unit
    ) {
        val email = _loginUiState.value.email
        val password = _loginUiState.value.password

        if (email.isBlank() || password.isBlank()) {
            _loginUiState.update { it.copy(toastMessage = "Email and password cannot be empty") }
            return
        }

        _loginUiState.update { it.copy(isLoading = true, toastMessage = null) }

        viewModelScope.launch {
            authRepository.signInWithEmailAndPassword(email, password).collect { result ->
                when (result) {
                    is AuthResult.Loading -> _loginUiState.update { it.copy(isLoading = true) }

                    is AuthResult.Success -> {
                        if (result.user.isEmailVerified) {
                            _loginUiState.update {
                                it.copy(isLoading = false, toastMessage = "Login successful")
                            }
                            onNavigateToUserDetailsFetchLoadingScreen()
                        } else {
                            _loginUiState.update {
                                it.copy(
                                    isLoading = false,
                                    toastMessage = "Please verify your email"
                                )
                            }
                        }
                    }

                    is AuthResult.VerificationNeeded -> {
                        _loginUiState.update {
                            it.copy(
                                isLoading = false,
                                toastMessage = "Email not verified. Please verify."
                            )
                        }
                        goToEmailVerificationScreen()
                    }

                    is AuthResult.Error -> {
                        _loginUiState.update {
                            it.copy(isLoading = false, toastMessage = result.message)
                        }
                    }

                    else -> {}
                }
            }
        }
    }

    fun onGoogleClicked(context: Context, onSuccessfulSignIn: () -> Unit) {
        _loginUiState.update { it.copy(isLoading = true) }

        val googleSignInClient = GoogleSignInClient(context)

        viewModelScope.launch {
            val idToken = googleSignInClient.signIn()
            Log.i("LoginViewModel", "Google ID Token: $idToken")
            if (idToken == null) {
                _loginUiState.update {
                    it.copy(isLoading = false, toastMessage = "Google Sign-In failed")
                }
            } else {
                authRepository.signInWithGoogle(idToken).collect { result ->
                    when (result) {
                        is AuthResult.Success -> {
                            _loginUiState.update {
                                it.copy(isLoading = false, toastMessage = "Sign in successful")
                            }
                            onSuccessfulSignIn()
                        }

                        is AuthResult.Error -> {
                            _loginUiState.update {
                                it.copy(isLoading = false, toastMessage = result.message)
                            }
                        }

                        else -> {}
                    }
                }
            }
        }
    }
}
