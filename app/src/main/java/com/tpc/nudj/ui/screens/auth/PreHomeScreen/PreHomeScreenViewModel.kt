package com.tpc.nudj.ui.screens.auth.PreHomeScreen

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel

data class ClubCardState(
    val clubName: String,
    var isSelected: Boolean = false
)
class PreHomeScreenViewModel : ViewModel() {

    private val _culturalClubs = mutableStateListOf<ClubCardState>().apply {
        addAll(culturalClubs.clubName.map { ClubCardState(it) })
    }
    private val _technicalClubs = mutableStateListOf<ClubCardState>().apply {
        addAll(technicalClubs.clubName.map { ClubCardState(it) })
    }
    private val _sportsClubs = mutableStateListOf<ClubCardState>().apply {
        addAll(sportsClubs.clubName.map { ClubCardState(it) })
    }
    private val _otherClubs = mutableStateListOf<ClubCardState>().apply {
        addAll(otherClubs.clubName.map { ClubCardState(it) })
    }

    val culturalClubsState: SnapshotStateList<ClubCardState> = _culturalClubs
    val technicalClubsState: SnapshotStateList<ClubCardState> = _technicalClubs
    val sportsClubsState: SnapshotStateList<ClubCardState> = _sportsClubs
    val otherClubsState: SnapshotStateList<ClubCardState> = _otherClubs

    val selectedCount = derivedStateOf {
        (_culturalClubs + _technicalClubs + _sportsClubs + _otherClubs).count { it.isSelected }
    }

    fun ClubSelection(clubList: SnapshotStateList<ClubCardState>, index: Int) {
        val current = clubList[index]
        clubList[index] = current.copy(isSelected = !current.isSelected)
    }
}
