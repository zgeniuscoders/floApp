package cd.zgeniuscoders.floapp.ui.screens.payments

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import cd.zgeniuscoders.floapp.ui.navigation.Screen
import cd.zgeniuscoders.floapp.ui.screens.payments.components.PaymentItemCard

@Composable
fun PaymentsScreen(navController: NavController) {
    val vm = hiltViewModel<PaymentViewModel>()
    val state = vm.state

    PaymentsBody(navController, state)
}

@Composable
fun PaymentsBody(navController: NavController, state: PaymentState) {

    val allPayments = state.pendingPayments

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        item {
            Text(
                text = "Tous les paiements",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        items(allPayments) { payment ->
            PaymentItemCard(
                payment = payment,
                onPayClick = { navController.navigate(Screen.PaymentDetails(paymentId = payment.id)) }
            )
        }
    }
}


