package com.tpc.nudj.ui.screens.auth.login

import android.content.res.Configuration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.tpc.nudj.ui.theme.NudjTheme

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
){
    val uiState by viewModel.loginUiState.collectAsState()
    LoginScreenLayout(
        uiState = uiState,
        onLoginClick = { /* Handle login click */ },
        onForgotPasswordClick = { /* Handle forgot password click */ },
        onGoogleSignInClick = { /* Handle Google Sign-In click */ },
        onEmailChange = { email ->
            viewModel.onEmailChange(email)
        },
        onPasswordChange = {
            password ->
            viewModel.onPasswordChange(password)
        }
    )
}

@Composable
private fun LoginScreenLayout(uiState: LoginUiState,
                              onEmailChange: (String) -> Unit,
                              onPasswordChange: (String) -> Unit,
                              onLoginClick: () -> Unit,
                              onForgotPasswordClick: () -> Unit,
                              onGoogleSignInClick: () -> Unit){
    //Design the layout for the login screen here

}


@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LoginScreenPreview(){
    NudjTheme {
        LoginScreenLayout(
            uiState = LoginUiState(
                email = "abc@iiitdmj.ac.in",
                password = "password123",
            ),
            onEmailChange = {},
            onPasswordChange = {},
            onLoginClick = {},
            onForgotPasswordClick = {},
            onGoogleSignInClick = {}
        )
    }
}