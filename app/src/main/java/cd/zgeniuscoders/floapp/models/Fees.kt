package cd.zgeniuscoders.floapp.models

data class Fees(
    val academicYear: String,
    val amount: Int,
    val appliesTo: List<String>,
    val createdAt: String,
    val description: String,
    val dueDate: String,
    val title: String,
    val type: String
)
