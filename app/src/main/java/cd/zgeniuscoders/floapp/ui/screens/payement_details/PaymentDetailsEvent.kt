package cd.zgeniuscoders.floapp.ui.screens.payement_details

sealed interface PaymentDetailsEvent {
    data class OnSelectedPaymentMethod(val selectedPaymentMethod: String) : PaymentDetailsEvent

    data object OnProcessCheckout:  PaymentDetailsEvent

}