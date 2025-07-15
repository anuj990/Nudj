package com.tpc.nudj.ui.screens.auth.forgotPassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tpc.nudj.model.AuthResult
import com.tpc.nudj.repository.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgetPasswordScreenModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {
    private val _forgetPasswordUiState = MutableStateFlow(ForgetPasswordUiState())
    val forgetPasswordUiState: StateFlow<ForgetPasswordUiState> = _forgetPasswordUiState.asStateFlow()
    fun emailInput(input:String){
        _forgetPasswordUiState.update {
            it.copy(
                email = input,
            )
        }
    }
    fun clearError(){
        _forgetPasswordUiState.update {
            it.copy(
                toastMessage = null
            )
        }
    }

    fun resendEmail() {
        onSendEmailClicked {}
    }

    fun onSendEmailClicked(onResetSent: () -> Unit) {
        val email = _forgetPasswordUiState.value.email

        if (email.isBlank()) {
            _forgetPasswordUiState.update {
                it.copy(toastMessage = "Email cannot be empty")
            }
            return
        }

        _forgetPasswordUiState.update { it.copy(isLoading = true, toastMessage = null) }

        viewModelScope.launch {
            authRepository.sendPasswordResetEmail(email).collect { result ->
                when (result) {
                    is AuthResult.Loading -> {
                        _forgetPasswordUiState.update { it.copy(isLoading = true) }
                    }

                    is AuthResult.Success -> {
                        _forgetPasswordUiState.update {
                            it.copy(isLoading = false, toastMessage = "Reset link sent!")
                        }
                        onResetSent()
                    }

                    is AuthResult.Error -> {
                        _forgetPasswordUiState.update {
                            it.copy(isLoading = false, toastMessage = result.message)
                        }
                    }

                    else -> {}
                }
            }
        }
    }
}