package com.tpc.nudj.ui.screens.auth.detailsInput

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
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
import com.tpc.nudj.ui.components.NudjLogo
import com.tpc.nudj.ui.theme.LocalAppColors
import com.tpc.nudj.ui.theme.NudjTheme


@Composable
fun DetailsInputScreen(
    viewModel: UserDetailViewModel = hiltViewModel(),
    onNavigateToConfirmationScreen: () -> Unit
) {
    DetailsInputScreenLayout(
        onSaveClick = {
            onNavigateToConfirmationScreen()
        },
        onFirstNameChange = { viewModel.updateFirstName(it) },
        onLastNameChange = { viewModel.updateLastName(it) },
        onBranchChange = { viewModel.updateBranch(it) },
        onBatchChange = { viewModel.updateBatch(it) },
        firstName = viewModel.userDetailsUIState.collectAsState().value.firstName,
        lastName = viewModel.userDetailsUIState.collectAsState().value.lastName,
        branch = viewModel.userDetailsUIState.collectAsState().value.branch,
        batch = viewModel.userDetailsUIState.collectAsState().value.batch
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsInputScreenLayout(
    firstName: String = "",
    onFirstNameChange: (String) -> Unit = {},
    lastName: String = "",
    onLastNameChange: (String) -> Unit = {},
    branch: String = "",
    onBranchChange: (String) -> Unit = {},
    batch: String = "",
    onBatchChange: (String) -> Unit = {},
    onSaveClick: () -> Unit = {}
) {
    var branchExpanded by remember { mutableStateOf(false) }
    var batchExpanded by remember { mutableStateOf(false) }


    val branchOptions = listOf("CSE", "ECE", "ME", "SM", "DS")
    val batchOptions = listOf("2025", "2024", "2023", "2022")
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
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            NudjLogo()

            Spacer(modifier = Modifier.padding(top = 113.dp))

            Text(
                text = "Hold up! A few more details...",
                fontFamily = clashDisplayFont,
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp,
                lineHeight = 20.sp,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )

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
                    OutlinedTextField(
                        modifier = Modifier
                            .size(width = 192.dp, height = 62.dp)
                            .background(
                                color = (LocalAppColors.current.editTextBackground),
                                shape = RoundedCornerShape(16.dp)
                            )
                            .border(
                                2.dp,
                                color = MaterialTheme.colorScheme.onSurface,
                                shape = RoundedCornerShape(16.dp)
                            ),
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
                    OutlinedTextField(
                        modifier = Modifier
                            .size(width = 153.dp, height = 62.dp)
                            .background(
                                color = (LocalAppColors.current.editTextBackground),
                                shape = RoundedCornerShape(16.dp)
                            )
                            .border(
                                2.dp,
                                color = MaterialTheme.colorScheme.onSurface,
                                shape = RoundedCornerShape(16.dp)
                            ),
                        value = lastName,
                        onValueChange = onLastNameChange
                    )
                }
            }

            Row(
                modifier = Modifier
                    .padding(top = 44.dp)
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
                        OutlinedTextField(
                            modifier = Modifier
                                .size(width = 192.dp, height = 62.dp)
                                .menuAnchor()

                                .background(
                                    color = (LocalAppColors.current.editTextBackground),
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .border(
                                    2.dp,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    shape = RoundedCornerShape(16.dp)
                                ),
                            value = branch,
                            onValueChange = onBranchChange,
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
                            branchOptions.forEach {
                                DropdownMenuItem(
                                    text = { Text(it) },
                                    onClick = {
                                        onBranchChange(it)
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
                        OutlinedTextField(
                            modifier = Modifier
                                .size(width = 153.dp, height = 62.dp)
                                .menuAnchor()
                                .background(
                                    color = (LocalAppColors.current.editTextBackground),
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .border(
                                    2.dp,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    shape = RoundedCornerShape(16.dp)
                                ),
                            value = batch,
                            onValueChange = onBatchChange,
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
                            batchOptions.forEach {
                                DropdownMenuItem(
                                    text = { Text(it) },
                                    onClick = {
                                        onBatchChange(it)
                                        batchExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }


            Spacer(modifier = Modifier.height(82.dp))


            Button(
                onClick = {
                    onSaveClick()
                },
                modifier = Modifier
                    .width(142.dp)
                    .height(54.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5E00))
            ) {
                Text(
                    text = "Save",
                    fontSize = 20.sp,
                    fontFamily = clashDisplayFont,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailsInputScreePreview() {
    NudjTheme(darkTheme = false) {
        DetailsInputScreenLayout()
    }
}




