package com.tpc.nudj.ui.screens.myClubs

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.tpc.nudj.model.ClubUser
import com.tpc.nudj.repository.follow.FollowRepository
import com.tpc.nudj.repository.user.UserRepository
import com.tpc.nudj.utils.FirestoreCollections
import com.tpc.nudj.utils.FirestoreUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


@HiltViewModel
class MyClubsViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val followRepository: FollowRepository
) : ViewModel() {

    private val _allClubsList = MutableStateFlow<List<ClubUser>>(emptyList())
    val allClubsList: StateFlow<List<ClubUser>> = _allClubsList.asStateFlow()

    private val _selectedClubs = MutableStateFlow<List<ClubUser>>(emptyList())
    val selectedClubs: StateFlow<List<ClubUser>> = _selectedClubs.asStateFlow()

    private val _followedClubIds = MutableStateFlow<List<String>>(emptyList())

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _isSaved = MutableStateFlow(false)
    val isSaved: StateFlow<Boolean> = _isSaved.asStateFlow()
    private val firestore = FirebaseFirestore.getInstance()

    init {
        fetchFollowedClubs()
    }

    fun addSelectedClub(club: ClubUser) {
        val current = _selectedClubs.value.toMutableList()
        if (current.none { it.clubId == club.clubId }) {
            current.add(club)
            _selectedClubs.value = current
            fetchAllClubs()
        }
    }

    fun removeSelectedClub(clubId: String) {
        val currentList = _selectedClubs.value.toMutableList()
        currentList.removeAll { it.clubId == clubId }
        _selectedClubs.value = currentList
        fetchAllClubs()
    }


    fun fetchAllClubs() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val selectedClubIds = _selectedClubs.value.map { it.clubId }
                val clubCollection = firestore.collection(FirestoreCollections.CLUBS.path)
                    .get()
                    .await()
                val clubs = clubCollection.documents.mapNotNull {
                    it.data?.let { FirestoreUtils.toClubUser(it) }
                }.filter { club ->
                    !selectedClubIds.contains(club.clubId)
                }
                _allClubsList.value = clubs
            } catch (e: Exception) {
                Log.e("MyClubsViewModel", "Failed to fetch all clubs: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }


    fun fetchFollowedClubs() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val currentUser = userRepository.fetchCurrentUser()
                if (currentUser == null) {
                    Log.e("MyClubsViewModel", "No user found")
                    return@launch
                }
                val followedClubs = followRepository.fetchFollowingClubs(currentUser.userid)
                _followedClubIds.value = followedClubs.map { it.clubId }.toList()
                _selectedClubs.value = followedClubs.mapNotNull { data ->
                    val clubCollection = firestore.collection(FirestoreCollections.CLUBS.path)
                        .document(data.clubId)
                        .get()
                        .await()
                    clubCollection.data?.let { FirestoreUtils.toClubUser(it) }
                }
                fetchAllClubs()
            } catch (e: Exception) {
                Log.e("MyClubsViewModel", "Failed to fetch followed clubs: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }


    fun onFollowClubs() {
        viewModelScope.launch {
            _isLoading.value = true
            val currentUser = userRepository.fetchCurrentUser()
            if (currentUser == null) {
                Log.e("MyClubsViewModel", "No user found")
                return@launch
            }
            val selectedIds = _selectedClubs.value.map { it.clubId }
            val initialIds = _followedClubIds.value
            val toFollow = selectedIds.filter { !initialIds.contains(it) }
            val toUnfollow = initialIds.filter { !selectedIds.contains(it) }
            toFollow.forEach { clubId ->
                followRepository.followClub(currentUser.userid, clubId)
            }
            toUnfollow.forEach { clubId ->
                followRepository.unfollowClub(currentUser.userid, clubId)
            }
            _followedClubIds.value = selectedIds
            _isLoading.value = false
            _isSaved.value = true
            delay(2000)
            _isSaved.value = false
        }
    }

}
