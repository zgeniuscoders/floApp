package cd.zgeniuscoders.floapp.ui.screens.profile

import cd.zgeniuscoders.floapp.models.PendingPayment
import cd.zgeniuscoders.floapp.models.User

data class ProfileState(
    var isLoading: Boolean = false,
    var user: User? = null,
    var errorMessage: String = ""
)
