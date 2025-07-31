package com.tpc.nudj.ui.screens.eventRegistration.eventRegistrationScreen2

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.google.firebase.Timestamp
import com.tpc.nudj.ui.components.NudjTextField
import com.tpc.nudj.ui.screens.eventRegistration.EventRegistrationDate
import com.tpc.nudj.ui.screens.eventRegistration.EventRegistrationUiState
import com.tpc.nudj.ui.theme.ClashDisplay
import com.tpc.nudj.ui.theme.LocalAppColors
import com.tpc.nudj.ui.theme.NudjTheme
import com.tpc.nudj.ui.theme.Orange
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventRegisterScreen2(
    uiState: EventRegistrationUiState,
    onEventDateInput: (EventRegistrationDate) -> Unit,
    onRemoveEventDate: (Int) -> Unit,
    onEventVenueInput: (String) -> Unit,
) {

    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    var durationInput by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf("") }
    var selectedTime by remember { mutableStateOf("") }
    val datePickerState = rememberDatePickerState()
    val timePickerState = rememberTimePickerState()
    val focusManager = LocalFocusManager.current
    val lazyListState = rememberLazyListState()

    LaunchedEffect(uiState.eventDates.size) {
        if(uiState.eventDates.isNotEmpty()){
            lazyListState.animateScrollToItem(uiState.eventDates.size)
        }
    }
    if (showTimePicker) {
        Dialog(
            onDismissRequest = {
                showTimePicker = false
            },
            content = {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = LocalAppColors.current.editTextBackground
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp),
                        horizontalAlignment = Alignment.End
                    ) {
                        Spacer(modifier = Modifier.padding(4.dp))
                        TimePicker(
                            state = timePickerState,
                            colors = TimePickerDefaults.colors(
                                selectorColor = Orange,
                                clockDialColor = LocalAppColors.current.editTextBackground,
                                periodSelectorBorderColor = LocalAppColors.current.editTextBorder,
                                clockDialSelectedContentColor = Color.White,
                                timeSelectorSelectedContainerColor = Orange,
                                timeSelectorUnselectedContainerColor = Color.Transparent,
                                periodSelectorSelectedContainerColor = Orange
                            )
                        )
                        Row {
                            TextButton(
                                onClick = {
                                    showTimePicker = false
                                },
                            ) {
                                Text(
                                    text = "Cancel",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        color = Orange
                                    )
                                )
                            }
                            TextButton(onClick = {
                                showTimePicker = false

                                val calendar = Calendar.getInstance().apply {
                                    set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                                    set(Calendar.MINUTE, timePickerState.minute)
                                }
                                val formattedTime = SimpleDateFormat("hh:mm a", Locale.getDefault())
                                    .format(calendar.time)
                                selectedTime = formattedTime
                            }) {
                                Text(
                                    "OK",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        color = Orange
                                    )
                                )
                            }
                        }
                    }
                }
            }
        )
    }
    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    showDatePicker = false
                    val selectedMillis = datePickerState.selectedDateMillis
                    if (selectedMillis != null) {
                        val formatter = SimpleDateFormat("MMMM dd, EEEE", Locale.getDefault())
                        val formatDate = formatter.format(Date(selectedMillis))
                        selectedDate = formatDate
                    }
                }) {
                    Text(
                        "OK",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = Orange
                        )
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDatePicker = false
                }) {
                    Text(
                        "Cancel",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = Orange
                        )
                    )
                }
            },
            colors = DatePickerDefaults.colors(
                containerColor = LocalAppColors.current.editTextBackground,
            ),
        ) {
            DatePicker(
                state = datePickerState,
                showModeToggle = false,
                colors = DatePickerDefaults.colors(
                    containerColor = LocalAppColors.current.editTextBackground,
                    headlineContentColor = Orange,
                    dividerColor = Orange,
                    todayDateBorderColor = Orange,
                    titleContentColor = Orange,
                    selectedDayContainerColor = Orange,
                    selectedDayContentColor = Color.White,
                    selectedYearContentColor = Color.White,
                    selectedYearContainerColor = Orange,
                ),
                title = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Pick a Date",
                            style = MaterialTheme.typography.displaySmall.copy(
                                fontFamily = ClashDisplay,
                            )
                        )
                    }
                }
            )
        }

    }

    LazyColumn(
        state = lazyListState,
        modifier = Modifier
            .fillMaxSize()
            .background(LocalAppColors.current.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Spacer(modifier = Modifier.padding(24.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 44.dp)
            ) {
                Text(
                    text = "Event Venue",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontFamily = ClashDisplay,
                        color = Orange
                    ),
                )
                NudjTextField(
                    value = uiState.eventVenue,
                    onValueChange = {
                        onEventVenueInput(it)
                    },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.8F.dp,
                            LocalAppColors.current.editTextBorder,
                            shape = RoundedCornerShape(16.dp)
                        )
                )
            }
            Spacer(modifier = Modifier.padding(10.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 44.dp)
            ) {
                Text(
                    text = "Event Dates",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontFamily = ClashDisplay,
                        color = Orange
                    ),
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(16.dp))
                            .background(LocalAppColors.current.editTextBackground)
                            .border(
                                width = 1.8F.dp,
                                LocalAppColors.current.editTextBorder,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .padding(top = 6.dp, bottom = 6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { showDatePicker = true },
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Spacer(modifier = Modifier.padding(25.dp))
                            if (selectedDate == "") {
                                Text(
                                    text = "Start Date",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontFamily = ClashDisplay,
                                        color = if (!isSystemInDarkTheme()) Color.Black.copy(
                                            alpha = 0.4f
                                        ) else Color.White.copy(
                                            alpha = 0.5f
                                        ),
                                        fontSize = 20.sp
                                    ),
                                    modifier = Modifier.padding(horizontal = 20.dp)
                                )
                            } else {
                                Text(
                                    text = selectedDate,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    style = TextStyle(fontSize = 20.sp),
                                    modifier = Modifier.padding(horizontal = 16.dp)
                                )
                            }
                            Spacer(modifier = Modifier.padding(25.dp))
                        }
                        HorizontalDivider(
                            thickness = 1.dp,
                            color = LocalAppColors.current.editTextBorder,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { showTimePicker = true },
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Spacer(modifier = Modifier.padding(25.dp))
                            if (selectedTime == "") {
                                Text(
                                    text = "Start Time",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontFamily = ClashDisplay,
                                        color = if (!isSystemInDarkTheme()) Color.Black.copy(
                                            alpha = 0.4f
                                        ) else Color.White.copy(
                                            alpha = 0.5f
                                        ),
                                        fontSize = 20.sp
                                    ),
                                    modifier = Modifier.padding(horizontal = 16.dp)
                                )
                            } else {
                                Text(
                                    text = selectedTime,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    style = TextStyle(fontSize = 20.sp),
                                    modifier = Modifier.padding(horizontal = 20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.padding(25.dp))
                        }
                        HorizontalDivider(
                            thickness = 1.dp,
                            color = LocalAppColors.current.editTextBorder,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                        OutlinedTextField(
                            value = durationInput,
                            onValueChange = {
                                durationInput = it
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedContainerColor = LocalAppColors.current.editTextBackground,
                                focusedContainerColor = LocalAppColors.current.editTextBackground,
                                focusedBorderColor = Color.Transparent,
                                unfocusedBorderColor = Color.Transparent
                            ),
                            textStyle = TextStyle(fontSize = 20.sp),
                            singleLine = true,
                            placeholder = {
                                Text(
                                    text = "Duration",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontFamily = ClashDisplay,
                                        color = if (!isSystemInDarkTheme()) Color.Black.copy(
                                            alpha = 0.4f
                                        ) else Color.White.copy(
                                            alpha = 0.5f
                                        ),
                                        fontSize = 20.sp
                                    ),
                                    modifier = Modifier.padding(horizontal = 6.dp)
                                )
                            }
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    IconButton(
                        modifier = Modifier.size(60.dp),
                        onClick = {
                            val calendar = Calendar.getInstance().apply {
                                timeInMillis =
                                    datePickerState.selectedDateMillis ?: System.currentTimeMillis()
                                set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                                set(Calendar.MINUTE, timePickerState.minute)
                                set(Calendar.SECOND, 0)
                                set(Calendar.MILLISECOND, 0)
                            }
                            val timestamp = Timestamp(calendar.time)
                            if (selectedDate != "" && selectedTime != "" && durationInput != "") {
                                onEventDateInput(
                                    EventRegistrationDate(
                                        startDateTime = timestamp,
                                        estimatedDuration = durationInput
                                    )
                                )
                                durationInput = ""
                                selectedTime = ""
                                selectedDate = ""
                            }
                            focusManager.clearFocus()
                        },
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = Orange
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add event dates",
                            tint = Color.White,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.padding(10.dp))
                uiState.eventDates.forEachIndexed { index, date ->
                    val visible = remember { mutableStateOf(false) }
                    LaunchedEffect(Unit) {
                        visible.value = true
                    }

                    AnimatedVisibility(
                        visible = visible.value,
                        enter = slideInHorizontally(initialOffsetX = { -it }) + fadeIn(),
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "•",
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        fontFamily = ClashDisplay,
                                        color = Orange
                                    ),
                                    modifier = Modifier.padding(end = 6.dp)
                                )
                                Spacer(modifier = Modifier.width(3.dp))
                                Text(
                                    text = formatEventDate(date),
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        fontFamily = ClashDisplay,
                                        color = Orange
                                    ),
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(end = 8.dp),
                                    textAlign = TextAlign.Start
                                )
                                IconButton(
                                    onClick = { onRemoveEventDate(index) },
                                    modifier = Modifier.size(36.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Delete the event date",
                                        tint = Orange,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(4.dp))

                            HorizontalDivider(
                                thickness = 1.dp,
                                color = Orange,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }
}


fun formatEventDate(eventDate: EventRegistrationDate): String {

    val date = eventDate.startDateTime.toDate()
    val dateFormatter = SimpleDateFormat("MMMM dd, EEEE", Locale.getDefault())
    val timeFormatter = SimpleDateFormat("hh:mm a", Locale.getDefault())
    val formatedDate = dateFormatter.format(date)
    val formatedTime = timeFormatter.format(date)

    return "$formatedDate - $formatedTime - ${eventDate.estimatedDuration}"

}

@Preview
@Composable
fun EventScreen2Preview() {
    NudjTheme {
        EventRegisterScreen2(
            uiState = EventRegistrationUiState(),
            onEventDateInput = {},
            onRemoveEventDate = {},
            onEventVenueInput = {}
        )
    }
}