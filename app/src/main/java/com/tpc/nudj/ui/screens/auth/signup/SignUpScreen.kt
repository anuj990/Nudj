package com.tpc.nudj.ui.screens.auth.signup

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tpc.nudj.R
import com.tpc.nudj.ui.components.NudjTextField
import com.tpc.nudj.ui.components.PrimaryButton
import com.tpc.nudj.ui.theme.ClashDisplay
import com.tpc.nudj.ui.theme.LocalAppColors
import com.tpc.nudj.ui.theme.NudjTheme

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val uiState by viewModel.signUpUiState.collectAsState()

    SignUpScreenLayout(
        uiState = uiState,
        onEmailChange = { email ->
            viewModel.onEmailChange(email)
        },
        onPasswordChange = { password ->
            viewModel.onPasswordChange(password)
        },
        onConfirmPasswordChange = { confirmPassword ->
            viewModel.onConfirmPasswordChange(confirmPassword)
        },
        onSignUpClick = {
            // SignUp click logic
        },
        onGoogleSignUpClick = {
            // Google SignUp click logic
        }
    )
}


@Composable
fun SignUpScreenLayout(
    uiState: SignUpUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onSignUpClick: () -> Unit,
    onGoogleSignUpClick: () -> Unit
) {
    Scaffold(
        topBar = {},
        snackbarHost = {},
        modifier = Modifier
            .fillMaxSize(),
    ) { paddingValues ->
        Surface(modifier = Modifier.padding(paddingValues)) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .background(LocalAppColors.current.background)
            ) {

                Text(
                    text = "Nudj",
                    fontSize = 52.sp,
                    color = LocalAppColors.current.appTitle,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontFamily = ClashDisplay
                    ),
                    modifier = Modifier
                        .padding(top = 40.dp)

                )

                Spacer(modifier = Modifier.height(32.dp))

                NudjTextField(
                    label = "Email",
                    value = uiState.email,
                    onValueChange = onEmailChange
                )

                NudjTextField(
                    label = "Password",
                    value = uiState.password,
                    onValueChange = onPasswordChange
                )

                NudjTextField(
                    label = "Confirm Password",
                    value = uiState.confirmPassword,
                    onValueChange = onConfirmPasswordChange
                )

                PrimaryButton(
                    text = "Sign Up",
                    onClick = { /* Handle sign up */ },
                    modifier = Modifier,
                    enabled = uiState.email.isNotBlank() &&
                            uiState.confirmPassword == uiState.password,
                    isDarkModeEnabled = false
                )

                Text(
                    text = "OR",
                    modifier = Modifier
                        .padding(top = 8.dp, bottom = 16.dp)
                )

                IconButton(
                    onClick = {
                        // Google button onClick logic.
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.google_icon),
                        contentDescription = "Google SignUp",
                        modifier = Modifier
                            .size(96.dp)
                    )
                }
            }
        }
        // Design the layout.
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SignUpScreenPreview() {
    NudjTheme {
        SignUpScreenLayout(
            uiState = SignUpUiState(
                email = "abc@iiitdmj.ac.in",
                password = "password123",
                confirmPassword = "password123"
            ),
            onEmailChange = {},
            onPasswordChange = {},
            onConfirmPasswordChange = {},
            onSignUpClick = {},
            onGoogleSignUpClick = {}
        )
    }
}
