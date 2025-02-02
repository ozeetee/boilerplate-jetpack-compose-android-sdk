package io.atomic.sdk

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.atomic.sdk.screens.dummy.DummyRoute
import io.atomic.sdk.screens.inbox.InboxRoute

@Composable
fun BoilerPlateNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "inbox") {
        composable("inbox") { InboxRoute(navController) }
        composable("dummy") { DummyRoute(navController) }
    }
}