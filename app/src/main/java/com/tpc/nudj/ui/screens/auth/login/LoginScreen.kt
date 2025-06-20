package com.tpc.nudj.ui.screens.auth.login

import com.tpc.nudj.R
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tpc.nudj.ui.components.EmailField
import com.tpc.nudj.ui.components.PasswordField
import com.tpc.nudj.ui.components.PrimaryButton
import com.tpc.nudj.ui.components.TertiaryButton
import com.tpc.nudj.ui.theme.ClashDisplay
import com.tpc.nudj.ui.theme.NudjTheme
import com.tpc.nudj.ui.theme.Purple
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.tpc.nudj.ui.screens.auth.login.LoginViewModel
import com.tpc.nudj.ui.theme.LocalAppColors
import kotlinx.coroutines.launch
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Scaffold
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onSignInComplete: ()->Unit
) {
    val context = LocalContext.current
    val uiState by viewModel.loginUiState.collectAsState()
    LoginScreenLayout(
        uiState = uiState,
        onEmailInput = { email -> viewModel.emailInput(email) },
        onPasswordInput = { password -> viewModel.passwordInput(password) },
        onForgetPasswordClicked = viewModel::onForgetPasswordClicked,
        onLoginClicked = viewModel::onLoginClicked,
        onGoogleClicked = {
            viewModel.onGoogleClicked(context, onSignInComplete)

        },
        clearToastMessage = viewModel::clearError
    )
}

@Composable
private fun LoginScreenLayout(
    uiState: LoginUiState,
    onEmailInput: (String) -> Unit,
    onPasswordInput: (String) -> Unit,
    onForgetPasswordClicked: () -> Unit,
    onLoginClicked: () -> Unit,
    onGoogleClicked: () -> Unit,
    clearToastMessage: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    LaunchedEffect(uiState.toastMessage) {
        if (uiState.toastMessage != null) {
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = uiState.toastMessage
                )
            }
            clearToastMessage()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = LocalAppColors.current.background
    ) { it->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(LocalAppColors.current.background),
            verticalArrangement = Arrangement.Center
        ) {
            EmailField(email = uiState.email, onEmailChange = onEmailInput)
            Spacer(modifier = Modifier.height(24.dp))
            PasswordField(password = uiState.password, onPasswordChange = onPasswordInput, label = "Password")

            TertiaryButton(
                text = "Forget Password?",
                onClick = onForgetPasswordClicked,
                modifier = Modifier.padding(start = 10.dp, top = 2.dp),
                isDarkModeEnabled = isSystemInDarkTheme()
            )
            Spacer(modifier = Modifier.height(30.dp))
            PrimaryButton(
                text = "Login",
                onClick = onLoginClicked,
                isDarkModeEnabled = false,
                modifier = Modifier
                    .align(
                        Alignment.CenterHorizontally
                    )
                    .width(130.dp),
                isLoading = uiState.isLoading
            )
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = "OR",
                color = LocalAppColors.current.editText,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontFamily = ClashDisplay
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(40.dp))
            IconButton(
                onClick = onGoogleClicked,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Icon(
                    painter = painterResource(R.drawable.google_icon),
                    contentDescription = "Google",
                    tint = if (isSystemInDarkTheme()) Color.White else Color.Unspecified,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LoginScreenPreview() {
    NudjTheme {
        LoginScreenLayout(
            uiState = LoginUiState(
                email = "abc@iiitdmj.ac.in",
                password = "password123",
            ),
            onEmailInput = {},
            onPasswordInput = {},
            onLoginClicked = {},
            onGoogleClicked = {},
            onForgetPasswordClicked = {},
            clearToastMessage = {}
        )
    }
}