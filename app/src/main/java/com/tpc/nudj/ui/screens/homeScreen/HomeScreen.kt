package com.tpc.nudj.ui.screens.homeScreen

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.tpc.nudj.ui.theme.LocalAppColors
import com.tpc.nudj.ui.theme.ClashDisplay
import com.tpc.nudj.ui.theme.Orange
import com.tpc.nudj.ui.theme.Purple
import java.text.SimpleDateFormat
import java.util.*
import com.tpc.nudj.R
import com.tpc.nudj.model.ClubUser
import com.tpc.nudj.model.Event
import com.tpc.nudj.ui.components.LoadingScreenOverlay
import com.tpc.nudj.ui.components.PrimaryButton
import com.tpc.nudj.ui.components.SecondaryButton
import com.tpc.nudj.ui.theme.CardBackgroundColor
import com.tpc.nudj.ui.theme.EditTextBackgroundColorDark
import com.tpc.nudj.ui.theme.EditTextBackgroundColorLight
import org.w3c.dom.Text

@Composable
fun HomeScreenLayout(
    viewModel: HomeScreenViewModel = hiltViewModel(),
    onEventCardClicked: (String) -> Unit
) {
    val uiState = viewModel.uiState.collectAsState()
    val filterClicked = uiState.value.filterClicked
    val clubClicked = uiState.value.clubClicked

    HomeScreenContent(
        onBack = {},
        onFilterClicked = viewModel::onFilterClicked,
        query = uiState.value.searchQuery,
        onSearchQueryChanged = viewModel::onSearchQueryChanged,
        onQueryCleared = viewModel::onQueryCleared,
        sampleEvents = uiState.value.eventList,
        onEventCardClicked = onEventCardClicked
    )
    if (uiState.value.isLoading) {
        LoadingScreenOverlay()
    }
    if (filterClicked) {
        FilterBottomSheet(
            filterList = uiState.value.filters,
            onDismissRequest = viewModel::onDismissRequest
            ,
            onSelectFilter = viewModel::onSelectFilter,
            onClickClubsFilter = viewModel::onClickClubsFilter,
            onSelectClubFilter = viewModel::onSelectClubFilter,
            clubFilterList = uiState.value.clubFilters
        )
    }
    if (clubClicked) {
        ClubDialog(
            onDismissRequest = viewModel::onDismissDialog,
            filterList = uiState.value.filters,
            clubList = uiState.value.clubList,
            addFilterFromClub = {
                viewModel.addFilterFromClub(it)
                viewModel.onDismissDialog()
            }
        )
    }


}

@Composable
fun HomeScreenContent(
    onBack: () -> Unit,
    onFilterClicked: () -> Unit,
    query: String,
    onSearchQueryChanged: (String) -> Unit,
    onQueryCleared: () -> Unit,
    sampleEvents: List<Event>,
    onEventCardClicked: (String) -> Unit
) {
    var searchResult by remember { mutableStateOf(false) }
    val filterEvents = sampleEvents.filter {
        it.eventName.lowercase().contains(query.lowercase())
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LocalAppColors.current.background)
            .padding(start = 16.dp, end = 16.dp)
    ) {
        TopBar(
            onBackClicked = { onBack() }
        )
        Spacer(modifier = Modifier.height(8.dp))
        AnimatedVisibility(
            visible = !searchResult,
            enter = fadeIn(animationSpec = tween(300)) + expandVertically(),
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SearchBar(
                        value = query,
                        onValueChange = {
                            onSearchQueryChanged(it)
                            if (it.isEmpty()) {
                                searchResult = false
                            }
                        },
                        onSearch = {
                            if (query.trim().isNotEmpty()) {
                                searchResult = true
                            }
                        },
                        clearSearch = query != "",
                        onClearClicked = {
                            onQueryCleared()
                            searchResult = false
                        },
                        searchBarColor = EditTextBackgroundColorLight,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.padding(6.dp))
                    IconButton(
                        onClick = { onFilterClicked() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Tune,
                            contentDescription = "Search Filter",
                            modifier = Modifier.size(40.dp),
                            tint = LocalAppColors.current.appTitle
                        )
                    }
                }
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text = "Welcome Sir/Madam!",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontFamily = ClashDisplay,
                        color = LocalAppColors.current.appTitle
                    )
                )
            }
        }
        AnimatedVisibility(
            visible = searchResult,
            enter = fadeIn(animationSpec = tween(300)) + expandVertically(),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        1.dp,
                        Color.Black,
                        shape = RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp)
                    )
                    .background(
                        EditTextBackgroundColorLight,
                        shape = RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp)
                    ),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 14.dp)
                        .padding(horizontal = 10.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SearchBar(
                        value = query,
                        onValueChange = {
                            onSearchQueryChanged(it)
                            if (it.isEmpty()) {
                                searchResult = false
                            }
                        },
                        onSearch = {
                            if (query.trim().isNotEmpty()) {
                                searchResult = true
                            }
                        },
                        clearSearch = query != "",
                        onClearClicked = {
                            onQueryCleared()
                            searchResult = false
                        },
                        searchBarColor = Color.White,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.padding(6.dp))
                    IconButton(
                        onClick = { onFilterClicked() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Tune,
                            contentDescription = "Search Filter",
                            modifier = Modifier.size(40.dp),
                            tint = Purple
                        )
                    }
                }
                Spacer(modifier = Modifier.padding(6.dp))
                Text(
                    text = "Search Results for : ${query}",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontFamily = ClashDisplay
                    ),
                    color = Color.Black,
                    modifier = Modifier.padding(start = 20.dp)
                )
                Spacer(modifier = Modifier.padding(6.dp))
            }
        }

        Spacer(modifier = Modifier.padding(8.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            itemsIndexed(filterEvents) { index, eventData ->
                EventCard(
                    event = eventData,
                    index = index,
                    onEventCardClicked = {
                        onEventCardClicked(eventData.eventId)
                    }
                )
                Spacer(modifier = Modifier.padding(10.dp))
            }
        }
    }
}


@Composable
fun EventCard(
    event: Event,
    index: Int,
    onEventCardClicked: () -> Unit,
) {
    val date = event.eventDates.first().startDateTime.toDate()
    val formattedDate = SimpleDateFormat("MMMM dd, EEEE", Locale.getDefault()).format(date)
    val cardColor =
        if (index % 2 == 0) Orange else if (isSystemInDarkTheme()) EditTextBackgroundColorDark else Purple
    val logo = if (cardColor == Orange) R.drawable.purple_n else R.drawable.orange_n

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onEventCardClicked() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .aspectRatio(0.65f)
                    .background(Color.White, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center,
            ) {
                EventCardBanner(
                    eventBanner = event.eventBannerUrl,
                    logo = logo,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier.weight(1.7f),
                contentAlignment = Alignment.Center
            ) {
                EventCardMainContent(
                    cardColor = cardColor,
                    clubName = event.organizerName,
                    eventName = event.eventName,
                    dateTime = formattedDate,
                    eventVenue = event.eventVenue
                )
            }
        }
    }
}


@Composable
fun EventCardBanner(
    modifier: Modifier,
    eventBanner: String?,
    @DrawableRes logo: Int
) {
    AsyncImage(
        model = eventBanner,
        contentDescription = "Event Banner",
        modifier = modifier.clip(RoundedCornerShape(12.dp)),
        contentScale = ContentScale.Crop,
        placeholder = painterResource(id = logo),
        error = painterResource(id = logo)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheet(
    filterList: List<FilterData>,
    clubFilterList: List<FilterData>,
    onDismissRequest: () -> Unit,
    onSelectFilter: (FilterData) -> Unit,
    onSelectClubFilter: (FilterData) -> Unit,
    onClickClubsFilter: () -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = rememberModalBottomSheetState(),
        containerColor = CardBackgroundColor,
        dragHandle = null
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Text(
                text = "Filters",
                fontFamily = ClashDisplay,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(10.dp)
            )
            FlowRow {
                Box(
                    modifier = Modifier.padding(10.dp)
                ) {
                    SecondaryButton(
                        text = "Clubs",
                        onClick = onClickClubsFilter,
                        isDarkModeEnabled = isSystemInDarkTheme()
                    )
                }
                filterList.forEach { item ->
                    key(item.filterName) {
                        FilterCard(item, onSelectFilter)
                    }
                }
                clubFilterList.forEach { item ->
                    key(item.filterName) {
                        FilterCard(item, onSelectClubFilter)
                    }
                }
            }
        }
    }
}

@Composable
fun FilterCard(
    filter: FilterData,
    onSelectFilter: (FilterData) -> Unit,
) {
    Box(
        modifier = Modifier.padding(10.dp)
    ) {
        if (!filter.isSelected) {
            SecondaryButton(
                text = filter.filterName,
                onClick = {
                    onSelectFilter(filter)
                },
                isDarkModeEnabled = isSystemInDarkTheme(),
            )
        } else {
            PrimaryButton(
                text = filter.filterName,
                onClick = {
                    onSelectFilter(filter)
                },
                isDarkModeEnabled = isSystemInDarkTheme(),
            )
        }
    }
}


@Composable
fun EventCardMainContent(
    cardColor: Color,
    clubName: String,
    eventName: String,
    dateTime: String,
    eventVenue: String
) {
    Column(
        modifier = Modifier
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = if (cardColor == Orange) Purple else Orange,
                    shape = RoundedCornerShape(50)
                )
                .padding(horizontal = 20.dp, vertical = 8.dp)
        ) {
            Text(
                text = clubName,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontFamily = ClashDisplay,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                )
            )
        }
        Spacer(modifier = Modifier.padding(8.dp))
        Text(
            text = eventName,
            style = MaterialTheme.typography.titleLarge.copy(
                fontFamily = ClashDisplay,
                color = if (cardColor == Orange) Purple else Orange
            ),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.padding(4.dp))
        Box(
            modifier = Modifier,
            contentAlignment = Alignment.Center
        ) {
            Column() {
                Text(
                    text = dateTime,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = ClashDisplay,
                        fontSize = 19.sp,
                        color = Color.White
                    )
                )
                Text(
                    text = eventVenue,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontFamily = ClashDisplay,
                        fontSize = 19.sp,
                        color = Color.White
                    )
                )
            }
        }

    }
}

@Composable
fun TopBar(
    onBackClicked: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
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

@Composable
fun ClubDialog(
    onDismissRequest: () -> Unit,
    filterList: List<FilterData>,
    clubList: List<ClubUser>,
    addFilterFromClub: (ClubUser) -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = CardBackgroundColor),
            border = BorderStroke(width = 2.dp, color = Color.Black),
            modifier = Modifier.fillMaxWidth()
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,

            ) {
                item {
                    Spacer(Modifier.height(10.dp))
                    Text(
                        text = "Clubs",
                        fontFamily = ClashDisplay,
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(10.dp))
                }
                itemsIndexed(clubList) { index, club ->
                    Button(
                        modifier = Modifier.fillMaxWidth(0.9f),
                        onClick = {
                            addFilterFromClub(club)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        shape = RoundedCornerShape(20),
                        border = BorderStroke(width = 1.dp, color = Color.Black)
                    ) {
                        Text(
                            text = club.clubName,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontFamily = ClashDisplay
                            ),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    onSearch: () -> Unit,
    searchBarColor: Color,
    clearSearch: Boolean,
    onClearClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    Box(
        modifier = modifier
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = searchBarColor,
                focusedContainerColor = Color.White,
                unfocusedTextColor = Color.Black,
                focusedTextColor = Color.Black,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Purple,
                    modifier = Modifier
                        .size(48.dp)
                        .padding(start = 12.dp)
                )
            },
            trailingIcon = {
                if (clearSearch) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Clear Search",
                        tint = Purple,
                        modifier = Modifier
                            .size(40.dp)
                            .padding(end = 10.dp)
                            .clickable { onClearClicked() }
                    )
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearch()
                    focusManager.clearFocus()
                }
            ),
            textStyle = MaterialTheme.typography.titleLarge,
            shape = RoundedCornerShape(30.dp),
            modifier = Modifier
                .border(1.dp, Color.Black, shape = RoundedCornerShape(30.dp))
                .fillMaxWidth()
        )
    }
}