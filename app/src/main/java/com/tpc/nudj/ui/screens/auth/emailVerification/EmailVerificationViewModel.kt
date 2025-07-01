package com.tpc.nudj.ui.screens.auth.emailVerification


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.tpc.nudj.model.AuthResult
import com.tpc.nudj.model.User
import com.tpc.nudj.repository.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@HiltViewModel
class EmailVerificationViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(EmailVerificationUiState())
    val uiState: StateFlow<EmailVerificationUiState> = _uiState.asStateFlow()

    fun toastMessageShown(){
        _uiState.update { it.copy(message = null) }
    }

    fun checkCurrentUserVerificationStatus(goToLoginScreen: () -> Unit) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val currentUser = FirebaseAuth.getInstance().currentUser
            currentUser?.reload()?.await()

            if (currentUser?.isEmailVerified == true) {
                _uiState.update { it.copy(isLoading = false, message = "Email verified successfully!") }
                goToLoginScreen()
            } else {
                _uiState.update { it.copy(isLoading = false, message = "Email not verified yet. Please check your inbox.") }
            }
        }
    }

    fun sendVerificationEmail() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, canResend = false) }

            authRepository.sendEmailVerification().collect { authResult->
                _uiState.update {
                    it.copy(isLoading = false, message = "Verification email sent", canResend = false)
                }

                // Start countdown timer
                startResendCountdown()
            }
        }
    }

    private fun startResendCountdown() {
        viewModelScope.launch {
            _uiState.update { it.copy(countdown = 30) }

            for (i in 30 downTo 1) {
                _uiState.update { it.copy(countdown = i) }
                delay(1000)
            }

            _uiState.update { it.copy(canResend = true) }
        }
    }
}