package cd.zgeniuscoders.floapp.ui.navigation

import androidx.navigation.NavBackStackEntry

fun NavBackStackEntry?.fromRoute(): Screen {
    this?.destination?.route?.substringBefore("?")?.substringBefore("/")
        ?.substringAfterLast(".")?.let {
            return when (it) {
                Screen.Dashboard::class.simpleName -> Screen.Dashboard
                Screen.PaymentDetails::class.java.simpleName -> Screen.PaymentDetails("")
                Screen.History::class.java.simpleName -> Screen.History
                Screen.Profile::class.java.simpleName -> Screen.Profile
                Screen.Payments::class.java.simpleName -> Screen.Payments
                else -> Screen.Dashboard
            }
        }
    return Screen.Dashboard
}