package com.tpc.nudj.ui.screens.clubProfileScreen.editClubProfile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.tpc.nudj.R
import com.tpc.nudj.model.enums.ClubCategory
import com.tpc.nudj.ui.components.LoadingScreenOverlay
import com.tpc.nudj.ui.components.NudjTextField
import com.tpc.nudj.ui.components.TopBar
import com.tpc.nudj.ui.theme.ClashDisplay
import com.tpc.nudj.ui.theme.LocalAppColors
import com.tpc.nudj.ui.theme.NudjTheme
import com.tpc.nudj.ui.theme.Orange
import kotlinx.coroutines.launch

@Composable
fun EditCLubProfileScreen(
    viewModel: EditClubProfileViewModel = hiltViewModel(),
    onBackClicked: () -> Unit
) {
    val uiState by viewModel.profileUiState.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.fetchCurrentClub()
    }
    EditClubProfileLayout(
        uiState = uiState,
        onNameInput = viewModel::clubNameInput,
        onImagePicked = viewModel::clubLogoImageInput,
        onBackClicked = {
            onBackClicked()
        },
        onRemoveAchievement = viewModel::removeAchievement,
        onCategoryInput = viewModel::clubCategoryInput,
        onAddAchievement = viewModel::addAchievement,
        onDescriptionInput = viewModel::clubDescriptionInput,
        onAdditionalDetailsInput = viewModel::clubAdditionalDetailsInput,
        clearToastMessage = viewModel::clearToastMessage,
        removeTempClubLogo = viewModel::emptyTempClubLogo,
        onSaveClicked = {
            viewModel.onSaveClicked(
                toClubProfileScreen = {
                    onBackClicked()
                }
            )
        }
    )
    if (uiState.isLoading) {
        LoadingScreenOverlay()
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditClubProfileLayout(
    uiState: EditClubProfileUIState,
    onNameInput: (String) -> Unit,
    onCategoryInput: (ClubCategory) -> Unit,
    onDescriptionInput: (String) -> Unit,
    onAddAchievement: (String) -> Unit,
    onRemoveAchievement: (Int) -> Unit,
    clearToastMessage: () -> Unit,
    onBackClicked: () -> Unit,
    onImagePicked: (Uri?) -> Unit,
    onAdditionalDetailsInput: (String) -> Unit,
    onSaveClicked: () -> Unit,
    removeTempClubLogo: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val categoryList = ClubCategory.entries.toList()
    var achievementInput by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }


    LaunchedEffect(uiState.toastMessage) {
        if (uiState.toastMessage != null) {
            scope.launch {
                snackBarHostState.showSnackbar(
                    message = uiState.toastMessage
                )
            }
            clearToastMessage()
        }
    }

    var imageUri by remember { mutableStateOf<Uri?>(null) }

    LaunchedEffect(uiState.tempClubLogo) {
        imageUri = uiState.tempClubLogo?.toUri()
    }

    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            imageUri = uri
            onImagePicked(uri)
        }
    )
    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        topBar = {
            TopBar(
                onBackClicked = {
                    onBackClicked()
                },
                modifier = Modifier.padding(top = 20.dp)
            )
        },
        containerColor = LocalAppColors.current.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(LocalAppColors.current.background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.padding(10.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(LocalAppColors.current.background)
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 44.dp)
                    ) {
                        Text(
                            text = "Club Name",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontFamily = ClashDisplay,
                                color = Orange
                            ),
                        )
                        NudjTextField(
                            value = uiState.tempClubName,
                            onValueChange = { onNameInput(it) },
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
                            text = "Category",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontFamily = ClashDisplay,
                                color = Orange
                            ),
                        )
                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = {
                                expanded = !expanded
                            }
                        ) {
                            NudjTextField(
                                value = uiState.tempClubCategory.categoryName,
                                onValueChange = {},
                                singleLine = true,
                                readOnly = true,
                                trailingIcon = {
                                    Image(
                                        painter = painterResource(R.drawable.dropdownicon),
                                        contentDescription = null,
                                        modifier = Modifier.size(45.dp)
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor()
                                    .border(
                                        width = 1.8F.dp,
                                        LocalAppColors.current.editTextBorder,
                                        shape = RoundedCornerShape(16.dp)
                                    )
                            )
                            ExposedDropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false },
                                modifier = Modifier.background(LocalAppColors.current.editTextBackground)
                            ) {
                                categoryList.forEach { category ->
                                    DropdownMenuItem(
                                        text = {
                                            Text(
                                                text = category.categoryName,
                                                style = MaterialTheme.typography.titleMedium.copy(
                                                    fontFamily = ClashDisplay
                                                )
                                            )
                                        },
                                        onClick = {
                                            onCategoryInput(category)
                                            expanded = false
                                        },
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 44.dp)
                    ) {
                        Text(
                            text = "Description",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontFamily = ClashDisplay,
                                color = Orange
                            ),
                        )
                        NudjTextField(
                            value = uiState.tempDescription,
                            onValueChange = { onDescriptionInput(it) },
                            singleLine = false,
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
                            text = "Achievements",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontFamily = ClashDisplay,
                                color = Orange
                            ),
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            NudjTextField(
                                value = achievementInput,
                                onValueChange = { achievementInput = it },
                                singleLine = false,
                                modifier = Modifier
                                    .weight(1f)
                                    .border(
                                        width = 1.8F.dp,
                                        LocalAppColors.current.editTextBorder,
                                        shape = RoundedCornerShape(16.dp)
                                    )
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            IconButton(
                                modifier = Modifier.size(60.dp),
                                onClick = {
                                    onAddAchievement(achievementInput)
                                    achievementInput = ""
                                    focusManager.clearFocus()
                                },
                                colors = IconButtonDefaults.iconButtonColors(
                                    containerColor = Orange
                                )
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Add achievement",
                                    tint = Color.White,
                                    modifier = Modifier.size(40.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.padding(10.dp))
                        uiState.tempAchievementsList.forEachIndexed { index, achievement ->
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
                                            text = achievement,
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
                                            onClick = { onRemoveAchievement(index) },
                                            modifier = Modifier.size(36.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Delete,
                                                contentDescription = "Delete the achievement",
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
                    Spacer(modifier = Modifier.padding(10.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 44.dp)
                    ) {
                        Text(
                            text = "Club Logo",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontFamily = ClashDisplay,
                                color = Orange
                            ),
                        )
                        Card(
                            modifier = Modifier
                                .border(
                                    width = 1.8F.dp,
                                    LocalAppColors.current.editTextBorder,
                                    shape = RoundedCornerShape(16.dp)
                                ),
                            colors = CardDefaults.cardColors(
                                containerColor = LocalAppColors.current.editTextBackground
                            )
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                            ) {
                                if (imageUri == null) {
                                    Box(
                                        modifier = Modifier.fillMaxWidth(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Image(
                                            painter = if (isSystemInDarkTheme()) painterResource(R.drawable.frame3light) else painterResource(
                                                R.drawable.frame3
                                            ),
                                            contentDescription = null,
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(16.dp)
                                        )
                                        Column(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Text(
                                                text = "Club Logo",
                                                style = MaterialTheme.typography.titleLarge.copy(
                                                    fontFamily = ClashDisplay,
                                                    color = MaterialTheme.colorScheme.onBackground
                                                )
                                            )
                                            Spacer(modifier = Modifier.padding(20.dp))
                                            Button(
                                                modifier = Modifier,
                                                onClick = {
                                                    pickImageLauncher.launch(
                                                        PickVisualMediaRequest(
                                                            ActivityResultContracts.PickVisualMedia.ImageOnly
                                                        )
                                                    )
                                                },
                                                colors = ButtonDefaults.buttonColors(
                                                    containerColor = Orange
                                                ),
                                                shape = RoundedCornerShape(10.dp)
                                            ) {
                                                Text(
                                                    text = "Upload",
                                                    style = MaterialTheme.typography.titleMedium.copy(
                                                        fontFamily = ClashDisplay,
                                                        color = Color.White
                                                    ),
                                                    modifier = Modifier.padding(vertical = 2.dp)
                                                )
                                            }
                                        }
                                    }
                                } else {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(16.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        AsyncImage(
                                            model = ImageRequest.Builder(context = LocalContext.current)
                                                .data(imageUri)
                                                .crossfade(true)
                                                .build(),
                                            contentDescription = "club logo",
                                            contentScale = ContentScale.Crop
                                        )
                                    }
                                }
                                if (imageUri != null) {
                                    IconButton(
                                        onClick = {
                                            imageUri = null
                                            removeTempClubLogo()
                                        },
                                        colors = IconButtonDefaults.filledIconButtonColors(
                                            containerColor = Orange,
                                        ),
                                        modifier = Modifier.align(Alignment.TopEnd)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = "cancel the image selection",
                                            tint = Color.White,
                                            modifier = Modifier.size(23.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 44.dp)
                    ) {
                        Text(
                            text = "Additional Details",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontFamily = ClashDisplay,
                                color = Orange
                            ),
                        )
                        NudjTextField(
                            value = uiState.tempAdditionalDetails,
                            onValueChange = { onAdditionalDetailsInput(it) },
                            singleLine = false,
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(
                                    width = 1.8F.dp,
                                    LocalAppColors.current.editTextBorder,
                                    shape = RoundedCornerShape(16.dp)
                                )
                        )
                    }
                    Spacer(modifier = Modifier.padding(20.dp))
                    Button(
                        onClick = {
                            onSaveClicked()
                        },
                        modifier = Modifier.fillMaxWidth(0.7f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Orange
                        )
                    ) {
                        Text(
                            text = "Save",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontFamily = ClashDisplay,
                                color = Color.White
                            ),
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                }
            }
        }
    }
}

@Preview
@Composable
fun EditClubProfilePreview() {
    NudjTheme {
        EditClubProfileLayout(
            uiState = EditClubProfileUIState(),
            onBackClicked = {},
            onNameInput = {},
            onDescriptionInput = {},
            onCategoryInput = {},
            onAddAchievement = {},
            onRemoveAchievement = {},
            clearToastMessage = {},
            onImagePicked = {},
            onAdditionalDetailsInput = {},
            onSaveClicked = {},
            removeTempClubLogo = {}
        )
    }
}