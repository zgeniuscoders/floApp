package cd.zgeniuscoders.floapp.models

data class PendingPayment(
    val id: String = "",
    val title: String = "",
    val amount: Int = 0,
    val dueDate: String = "",

    val isPaid: Boolean = false,
    val isOverdue: Boolean = false
)
