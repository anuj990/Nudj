package com.tpc.nudj.ui.screens.auth.emailVerification


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tpc.nudj.model.AuthResult
import com.tpc.nudj.repository.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class EmailVerificationViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(EmailVerificationUiState())
    val uiState: StateFlow<EmailVerificationUiState> = _uiState.asStateFlow()



    fun toastMessageShown(){
        _uiState.update { it.copy(message = null) }
    }

    fun sendVerificationEmail(
        goToLoginScreen: ()-> Unit
    ) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            authRepository.sendEmailVerification().collect { authResult->
                _uiState.update {
                    it.copy(isLoading = false, message = it.message, canResend = false)
                }
                when (authResult) {
                    is AuthResult.Success -> {
                        goToLoginScreen()
                    }

                    else -> Unit
                }
            }
        }
    }
}