package com.tpc.nudj.ui.screens.auth.detailsInput

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.tpc.nudj.ui.components.PrimaryButton
import com.tpc.nudj.ui.components.SecondaryButton
import com.tpc.nudj.ui.navigation.Screens
import com.tpc.nudj.ui.theme.LocalAppColors
import com.tpc.nudj.ui.theme.NudjTheme


@Composable
fun DetailsConfirmationScreen(
    viewModel: UserDetailViewModel = hiltViewModel(),
    navArgs: Screens.UserDetailsConfirmationScreen,
    onEditClick: () -> Unit,
    onSaveSuccess: () -> Unit
) {
    val uiState = viewModel.userDetailsUIState.collectAsState().value

    // Load user details from navigation arguments when the screen is first created
    LaunchedEffect(Unit) {
        viewModel.loadUserDetailsFromArgs(
            firstName = navArgs.firstName,
            lastName = navArgs.lastName,
            branch = navArgs.branch,
            batch = navArgs.batch,
            gender = try { Gender.valueOf(navArgs.gender) } catch (e: Exception) { Gender.PREFER_NOT_TO_DISCLOSE }
        )
    }

    DetailsConfirmationScreenLayout(
        details = uiState,
        onEditClick = onEditClick,
        onSaveClick = {
            viewModel.saveUserDetails(onSuccess = onSaveSuccess)
        }
    )
}

@Composable
fun DetailsConfirmationScreenLayout(
    details: UserDetailsUIState,
    onEditClick: () -> Unit = {},
    onSaveClick: () -> Unit = {}
) {
    val clashDisplayFont = FontFamily(
        Font(R.font.clash_display_font, weight = FontWeight.Medium)
    )

    val scrollState = rememberScrollState()

    val isDark = isSystemInDarkTheme()
    val boxBackgroundColor = if (isDark) Color.White.copy(alpha = 0.9f) else LocalAppColors.current.editTextBackground
    val textColor = Color(0xFF3F1872)
    val labelColor = Color(0xFFFF5E00)

    Scaffold(
        containerColor = LocalAppColors.current.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NudjLogo()

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Review Your Information",
                fontFamily = clashDisplayFont,
                fontWeight = FontWeight.Medium,
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )

            if (details.errorMessage != null) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = details.errorMessage,
                    color = Color.Red,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
            }

            Box(
                modifier = Modifier
                    .padding(horizontal = 24.dp, vertical = 24.dp)
                    .fillMaxWidth()
                    .background(
                        color = boxBackgroundColor,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(20.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 28.dp)
                ) {
                    InfoField(label = "First Name", value = details.firstName, labelColor = labelColor, valueColor = textColor)

                    Spacer(modifier = Modifier.height(24.dp))

                    InfoField(label = "Last Name", value = details.lastName, labelColor = labelColor, valueColor = textColor)

                    Spacer(modifier = Modifier.height(24.dp))

                    InfoField(label = "Branch", value = details.branch.branchName, labelColor = labelColor, valueColor = textColor)

                    Spacer(modifier = Modifier.height(24.dp))

                    InfoField(label = "Batch", value = details.batch.toString(), labelColor = labelColor, valueColor = textColor)

                    Spacer(modifier = Modifier.height(24.dp))

                    InfoField(label = "Gender", value = details.gender.genderName, labelColor = labelColor, valueColor = textColor)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp)
            ) {
                SecondaryButton(
                    text = "Edit",
                    onClick = onEditClick,
                    isDarkModeEnabled = isSystemInDarkTheme(),
                    enabled = !details.isLoading,
                    modifier = Modifier.width(140.dp)
                )

                Spacer(modifier = Modifier.width(24.dp))

                PrimaryButton(
                    text = "Save",
                    onClick = onSaveClick,
                    isDarkModeEnabled = false,
                    enabled = !details.isLoading,
                    isLoading = details.isLoading,
                    modifier = Modifier.width(140.dp)
                )
            }
        }
    }
}

@Composable
fun InfoField(label: String, value: String, labelColor: Color, valueColor: Color) {
    val clashDisplayFont = FontFamily(
        Font(R.font.clash_display_font, weight = FontWeight.Medium)
    )

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            fontFamily = clashDisplayFont,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            color = labelColor,
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = value,
            fontFamily = clashDisplayFont,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = valueColor,
        )
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DetailsConfirmationScreenPreview() {
    NudjTheme(darkTheme = true) {
        DetailsConfirmationScreenLayout(
            details = UserDetailsUIState(
                firstName = "Anshu",
                lastName = "Kashyap",
                branch = Branch.ECE,
                batch = 2024,
                gender = Gender.MALE
            )
        )
    }
}
