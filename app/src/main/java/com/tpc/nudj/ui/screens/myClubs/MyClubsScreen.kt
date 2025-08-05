package com.tpc.nudj.ui.screens.myClubs

import android.content.res.Configuration
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import com.tpc.nudj.R
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tpc.nudj.model.ClubUser
import com.tpc.nudj.model.enums.ClubCategory
import com.tpc.nudj.ui.components.LoadingScreenOverlay
import com.tpc.nudj.ui.screens.auth.preHomeScreen.PreHomeScreenLayout
import com.tpc.nudj.ui.theme.ClashDisplay
import com.tpc.nudj.ui.theme.EditTextBackgroundColorDark
import com.tpc.nudj.ui.theme.EditTextBackgroundColorLight
import com.tpc.nudj.ui.theme.LocalAppColors
import com.tpc.nudj.ui.theme.NudjTheme
import com.tpc.nudj.ui.theme.Orange
import kotlinx.coroutines.launch


@Composable
fun MyClubs(
    onBackClicked: () -> Unit,
    viewModel: MyClubsViewModel = hiltViewModel()
) {
    val sheetExpanded = remember { mutableStateOf(false) }
    val followedClubs = viewModel.selectedClubs.collectAsState().value
    val allClubs = viewModel.allClubsList.collectAsState().value
    val uiState by viewModel.myClubsUiState.collectAsState()

    if (sheetExpanded.value) {
        AllClubsBottomSheetLayout(
            onDismiss = { sheetExpanded.value = false },
            allClubsList = allClubs,
            onFollowClub = {
                viewModel.addSelectedClub(it)
            }
        )
    }

    MyClubsLayout(
        uiState = uiState,
        followedClubs = followedClubs,
        onAddClicked = { sheetExpanded.value = true },
        onBack = { onBackClicked() },
        onCancelClub = { viewModel.removeSelectedClub(it) },
        clearToastMessage = { viewModel.clearToastMessage() },
        onSaveClicked = {
            viewModel.onFollowClubs(onBack = { onBackClicked() })
        }
    )


    if (uiState.isLoading) {
        LoadingScreenOverlay()
    }
}


@Composable
fun MyClubsLayout(
    uiState: MyClubsUiState,
    followedClubs: List<ClubUser>,
    onAddClicked: () -> Unit,
    onBack: () -> Unit,
    onCancelClub: (String) -> Unit,
    onSaveClicked: () -> Unit,
    clearToastMessage: () -> Unit,
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
        containerColor = LocalAppColors.current.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopBar(
                onBackClicked = { onBack() },
                modifier = Modifier.padding(top = 20.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.your_clubs),
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontFamily = ClashDisplay,
                    color = LocalAppColors.current.appTitle
                ),
            )
            Spacer(modifier = Modifier.padding(20.dp))
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(followedClubs) { club ->
                    val visible = remember { mutableStateOf(false) }

                    LaunchedEffect(Unit) {
                        visible.value = true
                    }

                    AnimatedVisibility(
                        visible = visible.value,
                        enter = slideInHorizontally { it } + fadeIn(),
                    ) {
                        FollowedClubCards(
                            clubName = club.clubName,
                            clubId = club.clubId,
                            onCancel = { onCancelClub(it) }
                        )
                    }

                    Spacer(modifier = Modifier.padding(8.dp))
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.9f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { onSaveClicked() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LocalAppColors.current.appTitle,
                        contentColor = if (isSystemInDarkTheme()) Color.White else Orange
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row {
                            AnimatedVisibility(
                                visible = !uiState.isSaved,
                                enter = slideInHorizontally { it } + fadeIn(),
                                exit = slideOutHorizontally { -it } + fadeOut()
                            ) {
                                Text(
                                    text = "Save",
                                    style = MaterialTheme.typography.headlineMedium.copy(
                                        fontFamily = ClashDisplay,
                                        color = Color.White
                                    ),
                                    modifier = Modifier
                                )
                            }
                        }
                        Row {
                            AnimatedVisibility(
                                visible = uiState.isSaved,
                                enter = slideInHorizontally { it } + fadeIn(),
                                exit = slideOutHorizontally { -it } + fadeOut()
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Done,
                                    contentDescription = null,
                                    tint = Color.Green,
                                    modifier = Modifier
                                        .size(40.dp)
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.width(30.dp))
                Button(
                    onClick = {
                        onAddClicked()
                    },
                    shape = CircleShape,
                    modifier = Modifier
                        .size(64.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (!isSystemInDarkTheme()) Orange else EditTextBackgroundColorDark
                    ),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add club to list",
                        modifier = Modifier.size(55.dp),
                        tint = Color.White
                    )
                }
            }
            Spacer(modifier = Modifier.padding(24.dp))
        }

    }
}


@Composable
fun FollowedClubCards(
    clubId: String,
    clubName: String,
    onCancel: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .border(1.dp, color = Color.Black, shape = RoundedCornerShape(14.dp)),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = EditTextBackgroundColorLight
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 25.dp, horizontal = 20.dp)
        ) {
            Text(
                text = clubName,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontFamily = ClashDisplay,
                    color = Color.Black
                ),
                modifier = Modifier.align(Alignment.CenterStart)
            )
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = "cancel club selection",
                tint = LocalAppColors.current.appTitle,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .clickable { onCancel(clubId) }
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllClubsBottomSheetLayout(
    allClubsList: List<ClubUser>,
    onFollowClub: (ClubUser) -> Unit,
    onDismiss: () -> Unit
) {

    val clubGroupedByCategory = allClubsList.groupBy { it.clubCategory }
    ModalBottomSheet(
        onDismissRequest = {
            onDismiss()
        },
        containerColor = if (isSystemInDarkTheme()) EditTextBackgroundColorDark else Color.White
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "All Clubs",
                style = MaterialTheme.typography.displaySmall.copy(
                    fontFamily = ClashDisplay,
                    color = LocalAppColors.current.appTitle
                )
            )
        }
        Spacer(modifier = Modifier.padding(20.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            clubGroupedByCategory.forEach { (category, clubs) ->
                item(category) {

                    Row(
                        modifier = Modifier.fillMaxWidth(0.9f),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        HorizontalDivider(
                            thickness = 2.dp,
                            color = Color.Gray,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = category.categoryName,
                            style = MaterialTheme.typography.headlineLarge.copy(
                                fontFamily = ClashDisplay,
                                color = LocalAppColors.current.appTitle
                            ),
                            modifier = Modifier.padding(
                                start = 12.dp, end = 12.dp
                            )
                        )
                        HorizontalDivider(
                            thickness = 2.dp,
                            color = Color.Gray,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                }
                items(clubs) { club ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .border(1.dp, color = Color.Black, shape = RoundedCornerShape(14.dp)),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = EditTextBackgroundColorLight
                        ),
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(18.dp)
                                .clickable {
                                    onFollowClub(club)
                                    onDismiss()
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                modifier = Modifier,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = club.clubName,
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        fontFamily = ClashDisplay,
                                        color = Color.Black
                                    ),
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = club.description,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontFamily = ClashDisplay,
                                        color = Color.DarkGray
                                    ),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.padding(8.dp))
                }
            }
        }
    }
}

@Composable
fun TopBar(
    onBackClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.displayMedium.copy(
                fontFamily = ClashDisplay,
                color = LocalAppColors.current.appTitle
            ),
            modifier = Modifier.align(Alignment.Center)
        )
        IconButton(
            onClick = { onBackClicked() }
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = stringResource(R.string.back_navigation),
                modifier = Modifier.size(25.dp),
                tint = LocalAppColors.current.appTitle
            )
        }
    }
}


@Preview
@Composable
fun ClubCardPreview() {
    NudjTheme {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            FollowedClubCards(
                onCancel = {},
                clubId = "",
                clubName = "The Programming Club"
            )
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MyClubScreenPreview() {
    val previewClubs = listOf(
        ClubUser(
            clubId = "samvaad@iiitdmj.ac.in",
            clubName = "Samvaad",
            description = "Poetry and literature society focusing on creative writing and literary discussions.",
            achievementsList = emptyList(),
            clubEmail = "samvaad@college.edu",
            clubLogo = "https://www.iiitdmj.ac.in/webix.iiitdmj.ac.in/tpclogo.png",
            clubCategory = ClubCategory.CULTURAL,
            additionalDetails = "Weekly meetings in Central Library, Wednesday 4 PM"
        ),
        ClubUser(
            clubId = "tpc@iiitdmj.ac.in",
            clubName = "The Programming Club",
            description = "TPC is the coding society of the college that focuses on developing programming skills and conducting coding competitions.",
            achievementsList = emptyList(),
            clubEmail = "tpc@college.edu",
            clubLogo = "https://www.iiitdmj.ac.in/webix.iiitdmj.ac.in/tpclogo.png",
            clubCategory = ClubCategory.TECHNICAL,
            additionalDetails = "Weekly coding sessions every Friday at 5 PM in Lab 1"
        ),
        ClubUser(
            clubId = "football@iiitdmj.ac.in",
            clubName = "Football Club",
            description = "Football/soccer training and competitive team.",
            achievementsList = emptyList(),
            clubEmail = "football@college.edu",
            clubLogo = "https://www.iiitdmj.ac.in/webix.iiitdmj.ac.in/tpclogo.png",
            clubCategory = ClubCategory.SPORTS,
            additionalDetails = "Practice sessions Monday, Wednesday, Friday at 5 PM"
        ),
        ClubUser(
            clubId = "avartan@iiitdmj.ac.in",
            clubName = "Avartan",
            description = "Dance society exploring various dance forms and performances.",
            achievementsList = emptyList(),
            clubEmail = "avartan@college.edu",
            clubLogo = "https://www.iiitdmj.ac.in/webix.iiitdmj.ac.in/tpclogo.png",
            clubCategory = ClubCategory.CULTURAL,
            additionalDetails = "Practice sessions in Dance Room, Tuesday and Thursday 6 PM"
        )
    )

    NudjTheme {
        MyClubsLayout(
            followedClubs = previewClubs,
            onAddClicked = {},
            onBack = {},
            onCancelClub = {},
            onSaveClicked = {},
            uiState = MyClubsUiState(),
            clearToastMessage = {}
        )
    }
}
