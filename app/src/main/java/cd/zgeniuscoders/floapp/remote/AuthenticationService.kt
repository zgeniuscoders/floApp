package cd.zgeniuscoders.floapp.remote

import cd.zgeniuscoders.floapp.models.Login
import cd.zgeniuscoders.floapp.utilis.Response
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class AuthenticationService {

    private var auth = Firebase.auth

    fun login(data: Login): Flow<Response<String>> = callbackFlow {
        try {
            auth.signInWithEmailAndPassword(data.email, data.password)
                .addOnCompleteListener { task ->

                    if (task.isComplete) {
                        trySend(
                            Response.Success(
                                data = auth.currentUser?.uid
                            )
                        )
                    }

                }
        } catch (e: Exception) {
            trySend(
                Response.Error(
                    e.message.toString()
                )
            )
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