package com.tpc.nudj.ui.screens.auth.emailVerification


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.tpc.nudj.EmailVerificationCredentials
import com.tpc.nudj.repository.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
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

    private var resetTimer: Job? = null

    fun toastMessageShown() {
        _uiState.update { it.copy(message = null) }
    }

    fun checkCurrentUserVerificationStatus(
        emailVerified: () -> Unit = {},
        toResetPasswordScreen: () -> Unit = {}
    ) {
        val mode = EmailVerificationCredentials.mode
        val oobCode = EmailVerificationCredentials.oobCode
        val firebaseAuth = FirebaseAuth.getInstance()

        _uiState.update {
            it.copy(
                isLoading = true,
                message = null
            )
        }

        if (mode == "verifyEmail" && !oobCode.isEmpty()) {
            viewModelScope.launch {
                try {
                    firebaseAuth.applyActionCode(oobCode).await()
                    firebaseAuth.currentUser?.reload()?.await()

                    val isVerified = firebaseAuth.currentUser?.isEmailVerified == true
                    if (isVerified) {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                message = "Email verified successfully! You're all set."
                            )
                        }
                        delay(2000)
                        clearVerificationCredentials()
                        emailVerified()
                    } else {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                message = "Email verification failed. Please try again or request a new verification link."
                            )
                        }
                        clearVerificationCredentials()
                    }
                } catch (e: Exception) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            message = "Something went wrong. The verification link may be invalid or expired. Please request a new one."
                        )
                    }
                    clearVerificationCredentials()
                }
            }
        } else if (mode == "resetPassword" && !oobCode.isEmpty()) {
            viewModelScope.launch {
                try {
                    firebaseAuth.verifyPasswordResetCode(oobCode).await()

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            message = "Email confirmed. You can now reset your password."
                        )
                    }
                    delay(2000)
                    clearVerificationCredentials()
                    toResetPasswordScreen()
                } catch (e: Exception) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            message = "Verification unsuccessful. Please try again later or request a new password reset link."
                        )
                    }
                    clearVerificationCredentials()
                }
            }
        } else {
            _uiState.update {
                it.copy(
                    isLoading = false,
                    message = "Email verification couldn't be completed. Please try again."
                )
            }
            clearVerificationCredentials()
        }
    }

    fun sendVerificationEmail() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, canResend = false) }

            authRepository.sendEmailVerification().collect { authResult ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        message = "Verification email sent. Please check your inbox.",
                        canResend = false
                    )
                }
                startResendCountdown()
            }
        }
    }

    private fun startResendCountdown() {
        resetTimer?.cancel()
        resetTimer = viewModelScope.launch {
            for (i in 60 downTo 1) {
                _uiState.update { it.copy(countdown = i) }
                delay(1000)
            }
            _uiState.update { it.copy(canResend = true) }
        }
    }

    private fun clearVerificationCredentials() {
        EmailVerificationCredentials.mode = ""
        EmailVerificationCredentials.oobCode = ""
    }
}
