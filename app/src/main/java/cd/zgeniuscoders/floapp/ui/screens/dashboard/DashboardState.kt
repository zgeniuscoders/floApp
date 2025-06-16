package cd.zgeniuscoders.floapp.ui.screens.dashboard

import cd.zgeniuscoders.floapp.models.User

data class DashboardState(
    var isLoading: Boolean = false,
    var user: User? = null,
    var errorMessage: String = ""
)
