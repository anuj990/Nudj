package com.tpc.nudj.ui.screens.auth.forgotPassword

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tpc.nudj.ui.components.EmailField
import com.tpc.nudj.ui.components.PrimaryButton
import com.tpc.nudj.ui.theme.NudjTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import com.tpc.nudj.ui.components.NudjLogo
import com.tpc.nudj.ui.theme.LocalAppColors
import kotlinx.coroutines.launch


@Composable
fun ForgetPasswordScreen(
    viewModel: ForgetPasswordScreenModel = hiltViewModel(),
    onNavigateToResetConfirmation: () -> Unit = {}
) {
    val uiState by viewModel.forgetPasswordUiState.collectAsState()
    ForgetPasswordScreenLayout(
        uiState = uiState,
        onEmailInput = viewModel::emailInput,
        onSendClicked = { viewModel.onSendEmailClicked(onNavigateToResetConfirmation) },
        onClearToastMessage = viewModel::clearError
    )
}


@Composable
fun ForgetPasswordScreenLayout(
    uiState: ForgetPasswordUiState,
    onEmailInput: (String) -> Unit,
    onSendClicked: () -> Unit,
    onClearToastMessage: () -> Unit
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
            onClearToastMessage()
        }
    }
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = LocalAppColors.current.background
    ) { it ->
        Box(modifier = Modifier.fillMaxSize()
            .padding(it)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(LocalAppColors.current.background),
                verticalArrangement = Arrangement.Center,
            ) {
                EmailField(email = uiState.email, onEmailChange = onEmailInput)
                Spacer(modifier = Modifier.height(40.dp))
                PrimaryButton(
                    text = "Send Email",
                    onClick = onSendClicked,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    isDarkModeEnabled = false,
                    isLoading = uiState.isLoading
                )
            }
            NudjLogo(modifier = Modifier.fillMaxWidth())
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ForgetPasswordScreenPreview() {
    NudjTheme {
        ForgetPasswordScreenLayout(
            uiState = ForgetPasswordUiState(
                email = "abc@iiitdmj.ac.in"
            ), onEmailInput = {}, onSendClicked = {}, onClearToastMessage = {})
    }
}