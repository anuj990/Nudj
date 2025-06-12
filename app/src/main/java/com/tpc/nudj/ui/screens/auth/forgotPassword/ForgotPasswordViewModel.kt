package com.tpc.nudj.ui.screens.auth.forgotPassword

import androidx.lifecycle.ViewModel
import com.tpc.nudj.repository.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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
    fun onCheckInboxClicked(){

    }

    fun resendEmail(){

    }

    fun onSendEmailClicked(){
        val email = _forgetPasswordUiState.value.email

        if (email.isBlank()) {
            _forgetPasswordUiState.update {
                it.copy(toastMessage = "Email cannot be empty")
            }
            return
        }
       _forgetPasswordUiState.update { it.copy(isLoading = true, toastMessage = null) }
    }
}