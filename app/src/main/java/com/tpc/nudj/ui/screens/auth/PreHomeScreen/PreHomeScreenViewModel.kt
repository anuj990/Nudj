package com.tpc.nudj.ui.screens.auth.PreHomeScreen

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.tpc.nudj.ui.theme.EditTextBackgroundColorLight
import com.tpc.nudj.ui.theme.Orange
import com.tpc.nudj.ui.theme.Purple
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class ClubCardState(
    val clubName: String,
    var isSelected: Boolean = false
)
data class ClubCategoryState(
    val category: String,
    val clubList: SnapshotStateList<ClubCardState>,
    val baseColor: Color,
    val textColor: Color
)

@HiltViewModel
class PreHomeScreenViewModel @Inject constructor() : ViewModel() {

    private val _culturalClubs = mutableStateListOf<ClubCardState>().apply {
        addAll(
            listOf("Aavartan", "Abhivyakti", "Jazbaat", "Saaz", "Samvaad", "Shutterbox")
                .map { ClubCardState(it) }
        )
    }

    private val _technicalClubs = mutableStateListOf<ClubCardState>().apply {
        addAll(
            listOf(
                "Astronomy and Physics Society", "Aero Fabrication", "Business and Management",
                "CAD and 3D Printing", "Electronics and Robotics Society", "IIITDMJ Racing Club",
                "The Programming Club"
            ).map { ClubCardState(it) }
        )
    }

    private val _sportsClubs = mutableStateListOf<ClubCardState>().apply {
        addAll(
            listOf(
                "Athletics", "Badminton", "Basketball", "Chess", "Carrom", "Cricket",
                "Football", "Gymnasium", "Kabaddi", "Lawn Tennis", "Table Tennis", "Volleyball"
            ).map { ClubCardState(it) }
        )
    }

    val culturalClubsState = ClubCategoryState(
        category = "Cultural clubs",
        clubList = _culturalClubs,
        baseColor = Purple,
        textColor = Color.White
    )

    val technicalClubsState = ClubCategoryState(
        category = "Technical clubs",
        clubList = _technicalClubs,
        baseColor = EditTextBackgroundColorLight,
        textColor = Purple
    )

    val sportsClubsState = ClubCategoryState(
        category = "Sport clubs",
        clubList = _sportsClubs,
        baseColor = Orange,
        textColor = Color.White
    )

    val selectedCount = derivedStateOf {
        (_culturalClubs + _technicalClubs + _sportsClubs).count { it.isSelected }
    }

    fun ClubSelection(clubList: SnapshotStateList<ClubCardState>, index: Int) {
        clubList[index] = clubList[index].copy(isSelected = !clubList[index].isSelected)
    }
}
