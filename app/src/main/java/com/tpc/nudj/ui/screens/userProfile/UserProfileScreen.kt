package com.tpc.nudj.ui.screens.userProfile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.google.firebase.Timestamp
import com.tpc.nudj.R
import com.tpc.nudj.model.Event
import com.tpc.nudj.model.EventDate
import com.tpc.nudj.model.enums.Branch
import com.tpc.nudj.ui.components.LoadingScreenOverlay
import com.tpc.nudj.ui.components.NudjTextField
import com.tpc.nudj.ui.components.PrimaryButton
import com.tpc.nudj.ui.components.TertiaryButton
import com.tpc.nudj.ui.theme.CardBackgroundColor
import com.tpc.nudj.ui.theme.ClashDisplay
import com.tpc.nudj.ui.theme.EditTextBackgroundColorDark
import com.tpc.nudj.ui.theme.LocalAppColors
import com.tpc.nudj.ui.theme.Orange
import com.tpc.nudj.ui.theme.Purple
import java.time.LocalDateTime
import java.time.Month
import java.time.Year
import java.util.Calendar
import java.util.Locale

@Composable
fun UserProfileScreen(
    viewModel: UserProfileScreenViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()
    val batch = uiState.value.batch
    val photoUrl = uiState.value.photoUrl
    val firstName = uiState.value.firstName
    val lastName = uiState.value.lastName
    val eventList = uiState.value.pastEventList
    val branch = uiState.value.branch
    val isLoading = uiState.value.isLoading
    UserProfileScreenLayout(
        batch = batch,
        photoUrl = photoUrl,
        firstName = firstName,
        lastName = lastName,
        eventList = eventList,
        onFirstNameChange = { newFirstName -> viewModel.onFirstNameChange(newFirstName) },
        onLastNameChange = { newLastName -> viewModel.onLastNameChange(newLastName) },
        onBatchChange = { newBatch -> viewModel.onBatchChange(newBatch) },
        uiState = uiState.value,
        viewModel = viewModel,
        userId = viewModel.uid,
        branch = branch,
        onBranchChange = { newBranch -> viewModel.onBranchChange(newBranch) },
    )
    if (isLoading) {
        LoadingScreenOverlay()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileScreenLayout(
    batch: Int,
    photoUrl: String,
    firstName: String,
    lastName: String,
    eventList: List<Event>,
    onFirstNameChange: (String) -> Unit,
    onBranchChange: (Branch) -> Unit,
    onLastNameChange: (String) -> Unit,
    onBatchChange: (String) -> Unit,
    uiState: UserProfileUiState,
    viewModel: UserProfileScreenViewModel,
    userId: String?,
    branch: Branch,
) {
    var feedbackEvent by remember { mutableStateOf<Event?>(null) }
    var isEditEnabled by remember {
        mutableStateOf(false)
    }
    var branchExpanded by remember { mutableStateOf(false) }
    val branchOptions = Branch.values().toList()
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val pickMediaLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                selectedImageUri = uri
                viewModel.onPhotoUriChanged(uri)
            }
        }
    )

    val currentYear = Year.now().value
    val currentMonth = LocalDateTime.now().month
    var batchExpanded by remember { mutableStateOf(false) }
    val batchOptions = if (currentMonth >= Month.AUGUST) {
        listOf(
            currentYear,
            currentYear - 1,
            currentYear - 2,
            currentYear - 3
        )
    } else {
        listOf(
            currentYear - 1,
            currentYear - 2,
            currentYear - 3,
            currentYear - 4
        )
    }
    val scrollState = rememberScrollState()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = LocalAppColors.current.background,
        topBar = {
            TopBar(
                onBackClicked = {},
            )
        }
    ) { contentPadding ->

        Column(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(20.dp))
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .border(
                        width = 2.dp,
                        color = LocalAppColors.current.appTitle,
                        shape = CircleShape
                    )
                    .then(
                        if (isEditEnabled) {
                            Modifier
                                .clickable(
                                    onClick = {
                                        pickMediaLauncher.launch(
                                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                        )
                                        viewModel.onPhotoUriChanged(selectedImageUri)
                                    }
                                )
                        } else {
                            Modifier
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = photoUrl,
                    contentDescription = "Profile picture",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(200.dp)
                        .clip(CircleShape)
                )
                if (isEditEnabled) {
                    Box(
                        modifier = Modifier
                            .size(200.dp)
                            .background(
                                Color.Black.copy(
                                    alpha = 0.3f
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.AddAPhoto,
                            contentDescription = "Add",
                            tint = Orange,
                            modifier = Modifier.size(50.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            Card(
                modifier = Modifier.fillMaxWidth(0.9f),
                border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.onBackground),
                colors = CardDefaults.cardColors(containerColor = LocalAppColors.current.appTitle)
            ) {
                AnimatedContent(
                    targetState = isEditEnabled
                ) { isEditable ->
                    if (!isEditable) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxWidth(0.75f)
                                ) {
                                    Text(
                                        text = "$firstName $lastName",
                                        fontFamily = ClashDisplay,
                                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                        color = LocalAppColors.current.background
                                    )
                                    Text(
                                        text = "Batch: $batch",
                                        fontFamily = ClashDisplay,
                                        color = if (isSystemInDarkTheme()) Color.White else Orange
                                    )
                                    Text(
                                        text = "Branch: ${branch.branchName}",
                                        fontFamily = ClashDisplay,
                                        color = if (isSystemInDarkTheme()) Color.White else Orange
                                    )
                                }
                                Row(
                                    modifier = Modifier.clickable(
                                        onClick = {
                                            isEditEnabled = !isEditEnabled
                                        }
                                    )
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Edit,
                                        contentDescription = "Edit",
                                        tint = Color.White
                                    )
                                    Spacer(Modifier.width(4.dp))
                                    Text(
                                        text = "Edit",
                                        color = Color.White,
                                        fontFamily = ClashDisplay
                                    )

                                }

                            }
                        }
                    } else {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(6.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Spacer(Modifier.height(13.dp))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(0.95f)
                            ) {
                                NudjTextField(
                                    value = firstName,
                                    placeholder = {
                                        Text(
                                            text = "First Name",
                                            fontFamily = ClashDisplay,
                                            color = Color.White
                                        )
                                    },
                                    color = EditTextBackgroundColorDark,
                                    onValueChange = onFirstNameChange,
                                    modifier = Modifier
                                        .fillMaxWidth(0.5f)
                                        .border(
                                            width = 1.8F.dp,
                                            color = Color.White,
                                            shape = RoundedCornerShape(16.dp)
                                        ),
                                    textColor = Color.White
                                )
                                Spacer(Modifier.width(7.dp))
                                NudjTextField(
                                    value = lastName,
                                    color = EditTextBackgroundColorDark,
                                    placeholder = {
                                        Text(
                                            text = "First Name",
                                            fontFamily = ClashDisplay,
                                            color = Color.White
                                        )
                                    },
                                    onValueChange = onLastNameChange,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .border(
                                            width = 1.8F.dp,
                                            color = Color.White,
                                            shape = RoundedCornerShape(16.dp)
                                        ),
                                    textColor = Color.White
                                )
                            }
                            Spacer(Modifier.height(15.dp))
                            ExposedDropdownMenuBox(
                                expanded = batchExpanded,
                                onExpandedChange = { batchExpanded = !batchExpanded }
                            ) {
                                NudjTextField(
                                    color = EditTextBackgroundColorDark,
                                    onValueChange = {},
                                    modifier = Modifier
                                        .fillMaxWidth(0.95f)
                                        .border(
                                            width = 1.8F.dp,
                                            color = Color.White,
                                            shape = RoundedCornerShape(16.dp)
                                        )
                                        .menuAnchor(),
                                    textColor = Color.White,
                                    value = batch.toString(),
                                    trailingIcon = {
                                        Icon(
                                            painter = painterResource(id = R.drawable.dropdownicon),
                                            contentDescription = "Dropdown Arrow",
                                            tint = Color(0xFFFF5E00),
                                            modifier = Modifier.size(42.dp)
                                        )
                                    },
                                    readOnly = true
                                )
                                ExposedDropdownMenu(
                                    expanded = batchExpanded,
                                    onDismissRequest = { batchExpanded = false }
                                ) {
                                    batchOptions.forEach { batchYear ->
                                        DropdownMenuItem(
                                            text = { Text(batchYear.toString()) },
                                            onClick = {
                                                onBatchChange(batchYear.toString())
                                                batchExpanded = false
                                            }
                                        )
                                    }
                                }
                            }
                            Spacer(Modifier.height(15.dp))
                            ExposedDropdownMenuBox(
                                expanded = branchExpanded,
                                onExpandedChange = { branchExpanded = !branchExpanded }
                            ) {
                                NudjTextField(
                                    modifier = Modifier
                                        .fillMaxWidth(0.95f)
                                        .border(
                                            width = 1.8F.dp,
                                            color = Color.White,
                                            shape = RoundedCornerShape(16.dp)
                                        )
                                        .menuAnchor(),
                                    textColor = Color.White,
                                    value = branch.name,
                                    onValueChange = {},
                                    trailingIcon = {
                                        Icon(
                                            painter = painterResource(id = R.drawable.dropdownicon),
                                            contentDescription = "Dropdown Arrow",
                                            tint = Color(0xFFFF5E00),
                                            modifier = Modifier.size(42.dp)
                                        )
                                    },
                                    readOnly = true
                                )
                                ExposedDropdownMenu(
                                    expanded = branchExpanded,
                                    onDismissRequest = { branchExpanded = false }
                                ) {
                                    branchOptions.forEach { branchOption ->
                                        DropdownMenuItem(
                                            text = { Text(branchOption.name) },
                                            onClick = {
                                                onBranchChange(branchOption)
                                                branchExpanded = false
                                            }
                                        )
                                    }
                                }
                            }
                            Spacer(Modifier.height(5.dp))
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                TertiaryButton(
                                    onClick = {
                                        isEditEnabled = !isEditEnabled
                                        viewModel.initialiseProfile()
                                    },
                                    text = "Cancel",
                                    contentColor = Color.White,
                                    isDarkModeEnabled = true
                                )
                                TertiaryButton(
                                    onClick = {
                                        viewModel.onClickSave()
                                        isEditEnabled = !isEditEnabled
                                    },
                                    text = "Save",
                                    contentColor = Color.White,
                                    isDarkModeEnabled = isSystemInDarkTheme()
                                )
                            }
                        }

                    }

                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Past Events",
                    fontFamily = ClashDisplay,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(Modifier.width(5.dp))
                HorizontalDivider(
                    Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            if (eventList.isNotEmpty()) {
                eventList.forEach { item ->
                    var isEnabled by remember { mutableStateOf(false) }

                    LaunchedEffect(key1 = item) {
                        isEnabled = viewModel.isRateItEnabled(item)
                    }
                    PastEventCard(
                        event = item,
                        onRateClick = {
                            feedbackEvent = item
                        },
                        isRateItEnabled = isEnabled
                    )
                }
            } else {
                Text(
                    text = "No past events!",
                    fontFamily = ClashDisplay,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
        feedbackEvent?.let { event ->
            FeedbackDialog(
                onFeedbackChange = { viewModel.onFeedbackChange(it) },
                clubId = event.clubId,
                userId = userId,
                onClickSubmit = { _, _ ->
                    viewModel.onClickSubmit(event.clubId, event.eventId)
                },
                onDismissRequest = {
                    feedbackEvent = null
                },
                feedbackText = uiState.feedback,
                onDismissFeedback = {
                    viewModel.onDismissFeedback()
                    feedbackEvent = null
                },
                onRatingChange =  viewModel::onRatingChange
            )
        }
    }
}

@Composable
fun PastEventCard(
    event: Event,
    onRateClick: () -> Unit,
    isRateItEnabled: Boolean = false
) {
    val date = event.eventDates[0].startDateTime.toDate()
    val calendar = Calendar.getInstance()
    calendar.time = date
    val year = calendar.get(Calendar.YEAR)
    val monthName = calendar.getDisplayName(
        Calendar.MONTH,
        Calendar.SHORT,
        Locale.getDefault()
    )
    val day = calendar.get(Calendar.DAY_OF_MONTH)


    Card(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(top = 10.dp),
        colors = CardDefaults.cardColors(containerColor = LocalAppColors.current.appTitle),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 10.dp),
        ) {
            Text(
                text = "$day $monthName $year",
                fontFamily = ClashDisplay,
                style = MaterialTheme.typography.titleMedium,
                color = if (isSystemInDarkTheme()) Purple else Orange,
                modifier = Modifier.padding(5.dp)
            )
            Text(
                text = event.eventName,
                fontFamily = ClashDisplay,
                style = MaterialTheme.typography.headlineSmall,
                color = if (isSystemInDarkTheme()) Purple else Orange,
                modifier = Modifier.padding(5.dp)

            )
        }
        PrimaryButton(
            text = if (isRateItEnabled) "Rate it!" else "Already rated!",
            isDarkModeEnabled = isSystemInDarkTheme(),
            onClick = onRateClick,
            enabled = isRateItEnabled,
            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
        )



        Spacer(Modifier.height(15.dp))
    }

}


@Composable
fun FeedbackDialog(
    onDismissRequest: () -> Unit,
    feedbackText: String,
    onFeedbackChange: (String) -> Unit,
    onRatingChange: (Int) -> Unit,
    onDismissFeedback: () -> Unit,
    clubId: String,
    userId: String?,
    onClickSubmit: (String, String) -> Unit

) {
    var rating by remember { mutableIntStateOf(0) }
    Dialog(
        onDismissRequest = {
            onDismissRequest()
            onDismissFeedback()
        },
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = CardBackgroundColor),
            border = BorderStroke(1.dp, color = LocalAppColors.current.appTitle)
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        repeat(5) { index ->
                            val starNumber = index + 1
                            IconButton(
                                onClick = { rating = starNumber }
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Star,
                                    contentDescription = "",
                                    tint = if (starNumber <= rating) LocalAppColors.current.appTitle else Color.DarkGray
                                )
                            }
                        }
                    }
                    Spacer(Modifier.height(5.dp))

                    if (rating > 0) {
                        Text(
                            text = "Any additional feedback would be highly appreciated!",
                            fontFamily = ClashDisplay,
                            fontSize = 15.sp,
                            textAlign = TextAlign.Center
                        )
                        OutlinedTextField(
                            value = feedbackText,
                            onValueChange = onFeedbackChange,
                            modifier = Modifier
                                .border(
                                    width = 1.8F.dp,
                                    color = LocalAppColors.current.editTextBorder,
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .background(
                                    LocalAppColors.current.editTextBackground,
                                    shape = RoundedCornerShape(24.dp)
                                )
                                .fillMaxWidth()
                                .height(60.dp),
                            singleLine = true,
                            textStyle = TextStyle(fontSize = 20.sp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.Transparent,
                                unfocusedBorderColor = Color.Transparent
                            )
                        )
                    }
                    Spacer(Modifier.height(5.dp))

                    PrimaryButton(
                        text = "Submit",
                        onClick = {
                            onRatingChange(rating)
                            userId?.let { onClickSubmit(clubId, it) }
                            onDismissFeedback()
                        },
                        isDarkModeEnabled = isSystemInDarkTheme(),
                        enabled = rating > 0
                    )

                }
            }
        }
    }
}
@Composable
fun TopBar(
    onBackClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
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
@Preview(showBackground = true)
fun CardPreview() {
    PastEventCard(
        event = Event(
            eventName = "Saaz night",
            eventDates = listOf(
                EventDate(
                    startDateTime = Timestamp.now(),
                    estimatedDuration = ""
                )
            )
        ),
        onRateClick = {}
    )
}