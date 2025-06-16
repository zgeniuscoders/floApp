package cd.zgeniuscoders.floapp.models

data class MethodDetails(
    val authorizationCode: String,
    val cardType: String,
    val last4Digits: String,
    val transactionReference: String
)