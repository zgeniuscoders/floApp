package cd.zgeniuscoders.floapp.ui.screens.payments

import cd.zgeniuscoders.floapp.models.PendingPayment
import cd.zgeniuscoders.floapp.models.User

data class PaymentState(
    var isLoading: Boolean = false,
    var pendingPayments: List<PendingPayment> = emptyList(),
    var errorMessage: String = ""
)
