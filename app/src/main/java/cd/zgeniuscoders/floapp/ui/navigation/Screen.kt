package cd.zgeniuscoders.floapp.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen {
    @Serializable
    object Dashboard : Screen

    @Serializable
    object Payments : Screen

    @Serializable
    object History : Screen

    @Serializable
    object Profile : Screen

    @Serializable
    class PaymentDetails(val paymentId: String) : Screen
}
