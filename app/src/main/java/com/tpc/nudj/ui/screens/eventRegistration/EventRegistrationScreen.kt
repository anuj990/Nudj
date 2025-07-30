package com.tpc.nudj.ui.screens.eventRegistration

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tpc.nudj.ui.screens.eventRegistration.eventRegistrationScreen1.EventRegisterScreen1
import com.tpc.nudj.ui.screens.eventRegistration.eventRegistrationScreen2.EventRegisterScreen2
import com.tpc.nudj.ui.screens.eventRegistration.eventRegistrationScreen3.EventRegisterScreen3
import com.tpc.nudj.ui.screens.eventRegistration.eventRegistrationScreen4.EventRegisterScreen4
import com.tpc.nudj.ui.screens.myClubs.TopBar
import com.tpc.nudj.ui.theme.ClashDisplay
import com.tpc.nudj.ui.theme.LocalAppColors
import com.tpc.nudj.ui.theme.NudjTheme
import com.tpc.nudj.ui.theme.Orange
import kotlinx.coroutines.launch

@Composable
fun EventRegistrationScreen(
    viewModel: EventRegistrationViewModel = hiltViewModel(),
    onBackClicked: () -> Unit,
) {
    val uiState by viewModel.eventRegistrationUiState.collectAsState()

    EventRegistrationLayout(
        uiState = uiState,
        onEventNameInput = viewModel::eventNameInput,
        onOrganizerNameInput = viewModel::organizerNameInput,
        onOrganizerContactNumberInput = viewModel::organizerContactNumberInput,
        onEventDescriptionInput = viewModel::eventDescriptionInput,
        onEventVenueInput = viewModel::eventVenueInput,
        onEventDateInput = viewModel::addEventDate,
        onRemoveEventDate = viewModel::removeEventDate,
        onImagePicked = viewModel::eventBannerInput,
        changeBatchSelection = viewModel::changeBatchSelection,
        addQuestion = viewModel::addFaqs,
        removeQuestion = viewModel::removeFaqs,
        clearToastMessage = viewModel::clearToastMessage,
        onSaveClicked = {
            viewModel.onSaveClicked {
                onBackClicked()
            }
        },
        onBackClicked = onBackClicked
    )

}

@Composable
fun EventRegistrationLayout(
    uiState: EventRegistrationUiState,
    onEventNameInput: (String) -> Unit,
    onOrganizerNameInput: (String) -> Unit,
    onOrganizerContactNumberInput: (String) -> Unit,
    onImagePicked: (Uri?) -> Unit,
    onEventDescriptionInput: (String) -> Unit,
    onEventDateInput: (EventRegistrationDate) -> Unit,
    onRemoveEventDate: (Int) -> Unit,
    onEventVenueInput: (String) -> Unit,
    changeBatchSelection: (String, Set<String>) -> Unit,
    addQuestion: (EventRegistrationFAQ) -> Unit,
    removeQuestion: (Int) -> Unit,
    onSaveClicked: () -> Unit,
    clearToastMessage: () -> Unit,
    onBackClicked: () -> Unit,
) {
    val pagerState = rememberPagerState(pageCount = { 4 })
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
                    text = "Event Details",
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
                            EventRegisterScreen1(
                                uiState = uiState,
                                onEventNameInput = onEventNameInput,
                                onOrganizerNameInput = onOrganizerNameInput,
                                onOrganizerContactNumberInput = onOrganizerContactNumberInput,
                                onEventDescriptionInput = onEventDescriptionInput
                            )

                        }

                        1 -> {
                            EventRegisterScreen2(
                                uiState = uiState,
                                onEventDateInput = onEventDateInput,
                                onRemoveEventDate = onRemoveEventDate,
                                onEventVenueInput = onEventVenueInput
                            )
                        }

                        2 -> {
                            EventRegisterScreen3(
                                uiState = uiState,
                                onImagePicked = onImagePicked,
                                changeBatchSelection = changeBatchSelection
                            )
                        }

                        else -> {
                            EventRegisterScreen4(
                                uiState = uiState,
                                addQuestion = addQuestion,
                                removeQuestion = removeQuestion
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
            if (pagerState.currentPage == 3) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 44.dp),
                    onClick = {
                        onSaveClicked()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Orange
                    )
                ) {
                    Text(
                        text = "Save & Register Event",
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
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
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
@Composable
fun EventRegistrationScreenPreview() {
    NudjTheme {
        EventRegistrationLayout(
            uiState = EventRegistrationUiState(),
            onEventNameInput = {},
            onOrganizerNameInput = {},
            onOrganizerContactNumberInput = {},
            onEventDescriptionInput = {},
            onEventVenueInput = {},
            onEventDateInput = {},
            onRemoveEventDate = {},
            changeBatchSelection = { _, _ -> },
            onImagePicked = {},
            addQuestion = {},
            removeQuestion = {},
            clearToastMessage = {},
            onSaveClicked = {},
            onBackClicked = {}
        )
    }
}