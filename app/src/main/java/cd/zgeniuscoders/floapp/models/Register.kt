package cd.zgeniuscoders.floapp.models

data class Register(
    var uuid: String? = null,
    var email: String,
    var username: String,
    var password: String
)