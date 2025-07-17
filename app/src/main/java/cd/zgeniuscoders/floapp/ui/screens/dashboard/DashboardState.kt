package cd.zgeniuscoders.floapp.ui.screens.dashboard

import cd.zgeniuscoders.floapp.models.PendingPayment
import cd.zgeniuscoders.floapp.models.User

data class DashboardState(
    var isLoading: Boolean = false,
    var user: User? = null,
    var pendingPayments: List<PendingPayment> = emptyList(),
    var errorMessage: String = ""
)
