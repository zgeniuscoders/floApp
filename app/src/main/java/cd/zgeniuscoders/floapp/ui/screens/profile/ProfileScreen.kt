package cd.zgeniuscoders.floapp.ui.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import cd.zgeniuscoders.floapp.ui.screens.profile.components.ProfileSection

@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    val vm = hiltViewModel<ProfileViewModel>()
    val state = vm.state

    ProfileBody(state)
}

@Composable
fun ProfileBody(state: ProfileState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Profil",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        // Informations personnelles
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = state.user?.username ?: "ZKoders",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = state.user?.email ?: "z.koders@example.com",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "Matricule: 2021INF001",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        // Informations académiques
        ProfileSection(
            title = "Informations académiques",
            items = listOf(
                ProfileItem("Filière", "Informatique"),
                ProfileItem("Niveau", "Licence 3"),
                ProfileItem("Année académique", "2023-2024"),
                ProfileItem("Statut", "Actif")
            )
        )

        // Paramètres de sécurité
        ProfileSection(
            title = "Sécurité",
            items = listOf(
                ProfileItem("Authentification 2FA", "Activée", Icons.Default.Security),
                ProfileItem("Notifications", "Activées", Icons.Default.Notifications),
                ProfileItem("Historique de connexion", "Voir", Icons.Default.History)
            )
        )

        Spacer(modifier = Modifier.weight(1f))

        // Bouton de déconnexion
        OutlinedButton(
            onClick = { /* TODO: Déconnexion */ },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.error
            )
        ) {
            Icon(Icons.Default.Logout, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Se déconnecter")
        }
    }
}


data class ProfileItem(
    val label: String,
    val value: String,
    val icon: ImageVector? = null
)
