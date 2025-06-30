package com.tpc.nudj.ui.screens.auth.PreHomeScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tpc.nudj.ui.components.*
import androidx.compose.runtime.getValue
import com.tpc.nudj.ui.theme.ClashDisplay
import com.tpc.nudj.ui.theme.LocalAppColors

val culturalClubs = clubDetails(
    listOf("Avartan", "Saaz", "Abhivyakti", "Jazbaat", "Samvaad", "Shutterbox"),
    "Cultural clubs"
)
val technicalClubs = clubDetails(
    listOf("Programming", "Robotics", "Buisness", "Racing", "AeroFabrication", "Astronomy", "CAD"),
    "Technical clubs"
)
val sportsClubs = clubDetails(
    listOf("Athletics", "Badminton", "Chess", "Carrom", "Cricket", "Football", "GYM", "Lawn Tennis", "Table Tennis", "VolleyBall"),
    "Sport clubs"
)
val otherClubs = clubDetails(listOf("E-Cell", "Jaagrati"), "Others")

@Composable
fun PreHomeScreenLayout(viewModel: PreHomeScreenViewModel = viewModel()) {
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
                NudjLogo()
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
                            .padding(16.dp),
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

                        Spacer(modifier = Modifier.height(12.dp))

                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            item {
                                ClubCategorySection(culturalClubs, viewModel.culturalClubsState) {
                                    viewModel.ClubSelection(viewModel.culturalClubsState, it)
                                }
                            }
                            item {
                                ClubCategorySection(technicalClubs, viewModel.technicalClubsState) {
                                    viewModel.ClubSelection(viewModel.technicalClubsState, it)
                                }
                            }
                            item {
                                ClubCategorySection(sportsClubs, viewModel.sportsClubsState) {
                                    viewModel.ClubSelection(viewModel.sportsClubsState, it)
                                }
                            }
                            item {
                                ClubCategorySection(otherClubs, viewModel.otherClubsState) {
                                    viewModel.ClubSelection(viewModel.otherClubsState, it)
                                }
                            }
                        }
                    }
                }
            }


            val selectedCount by viewModel.selectedCount

            val buttonText = if (selectedCount > 0)
                "Follow $selectedCount club${if (selectedCount> 1) "s" else ""}"
            else
                "Let's Go!"

            PrimaryButton(
                text = buttonText,
                onClick = {  },
                modifier = Modifier
                    .padding(10.dp)
                    .width(346.dp)
                    .height(62.dp),
                enabled = true,
                isDarkModeEnabled = false
            )
        }
    }
}


@Composable
fun ClubCategorySection(
    data: clubDetails,
    clubList: SnapshotStateList<ClubCardState>,
    onClubSelected: (index: Int) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        categoryName(data.clubCategory)

        val rows = (data.clubName.size + 1) / 2
        val gridHeight = (rows * 220).dp + ((rows - 1) * 12).dp

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            userScrollEnabled = false,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(gridHeight)
        ) {
            itemsIndexed(clubList) { index, club ->
                ClubNameCard(
                    club = club,
                    onClick = { onClubSelected(index) }
                )
            }
        }
    }
}
