package com.tpc.nudj.ui.screens.ClubDashBoard


import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.size

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tpc.nudj.ui.screens.ClubDashBoard.Data.BottomNavItem
import com.tpc.nudj.ui.screens.ClubDashBoard.Data.Screens
import com.tpc.nudj.ui.theme.EditTextBackgroundColorLight
import com.tpc.nudj.ui.theme.LocalAppColors

import kotlinx.coroutines.delay


@Composable
fun ClubDashScreen(
    viewModel: ClubDashBottomNavViewModel = hiltViewModel()

) {
    val screens = listOf(Screens.Home, Screens.Event, Screens.Profile)
    val pagerState = rememberPagerState(
        initialPage = viewModel.selectedPage,
        pageCount = { screens.size }
    )


    LaunchedEffect(viewModel.selectedPage) {
        if (pagerState.currentPage != viewModel.selectedPage) {
            delay(150)
            pagerState.scrollToPage(viewModel.selectedPage)
        }
    }


    LaunchedEffect(pagerState.currentPage) {
        if (pagerState.currentPage != viewModel.selectedPage) {
            delay(120)
            viewModel.onPageSelected(pagerState.currentPage)
        }
    }

    Scaffold(
        containerColor = LocalAppColors.current.background,
        bottomBar = {
            ClubBottomNavBar(
                selectedIndex = viewModel.selectedPage,
                items = screens.map { BottomNavItem(it.label, it.iconRes, it.route) },
                onItemClick = viewModel::onPageSelected
            )
        }
    ) { padding ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) { page ->
            when (screens[page]) {
                is Screens.Home -> HomeScreenLayout()
                is Screens.Event -> EventScreenLayout()
                is Screens.Profile -> ProfileScreenLayout()
            }
        }
    }
}


@Composable
fun ClubBottomNavBar(
    selectedIndex: Int,
    items: List<BottomNavItem>,
    onItemClick: (Int) -> Unit
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .height(85.dp)
    ) {
        val positionWidth = this.maxWidth / items.size
        val slidingEffect by animateDpAsState(
            targetValue = (selectedIndex * positionWidth.value).dp,
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
                .align(Alignment.TopStart)
        )

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            items.forEachIndexed { index, item ->
                val animateIconSize by animateDpAsState(
                    targetValue = if (index == selectedIndex) 32.dp else 30.dp,
                    animationSpec = tween(durationMillis = 150)
                )
                val slidingEffectY by animateDpAsState(
                    targetValue = if (index == selectedIndex) -4.dp else 0.dp,
                    animationSpec = tween(durationMillis = 150)
                )

                NavigationBarItem(
                    selected = index == selectedIndex,
                    onClick = { onItemClick(index) },
                    icon = {
                        Icon(
                            painter = painterResource(id = item.iconRes),
                            contentDescription = item.label,
                            tint = LocalAppColors.current.landingPageAppTitle,
                            modifier = Modifier
                                .size(animateIconSize)
                                .offset(y = slidingEffectY)
                        )
                    },
                    alwaysShowLabel = true,
                    label = {
                        Text(
                            text = item.label,
                            color = LocalAppColors.current.landingPageAppTitle,
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
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



@Composable
fun HomeScreenLayout() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("HOME Screen")
    }
}

@Composable
fun EventScreenLayout() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("EVENT Screen")
    }
}

@Composable
fun ProfileScreenLayout() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("PROFILE Screen")
    }
}




























