package cd.zgeniuscoders.floapp.ui.navigation

sealed class Screen(val route: String, val title: String) {
    object Dashboard : Screen("dashboard", "Tableau de bord")
    object Payments : Screen("payments", "Paiements")
    object History : Screen("history", "Historique")
    object Profile : Screen("profile", "Profil")
    object PaymentDetails : Screen("payment_details", "Détails du paiement")
}
