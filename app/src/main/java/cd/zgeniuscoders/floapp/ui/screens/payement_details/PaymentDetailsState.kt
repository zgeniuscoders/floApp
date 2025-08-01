import cd.zgeniuscoders.floapp.models.PendingPayment

data class PaymentDetailsState(
    val isLoading: Boolean = false,
    val payment: PendingPayment? = null,
    val error: String = "",
    var selectedPaymentMethod: String = "mobile_money",
    var phoneNumber: String = "",
    var cardNumber: String = "",
    var isProcessing: Boolean = false
)