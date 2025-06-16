package cd.zgeniuscoders.floapp.remote

import cd.zgeniuscoders.floapp.models.Login
import cd.zgeniuscoders.floapp.models.Register
import cd.zgeniuscoders.floapp.utilis.Response
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class AuthenticationService {

    private var auth = Firebase.auth

    fun login(data: Login): Flow<Response<String>> = callbackFlow {
        auth.signInWithEmailAndPassword(data.email, data.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    trySend(Response.Success(data = auth.currentUser?.uid ?: ""))
                } else {
                    val errorMessage = when (val exception = task.exception) {
                        is FirebaseAuthInvalidUserException -> "No user found with this email."
                        is FirebaseAuthInvalidCredentialsException -> "Invalid password or email format."
                        is FirebaseAuthUserCollisionException -> "This user already exists."
                        else -> exception?.localizedMessage ?: "Unknown error occurred."
                    }

                    trySend(Response.Error(errorMessage))
                }
            }

        awaitClose()
    }

    fun signup(data: Register): Flow<Response<Register>> = callbackFlow {
        auth.createUserWithEmailAndPassword(data.email, data.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    trySend(Response.Success(data = data.copy(uuid = auth.currentUser?.uid)))
                } else {
                    val errorMessage = when (val exception = task.exception) {
                        is FirebaseAuthUserCollisionException -> "Email is already in use."
                        is FirebaseAuthWeakPasswordException -> "Password is too weak."
                        is FirebaseAuthInvalidCredentialsException -> "Invalid email format."
                        else -> exception?.localizedMessage ?: "Sign up failed."
                    }
                    trySend(Response.Error(errorMessage))
                }
            }

        awaitClose()
    }

    fun logout(): Flow<Response<Boolean>> = callbackFlow {
        try {
            auth.signOut()
            trySend(
                Response.Success(true)
            )
        } catch (e: Exception) {
            trySend(
                Response.Error(e.message.toString())
            )
        }
    }
}