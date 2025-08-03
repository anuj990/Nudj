package com.tpc.nudj.ui.screens.ClubDashBoard

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class ClubDashBottomNavViewModel @Inject constructor(): ViewModel(){

    var selectedPage by mutableStateOf(0)

    open fun onPageSelected(index: Int){
        selectedPage = index
    }

}

