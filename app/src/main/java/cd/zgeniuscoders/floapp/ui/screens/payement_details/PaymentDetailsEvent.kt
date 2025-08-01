package cd.zgeniuscoders.floapp.ui.screens.payement_details

sealed interface PaymentDetailsEvent {
    data class OnSelectedPaymentMethod(val selectedPaymentMethod: String) : PaymentDetailsEvent

    data class OnPhoneNumberChange(val phone: String) : PaymentDetailsEvent

    data class OnCardNumberChange(val card: String) : PaymentDetailsEvent

    data object OnProcessCheckout:  PaymentDetailsEvent

}