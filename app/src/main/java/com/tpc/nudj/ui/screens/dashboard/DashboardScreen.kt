package com.tpc.nudj.ui.screens.dashboard

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.getValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.hilt.navigation.compose.hiltViewModel
import com.tpc.nudj.ui.theme.NudjTheme
import kotlinx.coroutines.delay
import com.tpc.nudj.R
import com.tpc.nudj.ui.navigation.Screens
import com.tpc.nudj.ui.screens.dashboard.announcement.AnnouncementScreen
import com.tpc.nudj.ui.screens.dashboard.announcement.recentAnnouncement
import com.tpc.nudj.ui.screens.homeScreen.HomeScreenLayout
import com.tpc.nudj.ui.theme.EditTextBackgroundColorLight
import com.tpc.nudj.ui.theme.LocalAppColors
import com.tpc.nudj.ui.viewmodels.AppViewModel


enum class BottomNavScreen(@StringRes val title: Int, @DrawableRes val icon: Int) {
    Home(title = R.string.home, R.drawable.home_image),
    Clubs(title = R.string.my_clubs, R.drawable.club_image),
    Rsvp(title = R.string.rsvp, R.drawable.rsvp_image),
    Profile(title = R.string.profile, R.drawable.profile_image)
}

@Composable
fun DashboardScreen(
    viewModel: DashBoardViewModel = viewModel(),
    onNavigateToMyClubs:()->Unit = {},
    appViewModel: AppViewModel = hiltViewModel()
) {
    val uiState by viewModel.dashBoardUiState.collectAsState()
    val destination = listOf(
        BottomNavScreen.Home,
        BottomNavScreen.Clubs,
        BottomNavScreen.Rsvp,
        BottomNavScreen.Profile
    )
    val pagerState = rememberPagerState(initialPage = uiState.selectedPage) { destination.size }
    LaunchedEffect(pagerState.currentPage) {
        if (uiState.selectedPage != pagerState.currentPage) {
            delay(120)
            viewModel.onScreenClicked(pagerState.currentPage)
        }
    }
    LaunchedEffect(uiState.selectedPage) {
        if (pagerState.currentPage != uiState.selectedPage) {
            delay(200)
            pagerState.scrollToPage(uiState.selectedPage)
        }
    }
    Scaffold(
        containerColor = LocalAppColors.current.background,
        bottomBar = {
            BottomNavigationBar(
                uiState = uiState,
                screens = destination,
                onScreenChange = { index -> viewModel.onScreenClicked(index) }
            )
        }
    ) { padding ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) { page ->
            val screen = destination[page]
            when (screen) {
                BottomNavScreen.Home -> {
                    HomeScreenLayout()
                }
                BottomNavScreen.Clubs -> {
                    AnnouncementScreen(
                        recentAnnouncements = recentAnnouncement,
                        onBackClicked = {},
                        navigateToMyClub = onNavigateToMyClubs
                    )
                }
                else -> NavScreens(
                    screen = screen,
                    onNavigateToMyClubs = onNavigateToMyClubs,
                    onSignOut = { appViewModel.signOut() }
                )
            }
        }
    }
}

//A temporary placeholder for other screens
@Composable
fun NavScreens(
    screen: BottomNavScreen,
    onNavigateToMyClubs: ()-> Unit,
    onSignOut: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LocalAppColors.current.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(screen.title),
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            if (screen == BottomNavScreen.Profile) {
                Button(
                    onClick = onSignOut,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF5E00)
                    ),
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text("Sign out")
                }
            }
            if(screen == BottomNavScreen.Clubs){
                Button(
                    onClick = onNavigateToMyClubs,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF5E00)
                    ),
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text("Go to My Clubs")
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(
    screens: List<BottomNavScreen> = emptyList<BottomNavScreen>(),
    uiState: DashBoardUiState,
    onScreenChange: (Int) -> Unit,
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .height(85.dp)
    ) {
        val positionWidth = this.maxWidth / screens.size
        val slidingEffect by animateDpAsState(
            targetValue = (uiState.selectedPage * positionWidth.value).dp,
            animationSpec = tween(durationMillis = 200)
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(EditTextBackgroundColorLight)
        )
        Box(
            modifier = Modifier
                .padding(start = 16.dp, top = 6.dp)
                .offset(x = slidingEffect)
                .width((positionWidth.value - 32).dp)
                .height(34.dp)
                .shadow(8.dp, shape = RoundedCornerShape(20.dp))
                .clip(RoundedCornerShape(20.dp))
                .background(LocalAppColors.current.landingPageBackground)
                .padding(end = 20.dp)
                .align(Alignment.TopStart),
        )
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 8.dp)
                .background(Color.Transparent),
        ) {
            screens.forEachIndexed { index, dest ->
                val animateIconSize by animateDpAsState(
                    targetValue = if (uiState.selectedPage == index) 32.dp else 30.dp,
                    animationSpec = tween(durationMillis = 200)
                )
                val slidingEffectY by animateDpAsState(
                    targetValue = if (uiState.selectedPage == index) -4.dp else 0.dp,
                    animationSpec = tween(durationMillis = 200)
                )
                NavigationBarItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = dest.icon),
                            contentDescription = stringResource(dest.title),
                            tint = LocalAppColors.current.landingPageAppTitle,
                            modifier = Modifier
                                .size(animateIconSize)
                                .offset(y = slidingEffectY)
                        )
                    },
                    selected = uiState.selectedPage == index,
                    onClick = {
                        onScreenChange(index)
                    },
                    alwaysShowLabel = true,
                    label = {
                        Text(
                            text = stringResource(dest.title),
                            color = LocalAppColors.current.landingPageAppTitle,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = Color.Transparent
                    )
                )
            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DashboardScreenPreview() {
    NudjTheme {
        DashboardScreen()
    }
}