package com.tpc.nudj.ui.screens.auth.detailsInput

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tpc.nudj.R
import com.tpc.nudj.model.enums.Branch
import com.tpc.nudj.model.enums.Gender
import com.tpc.nudj.ui.components.NudjLogo
import com.tpc.nudj.ui.components.NudjTextField
import com.tpc.nudj.ui.components.PrimaryButton
import com.tpc.nudj.ui.components.TertiaryButton
import com.tpc.nudj.ui.navigation.Screens
import com.tpc.nudj.ui.theme.LocalAppColors
import com.tpc.nudj.ui.theme.NudjTheme
import java.time.LocalDateTime
import java.time.Month
import java.time.Year

@Composable
fun DetailsInputScreen(
    viewModel: UserDetailViewModel = hiltViewModel(),
    onNavigateToConfirmationScreen: (Screens.UserDetailsConfirmationScreen) -> Unit,
    onNavigateToClubRegistration: () -> Unit = {}
) {
    val uiState = viewModel.userDetailsUIState.collectAsState().value

    DetailsInputScreenLayout(
        firstName = uiState.firstName,
        lastName = uiState.lastName,
        branch = uiState.branch,
        batch = uiState.batch,
        gender = uiState.gender,
        errorMessage = uiState.errorMessage,
        onFirstNameChange = { viewModel.updateFirstName(it) },
        onLastNameChange = { viewModel.updateLastName(it) },
        onBranchChange = { viewModel.updateBranch(it) },
        onBatchChange = { viewModel.updateBatch(it) },
        onGenderChange = { viewModel.updateGender(it) },
        onSaveClick = {
            if (viewModel.validateInputs()) {
                val confirmationScreen = Screens.UserDetailsConfirmationScreen(
                    firstName = uiState.firstName,
                    lastName = uiState.lastName,
                    branch = uiState.branch,
                    batch = uiState.batch,
                    gender = uiState.gender.name
                )
                onNavigateToConfirmationScreen(confirmationScreen)
            }
        },
        onClubRegistrationClick = onNavigateToClubRegistration
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsInputScreenLayout(
    firstName: String = "",
    lastName: String = "",
    branch: Branch = Branch.CSE,
    batch: Int = if (LocalDateTime.now().month >= Month.AUGUST) LocalDateTime.now().year else LocalDateTime.now().year - 1,
    gender: Gender = Gender.PREFER_NOT_TO_DISCLOSE,
    errorMessage: String? = null,
    onFirstNameChange: (String) -> Unit = {},
    onLastNameChange: (String) -> Unit = {},
    onBranchChange: (Branch) -> Unit = {},
    onBatchChange: (Int) -> Unit = {},
    onGenderChange: (Gender) -> Unit = {},
    onSaveClick: () -> Unit = {},
    onClubRegistrationClick: () -> Unit = {}
) {
    var branchExpanded by remember { mutableStateOf(false) }
    var batchExpanded by remember { mutableStateOf(false) }
    var genderExpanded by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    val branchOptions = Branch.values().toList()

    val currentYear = Year.now().value
    val currentMonth = LocalDateTime.now().month
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

    val genderOptions = Gender.values().toList()

    val clashDisplayFont = FontFamily(
        Font(R.font.clash_display_font, weight = FontWeight.Medium)
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = LocalAppColors.current.background,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            NudjLogo()

            Spacer(modifier = Modifier.padding(top = 60.dp))

            Text(
                text = "Hold up! A few more details...",
                fontFamily = clashDisplayFont,
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp,
                lineHeight = 20.sp,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )

            if (errorMessage != null) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
            }

            // First name and last name row
            Row(
                modifier = Modifier
                    .padding(top = 42.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Column(modifier = Modifier.padding(start = 10.dp)) {
                    Text(
                        text = "First Name",
                        fontFamily = clashDisplayFont,
                        fontWeight = FontWeight.Medium,
                        fontSize = 20.sp,
                        color = Color(0xFFFF5E00)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    NudjTextField(
                        modifier = Modifier
                            .size(width = 192.dp, height = 62.dp),
                        value = firstName,
                        onValueChange = onFirstNameChange
                    )
                }

                Column(modifier = Modifier.padding(start = 6.dp)) {
                    Text(
                        text = "Last Name",
                        fontFamily = clashDisplayFont,
                        fontWeight = FontWeight.Medium,
                        fontSize = 20.sp,
                        color = Color(0xFFFF5E00)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    NudjTextField(
                        modifier = Modifier
                            .size(width = 153.dp, height = 62.dp),
                        value = lastName,
                        onValueChange = onLastNameChange
                    )
                }
            }

            Row(
                modifier = Modifier
                    .padding(top = 24.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Column(modifier = Modifier.padding(start = 10.dp)) {
                    Text(
                        text = "Branch",
                        fontFamily = clashDisplayFont,
                        fontWeight = FontWeight.Medium,
                        fontSize = 20.sp,
                        color = Color(0xFFFF5E00)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    ExposedDropdownMenuBox(
                        expanded = branchExpanded,
                        onExpandedChange = { branchExpanded = !branchExpanded }
                    ) {
                        NudjTextField(
                            modifier = Modifier
                                .size(width = 192.dp, height = 62.dp)
                                .menuAnchor(),
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
                }

                Column(modifier = Modifier.padding(start = 6.dp)) {
                    Text(
                        text = "Batch",
                        fontFamily = clashDisplayFont,
                        fontWeight = FontWeight.Medium,
                        fontSize = 20.sp,
                        color = Color(0xFFFF5E00)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    ExposedDropdownMenuBox(
                        expanded = batchExpanded,
                        onExpandedChange = { batchExpanded = !batchExpanded }
                    ) {
                        NudjTextField(
                            modifier = Modifier
                                .size(width = 153.dp, height = 62.dp)
                                .menuAnchor(),
                            value = batch.toString(),
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
                            expanded = batchExpanded,
                            onDismissRequest = { batchExpanded = false }
                        ) {
                            batchOptions.forEach { batchYear ->
                                DropdownMenuItem(
                                    text = { Text(batchYear.toString()) },
                                    onClick = {
                                        onBatchChange(batchYear)
                                        batchExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }

            // Gender row
            Row(
                modifier = Modifier
                    .padding(top = 24.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Column(modifier = Modifier.padding(start = 10.dp)) {
                    Text(
                        text = "Gender",
                        fontFamily = clashDisplayFont,
                        fontWeight = FontWeight.Medium,
                        fontSize = 20.sp,
                        color = Color(0xFFFF5E00)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    ExposedDropdownMenuBox(
                        expanded = genderExpanded,
                        onExpandedChange = { genderExpanded = !genderExpanded }
                    ) {
                        NudjTextField(
                            modifier = Modifier
                                .size(width = 330.dp, height = 62.dp)
                                .menuAnchor(),
                            value = gender.genderName,
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
                            expanded = genderExpanded,
                            onDismissRequest = { genderExpanded = false }
                        ) {
                            genderOptions.forEach { genderOption ->
                                DropdownMenuItem(
                                    text = { Text(genderOption.genderName) },
                                    onClick = {
                                        onGenderChange(genderOption)
                                        genderExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            PrimaryButton(
                text = "Save",
                onClick = onSaveClick,
                isDarkModeEnabled = false,
                modifier = Modifier.width(160.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            TertiaryButton(
                text = "Register as Club?",
                onClick = onClubRegistrationClick,
                isDarkModeEnabled = isSystemInDarkTheme()
            )

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailsInputScreenPreview() {
    NudjTheme(darkTheme = false) {
        DetailsInputScreenLayout()
    }
}
