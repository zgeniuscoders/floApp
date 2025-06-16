package cd.zgeniuscoders.floapp.models

data class Payment(
    val amount: Int,
    val date: String,
    val methodDetails: Map<String, String>,
    val paymentMethod: PaymentMethod,
    val paymentType: String,
    val status: String,
    val studentId: String,
    val transactionId: String
)