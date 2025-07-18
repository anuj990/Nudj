package com.tpc.nudj.ui.screens.auth.clubRegistration.clubRegistrationScreen1

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tpc.nudj.R
import com.tpc.nudj.model.enums.ClubCategory
import com.tpc.nudj.ui.components.EmailField
import com.tpc.nudj.ui.components.NudjTextField
import com.tpc.nudj.ui.screens.auth.clubRegistration.ClubRegistrationUIState
import com.tpc.nudj.ui.screens.auth.clubRegistration.clubRegistrationScreen2.ClubsRegisterScreen2
import com.tpc.nudj.ui.theme.ClashDisplay
import com.tpc.nudj.ui.theme.LocalAppColors
import com.tpc.nudj.ui.theme.NudjTheme
import com.tpc.nudj.ui.theme.Orange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClubsRegisterScreen1(
    uiState: ClubRegistrationUIState,
    onNameInput: (String) -> Unit,
    onEmailInput: (String) -> Unit,
    onCategoryInput: (ClubCategory) -> Unit,
    onDescriptionInput: (String) -> Unit,
    onAddAchievement: (String) -> Unit,
    onRemoveAchievement: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val categoryList = ClubCategory.entries.toList()
    var achievementInput by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val listState = rememberLazyListState()

    LaunchedEffect(uiState.clubAchievementsList.size) {
        if (uiState.clubAchievementsList.isNotEmpty()) {
            listState.animateScrollToItem(uiState.clubAchievementsList.size)
        }
    }
    LazyColumn(
        state = listState,
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
                    text = "Club Name",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontFamily = ClashDisplay,
                        color = Orange
                    ),
                )
                NudjTextField(
                    value = uiState.clubName,
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
            EmailField(
                email = uiState.clubEmail,
                onEmailChange = { onEmailInput(it) },
            )
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
                        value = uiState.clubCategory.categoryName,
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
                    value = uiState.clubDescription,
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
                uiState.clubAchievementsList.forEachIndexed { index, achievement ->
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

        }

    }
}


@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun Screen1Preview() {
    NudjTheme {
        ClubsRegisterScreen1(
            onAddAchievement = {},
            onRemoveAchievement = {},
            onNameInput = {},
            onEmailInput = {},
            onDescriptionInput = {},
            onCategoryInput = {},
            uiState = ClubRegistrationUIState()
        )
    }
}