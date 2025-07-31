package com.tpc.nudj.ui.screens.eventRegistration.eventRegistrationScreen1

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tpc.nudj.ui.components.NudjTextField
import com.tpc.nudj.ui.screens.eventRegistration.EventRegistrationUiState
import com.tpc.nudj.ui.theme.ClashDisplay
import com.tpc.nudj.ui.theme.LocalAppColors
import com.tpc.nudj.ui.theme.NudjTheme
import com.tpc.nudj.ui.theme.Orange

@Composable
fun EventRegisterScreen1(
    uiState: EventRegistrationUiState,
    onEventNameInput: (String) -> Unit,
    onOrganizerNameInput: (String) -> Unit,
    onOrganizerContactNumberInput: (String) -> Unit,
    onEventDescriptionInput: (String) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(LocalAppColors.current.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Spacer(modifier = Modifier.padding(16.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 44.dp)
            ) {
                Text(
                    text = "Event Name",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontFamily = ClashDisplay,
                        color = Orange
                    ),
                )
                NudjTextField(
                    value = uiState.eventName,
                    onValueChange = { onEventNameInput(it) },
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
                    text = "Organizer Name",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontFamily = ClashDisplay,
                        color = Orange
                    ),
                )
                NudjTextField(
                    value = uiState.organizerName,
                    onValueChange = { onOrganizerNameInput(it) },
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
                    text = "Organizer Contact Number",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontFamily = ClashDisplay,
                        color = Orange
                    ),
                )
                NudjTextField(
                    value = uiState.organizerContactNumber,
                    onValueChange = { onOrganizerContactNumberInput(it) },
                    singleLine = true,
                    leadingIcon = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(start = 6.dp, end = 6.dp)
                        ) {
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = "+91",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontFamily = ClashDisplay,
                                    color = Orange,
                                    fontSize = 20.sp
                                ),
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            VerticalDivider(
                                modifier = Modifier
                                    .height(30.dp)
                                    .width(1.dp),
                                color = LocalAppColors.current.editTextBorder
                            )
                        }
                    },
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
                    text = "Event Description",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontFamily = ClashDisplay,
                        color = Orange
                    ),
                )
                NudjTextField(
                    value = uiState.eventDescription,
                    onValueChange = { onEventDescriptionInput(it) },
                    singleLine = false,
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 150.dp, max = 250.dp)
                        .border(
                            width = 1.8F.dp,
                            LocalAppColors.current.editTextBorder,
                            shape = RoundedCornerShape(16.dp)
                        )
                )
            }
        }
    }
}

@Preview
@Composable
fun EventScreen1Preview() {
    NudjTheme {
        EventRegisterScreen1(
            uiState = EventRegistrationUiState(),
            onEventNameInput = {},
            onEventDescriptionInput = {},
            onOrganizerNameInput = {},
            onOrganizerContactNumberInput = {}
        )
    }
}