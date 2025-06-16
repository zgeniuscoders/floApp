package cd.zgeniuscoders.floapp.models

data class BankTransferMethod(
    val accountNumber: String,
    val bankName: String,
    val swiftCode: String,
    val transactionReference: String
)