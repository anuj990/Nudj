package com.tpc.nudj.ui.screens.homeScreen

import android.util.Log
import android.widget.Filter
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tpc.nudj.model.ClubUser
import com.tpc.nudj.model.Event
import com.tpc.nudj.repository.events.EventsRepository
import com.tpc.nudj.repository.follow.FollowRepository
import com.tpc.nudj.repository.rsvp.RsvpRepository
import com.tpc.nudj.repository.user.UserRepository
import com.tpc.nudj.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val followRepository: FollowRepository,
    private val rsvpRepository: RsvpRepository,
    private val eventsRepository: EventsRepository,
): ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    private val rsvpCounts = mutableMapOf<String, Int>()

    private var masterEventList: List<Event> = emptyList()

    init {
        fetchUpcomingEvents()
        fetchAllClubs()
    }

    private fun fetchUpcomingEvents() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                masterEventList = eventsRepository.fetchAllUpcomingEvents()
                _uiState.update {
                    it.copy(
                        eventList = masterEventList
                    )
                }
                fetchRsvpCountsForEvents(masterEventList)
            } catch (e: Exception) {
                Log.e("HomeScreenViewModel", "Error fetching data!")
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    private suspend fun fetchRsvpCountsForEvents(events: List<Event>) {
        for (event in events) {
            val count = rsvpRepository.getRsvpCountForEvent(eventId = event.eventId)
            rsvpCounts[event.eventId] = count

        }
        if(_uiState.value.filters.any {it.filterName == "Popular" && it.isSelected}) {
            updateAndDisplayEvents()
        }
    }

    private fun applySorting(events: List<Event>): List<Event> {
        val selectedSortFilter = _uiState.value.filters.firstOrNull {it.isSelected}
        return when (selectedSortFilter?.filterName) {
            "Popular" -> events.sortedByDescending { rsvpCounts[it.eventId] ?: 0 }
            "Recently Posted" -> events.sortedByDescending { it.creationTimestamp }
            "Date: Closest to Farthest" -> events.sortedBy { it.eventDates.firstOrNull()?.startDateTime }
            "Date: Farthest to Closest" -> events.sortedByDescending { it.eventDates.firstOrNull()?.startDateTime }
            else -> events
        }
    }

    private fun updateAndDisplayEvents() {
        val selectedClubFilters = _uiState.value.clubFilters.filter { it.isSelected }.map { it.filterName }
        val filteredEvents = if (selectedClubFilters.isEmpty()) {
            masterEventList
        } else {
            masterEventList.filter {
                event -> selectedClubFilters.contains(event.organizerName)
            }
        }
        val sortedEvents = applySorting(filteredEvents)
        _uiState.update {
            it.copy(eventList = sortedEvents)
        }
    }

    fun fetchAllClubs() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    clubList = userRepository.fetchAllClubs()
                )
            }
        }
    }


    fun onSearchQueryChanged(input: String) {
        _uiState.update {
            it.copy(
                searchQuery = input
            )
        }
    }
    fun onQueryCleared() {
        _uiState.update {
            it.copy(
                searchQuery = ""
            )
        }
    }
    fun onFilterClicked() {
        _uiState.update {
            it.copy(
                filterClicked = true
            )
        }
    }

    fun onDismissRequest() {
        _uiState.update {
            it.copy(
                filterClicked = false
            )
        }
    }

    fun onSelectFilter(filter: FilterData) {
        _uiState.update { currentState ->
            val updatedFilters = currentState.filters.map {
                if (it.filterName == filter.filterName) {
                    it.copy(isSelected = !it.isSelected)
                } else {
                    it.copy(isSelected = false)
                }
            }
            currentState.copy(filters = updatedFilters)
        }
        updateAndDisplayEvents()
    }

    fun onSelectClubFilter(filter: FilterData) {
        _uiState.update {
            val updatedFilters = it.clubFilters.map {
                if (it.filterName == filter.filterName) {
                    it.copy(isSelected = !it.isSelected)
                } else {
                    it.copy(isSelected = it.isSelected)
                }
            }
            it.copy(clubFilters = updatedFilters)
        }
        updateAndDisplayEvents()
    }

    fun addFilterFromClub(club: ClubUser) {
        _uiState.update {
            val newFilter = FilterData(filterName = club.clubName, isSelected = true)
            val updatedFilters = it.clubFilters + newFilter
            it.copy(
                clubFilters = updatedFilters,
                clubList = it.clubList - club
            )
        }
        updateAndDisplayEvents()
    }

    fun onClickClubsFilter() {
        _uiState.update {
            it.copy(
                clubClicked = !it.clubClicked
            )
        }
    }

    fun onDismissDialog() {
        _uiState.update {
            it.copy(
                clubClicked = false
            )
        }
    }
}