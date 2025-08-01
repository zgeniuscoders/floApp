package cd.zgeniuscoders.floapp.models

data class History(
    val id: String = "",
    val name: String = "",
    val paymentMethod: String = "",
    val paymentNumber: String = "",
    val amount: Int = 0,
    val paidAt: String ="",
)
