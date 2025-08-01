package cd.zgeniuscoders.floapp.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    currentRoute: Screen?
) {
    val items = listOf(
        BottomNavItem(Screen.Dashboard, "Accueil", Icons.Default.Home),
        BottomNavItem(Screen.Payments, "Paiements", Icons.Default.Payment),
        BottomNavItem(Screen.History, "Historique", Icons.Default.History),
        BottomNavItem(Screen.Profile, "Profil", Icons.Default.Person)
    )
    
    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title) },
                selected = currentRoute == item,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

data class BottomNavItem(
    val route: Screen,
    val title: String,
    val icon: ImageVector
)
