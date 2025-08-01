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
import androidx.navigation.toRoute
import cd.zgeniuscoders.floapp.ui.navigation.BottomNavigationBar
import cd.zgeniuscoders.floapp.ui.navigation.Screen
import cd.zgeniuscoders.floapp.ui.navigation.fromRoute
import cd.zgeniuscoders.floapp.ui.screens.dashboard.DashboardScreen
import cd.zgeniuscoders.floapp.ui.screens.history.HistoryScreen
import cd.zgeniuscoders.floapp.ui.screens.login.LoginScreen
import cd.zgeniuscoders.floapp.ui.screens.payement_details.PaymentDetailsScreen
import cd.zgeniuscoders.floapp.ui.screens.payments.PaymentsScreen
import cd.zgeniuscoders.floapp.ui.screens.profile.ProfileScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AcademicPaymentApp() {
    val navController = rememberNavController()
    var isLoggedIn by remember { mutableStateOf(false) }
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
    val currentRoute = navBackStackEntry?.fromRoute()

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
            startDestination = Screen.Dashboard,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<Screen.Dashboard>() {
                DashboardScreen(navController)
            }
            composable<Screen.Payments>() {
                PaymentsScreen(navController)
            }
            composable<Screen.History>() {
                HistoryScreen()
            }
            composable<Screen.Profile>() {
                ProfileScreen()
            }
            composable<Screen.PaymentDetails> {
                val paymentId = it.toRoute<Screen.PaymentDetails>().paymentId
                PaymentDetailsScreen(navController, paymentId)
            }
        }
    }
}
