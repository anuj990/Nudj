package com.tpc.nudj.FirestoreDetailsFetch

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
            Box(modifier = Modifier.fillMaxSize()) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 250.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.userdetailsfetchimage),
                        contentDescription = "",
                        modifier = Modifier
                            .width(249.dp)
                            .height(222.dp)
                    )

                    Text(
                        text = text,
                        fontSize = 18.sp,
                        color = Orange,
                        fontWeight = FontWeight.Medium
                    )
                }

                Text(
                    text = "Nudj",
                    fontSize = 35.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Purple,
                    modifier = Modifier
                        .offset(x = 160.dp, y = 780.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewUserDetailsFetchScreen() {
    UserDetailsFetchScreen(
        text = "Hang in there!",
        onNormalUser = {},
        onClubUser = {},
        onUserNotFound = {}
    )
}
