package cd.zgeniuscoders.floapp.ui.screens.dashboard

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import cd.zgeniuscoders.floapp.models.PendingPayment
import cd.zgeniuscoders.floapp.ui.navigation.Screen
import cd.zgeniuscoders.floapp.ui.screens.dashboard.components.PendingPaymentCard
import cd.zgeniuscoders.floapp.ui.screens.dashboard.components.QuickActionCard

@Composable
fun DashboardScreen(navController: NavController) {

    val vm = hiltViewModel<DashboardViewModel>()
    val state = vm.state
    val onEvent = vm::onTriggerEvent

    LaunchedEffect(true) {
        onEvent(DashboardEvent.OnLoadData)
    }

    DashboardBody(navController, state, onEvent)
}

@Composable
fun DashboardBody(
    navController: NavController,
    state: DashboardState,
    onEvent: (event: DashboardEvent) -> Unit
) {
    val pendingPayments = state.pendingPayments

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            // En-tête de bienvenue
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "Bonjour, ${state.user?.username}",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = "Étudiant en Informatique - L3",
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }

        item {
            Text(
                text = "Paiements en attente",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        items(pendingPayments) { payment ->
            PendingPaymentCard(
                payment = payment,
                onPayClick = {
                    navController.navigate(Screen.PaymentDetails(paymentId = payment.id))
                }
            )
        }

        item {
            // Actions rapides
            Text(
                text = "Actions rapides",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                QuickActionCard(
                    title = "Historique",
                    icon = Icons.Default.History,
                    onClick = { navController.navigate(Screen.History) },
                    modifier = Modifier.weight(1f)
                )
                QuickActionCard(
                    title = "Reçus",
                    icon = Icons.Default.Receipt,
                    onClick = { /* TODO */ },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun StatCard(
    title: String,
    value: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}




