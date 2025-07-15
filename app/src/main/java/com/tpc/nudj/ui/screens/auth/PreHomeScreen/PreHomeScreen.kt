package com.tpc.nudj.ui.screens.auth.PreHomeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tpc.nudj.ui.components.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.tpc.nudj.model.ClubUser
import com.tpc.nudj.ui.theme.ClashDisplay
import com.tpc.nudj.ui.theme.LocalAppColors
import com.tpc.nudj.ui.theme.NudjTheme
import com.tpc.nudj.ui.theme.Purple

@Composable
fun PreHomeScreen(onCompleted: () -> Unit) {
    val viewModel: PreHomeScreenViewModel = hiltViewModel()
    val isLoading = viewModel.isLoading.collectAsState()
    val buttonText = if (viewModel.selectedCount.value > 0)
        "Follow ${viewModel.selectedCount.value} club${if (viewModel.selectedCount.value > 1) "s" else ""}"
    else
        "Let's Go!"

    PreHomeScreenLayout(
        culturalClubs = viewModel.culturalClubsState,
        technicalClubs = viewModel.technicalClubsState,
        sportsClubs = viewModel.sportsClubsState,
        onClubSelected = { list, index -> viewModel.ClubSelection(list, index) },
        buttonText = buttonText,
        onClickFollow = {
            viewModel.onClickFollow(
                onCompleted
            )
        }
    )

    if (isLoading.value) {
        LoadingScreenOverlay()
    }
}

@Composable
fun PreHomeScreenLayout(
    culturalClubs: ClubCategoryState,
    technicalClubs: ClubCategoryState,
    sportsClubs: ClubCategoryState,
    onClubSelected: (SnapshotStateList<ClubCardState>, Int) -> Unit,
    buttonText: String,
    onClickFollow: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = LocalAppColors.current.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(10.dp))

                Box(
                    modifier = Modifier
                        .padding(10.dp)
                        .width(391.dp)
                        .height(695.dp)
                        .background(color = Color.White, shape = RoundedCornerShape(16.dp))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Color.White, shape = RoundedCornerShape(16.dp))
                            .padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Choose your clubs!",
                            fontFamily = ClashDisplay,
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.W600,
                            fontSize = 25.sp,
                            color = LocalAppColors.current.landingPageAppTitle
                        )

                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            item {
                                ClubCategorySection(culturalClubs, onClubSelected)
                            }
                            item {
                                ClubCategorySection(technicalClubs, onClubSelected)
                            }
                            item {
                                ClubCategorySection(sportsClubs, onClubSelected)
                            }
                        }
                    }
                }
            }

            PrimaryButton(
                text = buttonText,
                onClick = {
                    onClickFollow()
                },
                modifier = Modifier
                    .padding(bottom = 19.dp, top = 6.dp)
                    .width(300.dp)
                    .height(62.dp),
                enabled = true,
                isDarkModeEnabled = false
            )
        }
    }
}

@Composable
fun ClubCategorySection(
    data: ClubCategoryState,
    onClubSelected: (SnapshotStateList<ClubCardState>, Int) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = data.category,
            fontFamily = ClashDisplay,
            fontWeight = FontWeight.W600,
            fontSize = 20.sp,
            lineHeight = 20.sp,
            color = Purple,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        val rows = (data.clubList.size + 1) / 2
        val gridHeight = (rows * 211).dp + ((rows - 1) * 12).dp

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            userScrollEnabled = false,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(gridHeight)
        ) {
            itemsIndexed(data.clubList) { index, club ->
                ClubNameCard(
                    club = club,
                    baseColor = data.baseColor,
                    textColor = data.textColor,
                    onClick = { onClubSelected(data.clubList, index) }
                )
            }
        }
    }
}




@Composable
@Preview(showBackground = true)
fun PreHomeScreenLayoutPreview() {
    val dummyClubs = listOf(ClubUser(clubName = "Club 1"), ClubUser(clubName = "Club 2"), ClubUser(clubName = "Club 3"), ClubUser(clubName = "Club 4"))


    val dummyList = mutableStateListOf<ClubCardState>().apply {
        addAll(dummyClubs.map { ClubCardState(it) })
    }

    val cultural = ClubCategoryState(
        category = "Cultural Clubs",
        clubList = dummyList,
        baseColor = Purple,
        textColor = Color.White
    )
    val technical = ClubCategoryState(
        category = "Technical Clubs",
        clubList = dummyList,
        baseColor = Color(0xFFFFF1E6),
        textColor = Purple
    )
    val sports = ClubCategoryState(
        category = "Sports Clubs",
        clubList = dummyList,
        baseColor = Color.Yellow,
        textColor = Color.Black
    )


    NudjTheme {
        PreHomeScreenLayout(
            culturalClubs = cultural,
            technicalClubs = technical,
            sportsClubs = sports,
            onClubSelected = { _, _ -> },
            buttonText = "Follow 0 clubs",
            onClickFollow = {}
        )
    }
}

@Composable
@Preview(showBackground = true)
fun LoadingScreenOverlay() {
    Box(
        modifier = Modifier.fillMaxSize()
            .background(color = Color.Black.copy(alpha = 0.5f)),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .size(150.dp),
            shape = RoundedCornerShape(percent = 15)
        ) {
            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}
