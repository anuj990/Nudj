package com.tpc.nudj.ui.screens.auth.emailVerification

import android.content.Intent
import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.google.firebase.auth.FirebaseAuth
import com.tpc.nudj.ui.theme.ClashDisplay
import com.tpc.nudj.ui.theme.NudjTheme
import com.tpc.nudj.ui.theme.Purple
import com.tpc.nudj.R
import com.tpc.nudj.ui.components.PrimaryButton
import com.tpc.nudj.ui.components.SecondaryButton
import com.tpc.nudj.ui.components.TertiaryButton
import com.tpc.nudj.ui.theme.LocalAppColors
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun EmailVerificationScreen(
    viewModel: EmailVerificationViewModel = hiltViewModel(),
    goToLoginScreen : () -> Unit
) {
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Show snackbar for messages
    LaunchedEffect(uiState.message) {
        uiState.message?.let {
            scope.launch {
                snackbarHostState.showSnackbar(it)
                viewModel.toastMessageShown()
            }
        }
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                Log.i("EmailVerificationScreen", "Checking user verification status")
                viewModel.checkCurrentUserVerificationStatus(goToLoginScreen)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }


    LaunchedEffect(Unit) {
        viewModel.sendVerificationEmail()
        while (true) {
            delay(1000)
            val user = FirebaseAuth.getInstance().currentUser
            user?.reload()
            if (user?.isEmailVerified == true) {
                goToLoginScreen()
                break
            }
        }
    }


    EmailVerificationScreenLayout(
        isLoading = uiState.isLoading,
        canResend = uiState.canResend,
        countdown = uiState.countdown,
        onResendEmail = {
            viewModel.sendVerificationEmail()
        },
        onCheckVerification = {
            viewModel.checkCurrentUserVerificationStatus(goToLoginScreen)
        },
        snackbarHostState = snackbarHostState
    )
}

@Composable
fun EmailVerificationScreenLayout(
    isLoading: Boolean = false,
    canResend: Boolean = false,
    countdown: Int = 0,
    onResendEmail: () -> Unit,
    onCheckVerification: () -> Unit,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {
    val context = LocalContext.current
    val isDarkMode = isSystemInDarkTheme()

    Scaffold(
        containerColor = LocalAppColors.current.background,
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) {paddingValues->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Verification Email Sent!",
                color = if (isDarkMode) Color.White else Purple,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontFamily = ClashDisplay
                ),
            )

            Text(
                text = "Please check your inbox and verify your email",
                color = LocalAppColors.current.editText,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(35.dp))

            Icon(
                painter = painterResource(R.drawable.hot_air_balloon__travel_transportation_hot_air_balloon),
                contentDescription = "Sent Successfully",
                tint = LocalAppColors.current.editText,
                modifier = Modifier
                    .aspectRatio(1.4f)
            )

            Spacer(modifier = Modifier.height(40.dp))

            PrimaryButton(
                text = "Check Inbox",
                onClick = {
                    val intent = Intent(Intent.ACTION_MAIN).apply {
                        addCategory(Intent.CATEGORY_APP_EMAIL)
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                    context.startActivity(intent)
                },
                modifier = Modifier.fillMaxWidth(0.8f),
                isDarkModeEnabled = false
            )

            Spacer(modifier = Modifier.height(16.dp))

            SecondaryButton(
                text = "Verify Status",
                onClick = onCheckVerification,
                modifier = Modifier.fillMaxWidth(0.8f),
                isDarkModeEnabled = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            if (isLoading) {
                CircularProgressIndicator(color = if (isDarkMode) Color.White else Purple)
            } else {
                if (canResend) {
                    TertiaryButton(
                        text = "Resend Email",
                        onClick = onResendEmail,
                        isDarkModeEnabled = isDarkMode
                    )
                } else {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Resend in $countdown seconds",
                            color = LocalAppColors.current.editText.copy(alpha = 0.6f),
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun EmailVerificationScreenPreview() {
    NudjTheme {
        EmailVerificationScreenLayout(
            isLoading = false,
            canResend = false,
            countdown = 15,
            onResendEmail = {},
            onCheckVerification = {}
        )
    }
}