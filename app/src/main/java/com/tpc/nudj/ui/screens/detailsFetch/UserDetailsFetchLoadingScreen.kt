package com.tpc.nudj.ui.screens.detailsFetch

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tpc.nudj.R
import com.tpc.nudj.ui.components.LoadingScreenLayout
import com.tpc.nudj.ui.theme.Orange
import com.tpc.nudj.ui.theme.Purple

@Composable
fun UserDetailsFetchScreen(
    text: String,
    onNormalUser: () -> Unit,
    onClubUser: () -> Unit,
    onUserNotFound: () -> Unit,
    viewModel: UserDetailsViewModel = hiltViewModel()
) {
    val userType = viewModel.userTypeState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.checkUserType()
    }

    when (userType.value) {
        is UserDetailsViewModel.UserType.NormalUser -> onNormalUser()
        is UserDetailsViewModel.UserType.ClubUser -> onClubUser()
        is UserDetailsViewModel.UserType.NotFound -> onUserNotFound()
        is UserDetailsViewModel.UserType.Loading -> {
            LoadingScreenLayout(text)
        }
    }
}
