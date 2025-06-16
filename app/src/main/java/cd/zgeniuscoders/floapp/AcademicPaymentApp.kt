package cd.zgeniuscoders.floapp

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import cd.zgeniuscoders.floapp.ui.navigation.BottomNavigationBar
import cd.zgeniuscoders.floapp.ui.navigation.Screen
import cd.zgeniuscoders.floapp.ui.screens.dashboard.DashboardScreen
import cd.zgeniuscoders.floapp.ui.screens.HistoryScreen
import cd.zgeniuscoders.floapp.ui.screens.login.LoginScreen
import cd.zgeniuscoders.floapp.ui.screens.PaymentDetailsScreen
import cd.zgeniuscoders.floapp.ui.screens.PaymentsScreen
import cd.zgeniuscoders.floapp.ui.screens.ProfileScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AcademicPaymentApp() {
    val navController = rememberNavController()
    var isLoggedIn by remember { mutableStateOf(true) }
    val snackbarHostState = remember { SnackbarHostState() }

    if (!isLoggedIn) {
        LoginScreen(onLoginSuccess = { isLoggedIn = true }, snackbarHostState)
    } else {
        MainAppContent(navController = navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppContent(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                currentRoute = currentRoute
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Dashboard.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Dashboard.route) {
                DashboardScreen(navController)
            }
            composable(Screen.Payments.route) {
                PaymentsScreen(navController)
            }
            composable(Screen.History.route) {
                HistoryScreen()
            }
            composable(Screen.Profile.route) {
                ProfileScreen()
            }
            composable(Screen.PaymentDetails.route) {
                PaymentDetailsScreen(navController)
            }
        }
    }
}
