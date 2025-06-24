package com.tpc.nudj.ui.screens.dashboard

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class DashBoardViewModel @Inject constructor(): ViewModel() {
    private val _dashBoardUiState = MutableStateFlow(DashBoardUiState())
    val dashBoardUiState: StateFlow<DashBoardUiState> = _dashBoardUiState.asStateFlow()

    fun onScreenClicked(index :Int){
        _dashBoardUiState.update {
            it.copy(
                selectedPage = index
            )
        }
    }
}