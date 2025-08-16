package com.tpc.nudj.ui.screens.homeScreen

import com.google.firebase.Timestamp
import com.tpc.nudj.model.ClubUser
import com.tpc.nudj.model.Event
import java.util.Date
import java.util.logging.Filter

data class FilterData(
    val filterName: String,
    var isSelected: Boolean
)
data class HomeUiState(
    val searchQuery: String = "",
    val eventList: List<Event> = emptyList(),
    val isLoading: Boolean = false,
    val filterClicked: Boolean = false,
    val filters: List<FilterData> = listOf(
        FilterData("Popular", false),
        FilterData("Recently Posted", false),
        FilterData("Date: Closest to Farthest", false),
        FilterData("Date: Farthest to Closest", false)
    ),
    val clubList: List<ClubUser> = emptyList(),
    val clubClicked: Boolean = false,
    val clubFilters: List<FilterData> = emptyList()
)

