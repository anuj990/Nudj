package com.tpc.nudj.ui.screens.homeScreen

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.tpc.nudj.ui.theme.LocalAppColors
import com.tpc.nudj.ui.theme.NudjTheme
import com.google.firebase.Timestamp
import com.tpc.nudj.ui.theme.ClashDisplay
import com.tpc.nudj.ui.theme.Orange
import com.tpc.nudj.ui.theme.Purple
import java.text.SimpleDateFormat
import java.util.*
import com.tpc.nudj.R
import com.tpc.nudj.ui.theme.EditTextBackgroundColorDark
import com.tpc.nudj.ui.theme.EditTextBackgroundColorLight

@Composable
fun HomeScreenLayout() {
    HomeScreenContent(
        onBack = {},
        onFilterClicked = {}
    )
}

@Composable
fun HomeScreenContent(
    onBack: () -> Unit,
    onFilterClicked: () -> Unit
) {
    var query by remember { mutableStateOf("") }
    var searchResult by remember { mutableStateOf(false) }
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
                            query = it
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
                            query = ""
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
                            query = it
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
                            query = ""
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
            itemsIndexed(sampleEvents) { index, eventData ->
                EventCard(
                    eventId = eventData.eventId,
                    index = index,
                    onEventCardClicked = {},
                    data = eventData
                )
                Spacer(modifier = Modifier.padding(10.dp))
            }
        }
    }
}


@Composable
fun EventCard(
    eventId: String,
    index: Int,
    onEventCardClicked:()->Unit,
    data: HomeUiState
) {
    val date = data.eventDates.first().startDateTime.toDate()
    val formattedDate = SimpleDateFormat("MMMM dd, EEEE", Locale.getDefault()).format(date)
    val cardColor =
        if (index % 2 == 0) Orange else if (isSystemInDarkTheme()) EditTextBackgroundColorDark else Purple
    val logo = if (cardColor == Orange) R.drawable.purple_n else R.drawable.orange_n

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable{onEventCardClicked()},
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
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
                    eventBanner = data.eventBannerUrl,
                    logo = logo
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier.weight(1.7f),
                contentAlignment = Alignment.Center
            ) {
                EventCardMainContent(
                    cardColor = cardColor,
                    clubName = data.clubId,
                    eventName = data.eventName,
                    dateTime = formattedDate,
                    eventVenue = data.eventVenue
                )
            }
        }
    }
}


@Composable
fun EventCardBanner(
    eventBanner: String,
    @DrawableRes logo: Int
) {
    AsyncImage(
        model = eventBanner,
        contentDescription = "Event Banner",
        modifier = Modifier.clip(RoundedCornerShape(12.dp)),
        contentScale = ContentScale.FillBounds,
        placeholder = painterResource(id = logo),
        error = painterResource(id = logo)
    )
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
                style = MaterialTheme.typography.titleLarge.copy(
                    fontFamily = ClashDisplay,
                    color = Color.White
                )
            )
        }
        Spacer(modifier = Modifier.padding(8.dp))
        Text(
            text = eventName,
            style = MaterialTheme.typography.headlineLarge.copy(
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
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = dateTime,
                    style = MaterialTheme.typography.titleMedium.copy(
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

val sampleEvents = listOf<HomeUiState>(
    HomeUiState(
        eventId = "1",
        clubId = "SAAZ",
        eventName = "SAAZ NIGHT'25",
        eventDescription = "A collaborative UI/UX design event for rapid prototyping.",
        eventVenue = "OAT",
        eventBannerUrl = "https://i.ibb.co/7dzZq7rr/SAAZ-NIGHT-25-poster.jpg",
        organizerName = "Saaz Club",
        organizerContactNumber = "+91 941XXXXXXX",
        eventDates = listOf(
            EventTime(
                startDateTime = Timestamp(
                    Date(
                        2025 - 1900,
                        5,
                        25,
                        16,
                        0
                    )
                ),
                estimatedDuration = "2 hours"
            )
        ),
        faqs = listOf(
            FAQS("Who can participate?", "Anyone from any branch."),
            FAQS("Do I need a team?", "No, individual entries are allowed.")
        ),
        isDeleted = false,
        creationTimestamp = Timestamp.now(),
        lastUpdatedTimestamp = Timestamp.now()
    ),

    HomeUiState(
        eventId = "2",
        eventDates = listOf(
            EventTime(
                estimatedDuration = "24 hours"
            )
        ),
        faqs = emptyList(),
        isDeleted = false,
        creationTimestamp = Timestamp.now(),
        lastUpdatedTimestamp = Timestamp.now()
    ),
    HomeUiState(
        eventId = "2",
        eventDates = listOf(
            EventTime(
                estimatedDuration = "24 hours"
            )
        ),
        faqs = emptyList(),
        isDeleted = false,
        creationTimestamp = Timestamp.now(),
        lastUpdatedTimestamp = Timestamp.now()
    ),
    HomeUiState(
        eventId = "2",
        eventDates = listOf(
            EventTime(
                estimatedDuration = "24 hours"
            )
        ),
        faqs = emptyList(),
        isDeleted = false,
        creationTimestamp = Timestamp.now(),
        lastUpdatedTimestamp = Timestamp.now()
    )
)

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomeScreenPreview() {
    NudjTheme {
        HomeScreenContent(
            onBack = {},
            onFilterClicked = {}
        )
    }
}
