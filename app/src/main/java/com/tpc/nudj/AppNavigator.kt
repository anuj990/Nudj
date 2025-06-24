package com.tpc.nudj

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigator(modifier: Modifier = Modifier, viewModel: UserDetailViewModel = viewModel()) {
    val navController: NavHostController = rememberNavController()

    NavHost(navController = navController, startDestination = "detailsInput") {
        composable("detailsInput") {
            DetailsInputScreen(viewModel = viewModel, navController = navController)
        }
        composable("detailsConfirmation") {
            DetailsConfirmationScreen(viewModel = viewModel)
        }
    }
}
