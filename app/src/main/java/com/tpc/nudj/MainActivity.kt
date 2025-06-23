package com.tpc.nudj

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.tpc.nudj.ui.navigation.Screens
import com.tpc.nudj.ui.screens.auth.detailsInput.DetailsInputScreen
import com.tpc.nudj.ui.screens.auth.forgotPassword.ForgetPasswordScreen
import com.tpc.nudj.ui.screens.auth.forgotPassword.ResetLinkConfirmationScreen
import com.tpc.nudj.ui.screens.auth.login.LoginScreen
import com.tpc.nudj.ui.screens.dashboard.DashboardScreen
import com.tpc.nudj.ui.theme.NudjTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NudjTheme {
                var backstack = rememberNavBackStack(Screens.LoginScreen)

                NavDisplay(
                    backStack = backstack,
                    modifier = Modifier.fillMaxSize(),
                    entryProvider = entryProvider {
                        entry<Screens.LoginScreen> {
                            LoginScreen(
                                onSignInComplete = {
                                    backstack.add(Screens.DashboardScreen)
                                },
                                onNavigateToForgotPassword = {
                                    backstack.add(Screens.ForgotPasswordScreen)
                                },
                                onNavigateToDetailsScreen = {
                                    backstack.add(Screens.DetailsScreen)
                                }
                            )
                        }
                        entry<Screens.ForgotPasswordScreen> {
                            ForgetPasswordScreen(
                                onNavigateToResetConfirmation = {
                                    backstack.add(Screens.ResetConfirmationScreen)
                                }
                            )
                        }
                        entry<Screens.ResetConfirmationScreen> {
                            ResetLinkConfirmationScreen()
                        }
                        entry<Screens.DashboardScreen> {
                            DashboardScreen()
                        }
                        entry<Screens.DetailsScreen> {
                            DetailsInputScreen()
                        }
                    }
                )
            }
        }
    }
}
