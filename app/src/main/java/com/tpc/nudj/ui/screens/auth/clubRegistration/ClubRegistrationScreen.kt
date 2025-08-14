package com.tpc.nudj.ui.screens.auth.clubRegistration

import android.content.res.Configuration
import android.net.Uri
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tpc.nudj.R
import com.tpc.nudj.model.enums.ClubCategory
import com.tpc.nudj.ui.components.TopBar
import com.tpc.nudj.ui.screens.auth.clubRegistration.clubRegistrationScreen1.ClubsRegisterScreen1
import com.tpc.nudj.ui.screens.auth.clubRegistration.clubRegistrationScreen2.ClubsRegisterScreen2
import com.tpc.nudj.ui.theme.ClashDisplay
import com.tpc.nudj.ui.theme.LocalAppColors
import com.tpc.nudj.ui.theme.NudjTheme
import com.tpc.nudj.ui.theme.Orange
import kotlinx.coroutines.launch


@Composable
fun ClubRegistrationScreen(
    viewModel: ClubRegistrationViewModel = hiltViewModel(),
    toDashboardScreen: () -> Unit,
    onBackClicked: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    ClubRegistrationLayout(
        uiState = uiState,
        onNameInput = viewModel::clubNameInput,
        onImagePicked = viewModel::clubLogoImageInput,
        onAdditionalDetailsInput = viewModel::clubAdditionalDetailsInput,
        onCategoryInput = viewModel::clubCategoryInput,
        onDescriptionInput = viewModel::clubDescriptionInput,
        onAddAchievement = viewModel::addAchievement,
        onRemoveAchievement = viewModel::removeAchievement,
        onSavedClicked = { viewModel.onSaveClicked { toDashboardScreen() } },
        clearToastMessage = viewModel::clearToastMessage,
        onBackClicked = {
            onBackClicked()
        }
    )
}


@Composable
fun ClubRegistrationLayout(
    uiState: ClubRegistrationUIState,
    onNameInput: (String) -> Unit,
    onBackClicked: () -> Unit,
    onImagePicked: (Uri?) -> Unit,
    onAdditionalDetailsInput: (String) -> Unit,
    onCategoryInput: (ClubCategory) -> Unit,
    onDescriptionInput: (String) -> Unit,
    onAddAchievement: (String) -> Unit,
    onRemoveAchievement: (Int) -> Unit,
    onSavedClicked: () -> Unit,
    clearToastMessage: () -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { 2 })
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.toastMessage) {
        if (uiState.toastMessage != null) {
            scope.launch {
                snackBarHostState.showSnackbar(
                    message = uiState.toastMessage
                )
            }
            clearToastMessage()
        }
    }
    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        containerColor = LocalAppColors.current.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(LocalAppColors.current.background)
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopBar(
                onBackClicked = {
                    onBackClicked()
                },
                modifier = Modifier.padding(top = 20.dp)
            )
            Spacer(modifier = Modifier.padding(15.dp))
            if (pagerState.currentPage == 0) {
                Text(
                    text = "Club Details",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontFamily = ClashDisplay,
                        color = Orange
                    )
                )
            } else {
                Text(
                    text = "Hold up! A few more details...",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontFamily = ClashDisplay,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                HorizontalPager(
                    state = pagerState,
                ) { page ->
                    when (page) {

                        0 -> {
                            ClubsRegisterScreen1(
                                uiState = uiState,
                                onNameInput = onNameInput,
                                onCategoryInput = onCategoryInput,
                                onDescriptionInput = onDescriptionInput,
                                onAddAchievement = onAddAchievement,
                                onRemoveAchievement = onRemoveAchievement
                            )

                        }

                        else -> {
                            ClubsRegisterScreen2(
                                uiState = uiState,
                                onImagePicked = onImagePicked,
                                onAdditionalDetailsInput = onAdditionalDetailsInput
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.padding(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(pagerState.pageCount) { index ->
                    val isSelected = pagerState.currentPage == index
                    val size by animateDpAsState(
                        targetValue = if (isSelected) 28.dp else 6.dp
                    )
                    val color by animateColorAsState(
                        targetValue = if (isSelected) Orange else Color.LightGray
                    )
                    Box(
                        modifier = Modifier
                            .padding(1.2.dp)
                            .width(size)
                            .height(6.dp)
                            .clip(CircleShape)
                            .background(color)
                    )
                }
            }
            Spacer(modifier = Modifier.padding(10.dp))
            if (pagerState.currentPage == 1) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 44.dp),
                    onClick = {
                        onSavedClicked()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Orange
                    )
                ) {
                    Text(
                        text = "Save & Register",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontFamily = ClashDisplay, color = Color.White
                        ),
                        modifier = Modifier.padding(vertical = 6.dp)
                    )
                }
                Spacer(modifier = Modifier.padding(10.dp))
            } else {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 44.dp),
                    onClick = {
                        scope.launch {
                            if (pagerState.currentPage == 0) {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Orange
                    )
                ) {
                    Text(
                        text = "Next",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontFamily = ClashDisplay, color = Color.White
                        ),
                        modifier = Modifier.padding(vertical = 6.dp)
                    )
                }
                Spacer(modifier = Modifier.padding(10.dp))
            }
        }
        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(50.dp),
                    color = LocalAppColors.current.appTitle,
                    strokeWidth = 6.dp
                )
            }
        }
    }
}



@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ClubRegistrationScreenPreview() {
    NudjTheme {
        ClubRegistrationLayout(
            onNameInput = {},
            onCategoryInput = {},
            onDescriptionInput = {},
            onAddAchievement = {},
            onAdditionalDetailsInput = {},
            onImagePicked = {},
            onRemoveAchievement = {},
            onSavedClicked = {},
            clearToastMessage = {},
            onBackClicked = {},
            uiState = ClubRegistrationUIState()
        )
    }
}