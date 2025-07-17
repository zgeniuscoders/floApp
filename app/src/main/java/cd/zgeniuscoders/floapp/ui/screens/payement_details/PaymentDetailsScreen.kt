package cd.zgeniuscoders.floapp.ui.screens.payement_details

import PaymentDetailsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
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
import cd.zgeniuscoders.floapp.ui.screens.payement_details.components.PaymentMethodOption


@Composable
fun PaymentDetailsScreen(navController: NavController, paymentId: String) {
    val vm = hiltViewModel<PaymentDetailsViewModel>()
    val state by vm.state.collectAsState()
    val onEvent = vm::onEvent

    LaunchedEffect(true) {
        vm.getPaymentDetails(paymentId)
    }

    PaymentDetailsBody(state, navController, onEvent)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentDetailsBody(
    state: PaymentDetailsState,
    navController: NavController,
    onEvent: (PaymentDetailsEvent) -> Unit
) {
    val selectedPaymentMethod = state.selectedPaymentMethod
    var phoneNumber = state.phoneNumber
    var isProcessing = state.isProcessing
    val payment = state.payment

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // En-tête avec bouton retour
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Retour")
            }
            Text(
                text = "Détails du paiement ${payment?.title}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // Détails du paiement
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Text(
                    text = "${payment?.title}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "${payment?.amount} $",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "${payment?.dueDate}",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        if (payment?.paid != null && !payment.paid) {
            // Méthodes de paiement
            Text(
                text = "Méthode de paiement",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )

            PaymentMethodOption(
                title = "Mobile Money",
                subtitle = "Orange Money",
                icon = Icons.Default.PhoneAndroid,
                selected = selectedPaymentMethod == "mobile_money",
                onSelect = { onEvent(PaymentDetailsEvent.OnSelectedPaymentMethod("mobile_money")) }
            )

            PaymentMethodOption(
                title = "Carte bancaire",
                subtitle = "Visa, Mastercard",
                icon = Icons.Default.CreditCard,
                selected = selectedPaymentMethod == "card",
                onSelect = { onEvent(PaymentDetailsEvent.OnSelectedPaymentMethod("card")) }
            )

            // Champ numéro de téléphone (si Mobile Money sélectionné)
            if (selectedPaymentMethod == "mobile_money") {
                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    label = { Text("Numéro de téléphone") },
                    leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Ex: +225 07 XX XX XX XX") }
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Informations de sécurité
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Security,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Transaction sécurisée et traçable",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }

            // Bouton de paiement
            Button(
                onClick = {
                    onEvent(PaymentDetailsEvent.OnProcessCheckout)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = !isProcessing &&
                        (selectedPaymentMethod != "mobile_money" || phoneNumber.isNotEmpty())
            ) {
                if (isProcessing) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Traitement en cours...")
                } else {
                    Icon(Icons.Default.Payment, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Payer ${payment.amount} $", fontSize = 16.sp)
                }
            }
        }

    }
}


