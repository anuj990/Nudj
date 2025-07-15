package com.tpc.nudj.ui.screens.auth.PreHomeScreen

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.tpc.nudj.model.ClubUser
import com.tpc.nudj.repository.follow.FollowRepository
import com.tpc.nudj.repository.follow.FollowRepositoryImpl
import com.tpc.nudj.repository.user.UserRepository
import com.tpc.nudj.repository.user.UserRepositoryImpl
import com.tpc.nudj.ui.theme.EditTextBackgroundColorLight
import com.tpc.nudj.ui.theme.Orange
import com.tpc.nudj.ui.theme.Purple
import com.tpc.nudj.utils.FirestoreCollections
import com.tpc.nudj.utils.FirestoreUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.collections.map

data class ClubCardState(
    val club: ClubUser,
    var isSelected: Boolean = false
)
data class ClubCategoryState(
    val category: String,
    val clubList: SnapshotStateList<ClubCardState>,
    val baseColor: Color,
    val textColor: Color
)

@HiltViewModel
class PreHomeScreenViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val followRepository: FollowRepository
) : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()

    init {
        loadAndMapClubs()
    }

    private val firebaseAuth: FirebaseAuth= FirebaseAuth.getInstance()

    suspend fun fetchClubsByCategory(category: String): List<ClubUser> {
        try {
            val querySnapshot = firestore.collection(FirestoreCollections.CLUBS.path)
                .whereEqualTo("clubCategory", category)
                .get()
                .await()

            val clubList = querySnapshot.documents.mapNotNull { document->
                document.data?.let { data->
                    FirestoreUtils.toClubUser(data)
                }
            }
            return clubList
        } catch (e: Exception) {
            return emptyList()
        }
    }

    private var _culturalClubs = mutableStateListOf<ClubCardState>()
    private var _technicalClubs = mutableStateListOf<ClubCardState>()
    private var _sportsClubs = mutableStateListOf<ClubCardState>()
    private fun loadAndMapClubs(){
        viewModelScope.launch {val culturalClubUsers: List<ClubUser> = fetchClubsByCategory("CULTURAL")
            val technicalClubUsers: List<ClubUser> = fetchClubsByCategory("TECHNICAL")
            val sportsClubUsers: List<ClubUser> = fetchClubsByCategory("SPORTS")

            _culturalClubs.clear()
            _culturalClubs.addAll(culturalClubUsers.map { ClubCardState(it) })

            _technicalClubs.clear()
            _technicalClubs.addAll(technicalClubUsers.map { ClubCardState(it) })

            _sportsClubs.clear()
            _sportsClubs.addAll(sportsClubUsers.map { ClubCardState(it) })
        }
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

    suspend fun onClickFollow() {
        val list = (_culturalClubs + _technicalClubs + _sportsClubs)
        val userId = firebaseAuth.currentUser?.uid
        if (userId != null) {
            list.forEach { club ->
                if (club.isSelected) {
                    followRepository.followClub(userId, club.club.clubId)
                } else { }
            }
        }

    }

    fun ClubSelection(clubList: SnapshotStateList<ClubCardState>, index: Int) {
        clubList[index] = clubList[index].copy(isSelected = !clubList[index].isSelected)
    }
}
