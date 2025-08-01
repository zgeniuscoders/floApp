package cd.zgeniuscoders.floapp.ui.screens.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import cd.zgeniuscoders.floapp.remote.AuthenticationService


@Composable
fun LoginScreen(onLoginSuccess: () -> Unit, snackbarHostState: SnackbarHostState) {

    val vm = hiltViewModel<LoginViewModel>()
    val state = vm.state

    LaunchedEffect(state.isLogged) {
        if (state.isLogged) {
            onLoginSuccess()
        }
    }

    LaunchedEffect(state.errorMessages) {
        if(state.errorMessages.isNotBlank()){
           snackbarHostState.showSnackbar(message = state.errorMessages)
        }
    }

    LoginPage(
        vm::onTriggerEvent, vm.state, snackbarHostState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPage(
    onEvent: (event: LoginEvent) -> Unit, state: LoginState, snackbarHostState: SnackbarHostState
) {

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }) { innerP ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = innerP.calculateTopPadding())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo et titre
            Icon(
                imageVector = Icons.Default.School,
                contentDescription = "Logo",
                modifier = Modifier.size(80.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Paiements Académiques",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = "Système sécurisé de traçabilité",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Champ email
            OutlinedTextField(
                value = state.email,
                onValueChange = { onEvent(LoginEvent.OnEmailChange(it)) },
                label = { Text("Email étudiant") },
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Champ mot de passe
            OutlinedTextField(
                value = state.password,
                onValueChange = { onEvent(LoginEvent.OnPasswordChange(it)) },
                label = { Text("Mot de passe") },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                trailingIcon = {
                    IconButton(onClick = { onEvent(LoginEvent.OnTogglePassword) }) {
                        Icon(
                            imageVector = if (state.isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = if (state.isPasswordVisible) "Masquer" else "Afficher"
                        )
                    }
                },
                visualTransformation = if (state.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Bouton de connexion
            Button(
                onClick = {
                    onEvent(LoginEvent.OnLogin)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = state.email.isNotEmpty() && state.password.isNotEmpty() && !state.isLoading
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp), color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text("Se connecter", fontSize = 16.sp)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            TextButton(onClick = { /* TODO: Mot de passe oublié */ }) {
                Text("Mot de passe oublié ?")
            }
        }
    }
}
