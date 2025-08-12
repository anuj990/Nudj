package com.tpc.nudj.ui.screens.clubProfileScreen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.google.firebase.Timestamp
import com.tpc.nudj.R
import com.tpc.nudj.model.enums.ClubCategory
import com.tpc.nudj.ui.components.LoadingScreenOverlay
import com.tpc.nudj.ui.components.TopBar
import com.tpc.nudj.ui.screens.clubProfileScreen.editClubProfile.EditCLubProfileScreen
import com.tpc.nudj.ui.theme.ClashDisplay
import com.tpc.nudj.ui.theme.LocalAppColors
import com.tpc.nudj.ui.theme.NudjTheme
import com.tpc.nudj.ui.theme.Orange
import com.tpc.nudj.ui.theme.Purple
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ClubProfileScreen(
    viewModel: ClubProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.profileUiState.collectAsState()
    var editProfile by remember { mutableStateOf(false) }
    LaunchedEffect(Unit, editProfile) {
        viewModel.fetchCurrentClub()
    }
    if (uiState.isLoading) {
        LoadingScreenOverlay()
    }
    AnimatedContent(
        targetState = editProfile,
        transitionSpec = {
            slideInHorizontally(
                initialOffsetX = { fullWidth -> fullWidth },
                animationSpec = tween(300)
            ) togetherWith
                    fadeOut(animationSpec = tween(90))
        }
    ) { editScreen ->
        if (editScreen) {
            EditCLubProfileScreen(
                onBackClicked = {
                    editProfile = false
                }
            )
        } else {
            ClubProfileLayout(
                uiState = uiState,
                onPastEventClicked = {},
                onEditClicked = {
                    editProfile = true
                },
                clearToastMessage = viewModel::clearToastMessage,
                clearClubLogo = viewModel::emptyClubLogo
            )
        }
    }

}

@Composable
fun ClubProfileLayout(
    uiState: ClubProfileUIState,
    onPastEventClicked: (String) -> Unit,
    onEditClicked: () -> Unit,
    clearToastMessage: () -> Unit,
    clearClubLogo: () -> Unit
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
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
        topBar = {
            TopBar(
                onBackClicked = {},
                modifier = Modifier.padding(top = 20.dp),
                haveToGoBack = false
            )
        },
        containerColor = LocalAppColors.current.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(LocalAppColors.current.background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.padding(10.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(0.9f),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = LocalAppColors.current.appTitle
                        ),
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                                .align(Alignment.CenterHorizontally),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Spacer(modifier = Modifier.padding(4.dp))
                            Text(
                                text = uiState.clubName,
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    fontFamily = ClashDisplay,
                                    color = Color.White
                                ),
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = uiState.clubCategory.categoryName,
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    fontFamily = ClashDisplay,
                                    color = if (isSystemInDarkTheme()) Purple else Orange
                                )
                            )
                            Spacer(modifier = Modifier.height(3.dp))
                            if (uiState.clubLogo != null) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {

                                    AsyncImage(
                                        model = uiState.clubLogo,
                                        contentDescription = "Club Logo",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .clip(RoundedCornerShape(20.dp)),
                                        onError = {
                                            clearClubLogo()
                                        }
                                    )

                                }
                            } else {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(140.dp)
                                        .background(
                                            Color.LightGray,
                                            shape = RoundedCornerShape(20.dp)
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {

                                    Image(
                                        painter = if (isSystemInDarkTheme()) painterResource(R.drawable.purple_n) else painterResource(
                                            R.drawable.orange_n
                                        ),
                                        contentDescription = null
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(
                                modifier = Modifier
                                    .clickable {
                                        onEditClicked()
                                    }
                                    .align(Alignment.End)
                                    .padding(end = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = null,
                                    tint = Color.White
                                )
                                Spacer(modifier = Modifier.width(2.dp))
                                Text(
                                    text = "Edit",
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        color = Color.White,
                                        fontSize = 20.sp,
                                        fontFamily = ClashDisplay
                                    )
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                        }
                    }
                    Spacer(modifier = Modifier.padding(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(0.9f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Description",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontFamily = ClashDisplay,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        HorizontalDivider(
                            thickness = 2.dp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    Spacer(modifier = Modifier.padding(4.dp))
                    Box(
                        modifier = Modifier.fillMaxWidth(0.9f),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = uiState.description,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontSize = 24.sp,
                                fontFamily = ClashDisplay,
                                color = Orange
                            ),
                        )
                    }
                    Spacer(modifier = Modifier.padding(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(0.9f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Achievements",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontFamily = ClashDisplay,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        HorizontalDivider(
                            thickness = 2.dp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    Spacer(modifier = Modifier.padding(4.dp))
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .border(
                                width = 1.8F.dp,
                                LocalAppColors.current.editTextBorder,
                                shape = RoundedCornerShape(10.dp)
                            ),
                        colors = CardDefaults.cardColors(
                            containerColor = LocalAppColors.current.editTextBackground
                        ),
                    ) {
                        Spacer(modifier = Modifier.height(16.dp))
                        uiState.achievementsList.forEach { achievement ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp)
                            ) {
                                Text(
                                    text = "•",
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        fontFamily = ClashDisplay,
                                        color = Orange
                                    ),
                                    modifier = Modifier.padding(end = 6.dp)
                                )
                                Spacer(modifier = Modifier.width(3.dp))
                                Text(
                                    text = achievement,
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        fontFamily = ClashDisplay,
                                        color = Orange
                                    ),
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(end = 8.dp),
                                    textAlign = TextAlign.Start
                                )
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                    Spacer(modifier = Modifier.padding(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(0.9f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Past Events",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontFamily = ClashDisplay,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        HorizontalDivider(
                            thickness = 2.dp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    Spacer(modifier = Modifier.padding(4.dp))
                    uiState.clubEvents.forEach { eventDetails ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                                .clickable {
                                    onPastEventClicked(eventDetails.eventId)
                                },
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = LocalAppColors.current.appTitle
                            ),
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(0.9f)
                                    .align(Alignment.CenterHorizontally),
                                horizontalAlignment = Alignment.Start,
                            ) {
                                Spacer(modifier = Modifier.padding(6.dp))
                                Text(
                                    text = timestampFormatConverter(eventDetails.eventDate),
                                    style = MaterialTheme.typography.headlineSmall.copy(
                                        fontFamily = ClashDisplay,
                                        color = LocalAppColors.current.background
                                    ),
                                )
                                Spacer(modifier = Modifier.padding(12.dp))
                                Text(
                                    text = eventDetails.eventName,
                                    style = MaterialTheme.typography.headlineMedium.copy(
                                        fontFamily = ClashDisplay,
                                        color = LocalAppColors.current.background
                                    ),
                                )
                                Spacer(modifier = Modifier.padding(10.dp))
                                Text(
                                    text = "${eventDetails.rsvp} RSVPs",
                                    style = MaterialTheme.typography.headlineSmall.copy(
                                        fontFamily = ClashDisplay,
                                        color = LocalAppColors.current.background
                                    ),
                                )
                                Spacer(modifier = Modifier.padding(10.dp))
                            }
                        }
                        Spacer(modifier = Modifier.padding(8.dp))
                    }
                    Spacer(modifier = Modifier.padding(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(0.9f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Additional Details",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontFamily = ClashDisplay,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        HorizontalDivider(
                            thickness = 2.dp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    Spacer(modifier = Modifier.padding(4.dp))
                    Box(
                        modifier = Modifier.fillMaxWidth(0.9f),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = uiState.additionalDetails,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontSize = 24.sp,
                                fontFamily = ClashDisplay,
                                color = Orange
                            ),
                        )
                    }
                }
            }

        }
    }
}

private fun timestampFormatConverter(eventDate: Timestamp): String {
    val dateFormatter = SimpleDateFormat("MMMM dd yyyy", Locale.getDefault())
    val formattedDate = dateFormatter.format(eventDate.toDate())
    return formattedDate
}


@Preview
@Composable
fun ClubProfileScreenPreview() {
    NudjTheme {
        ClubProfileLayout(
            uiState = ClubProfileUIState(
                clubName = "The Programming Club",
                clubCategory = ClubCategory.TECHNICAL,
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In sapien enim, blandit eget tellus in ultrices iaculis felis. In turpis mauris, ",
                achievementsList = listOf(
                    "Achievement 1",
                    "Achievement 2",
                    "Achievement 3",
                    "Achievement 4"
                ),
                clubEvents = listOf(
                    PastEventsDetails(
                        rsvp = 10,
                        eventDate = Timestamp.now()
                    )
                )
            ),
            onPastEventClicked = {},
            onEditClicked = {},
            clearToastMessage = {},
            clearClubLogo = {}
        )
    }
}