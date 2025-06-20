package com.tpc.nudj

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.tpc.nudj.ui.TestScreen
import com.tpc.nudj.ui.TestScreen2
import com.tpc.nudj.ui.navigation.Screens
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
                        entry<Screens.LoginScreen>{
                            LoginScreen(
                                onSignInComplete = {
                                    backstack.add(Screens.DashboardScreen)
                                }
                            )
                        }
                        entry<Screens.DashboardScreen> {
                            DashboardScreen()
                        }
                    }
                )
            }
        }
    }
}
