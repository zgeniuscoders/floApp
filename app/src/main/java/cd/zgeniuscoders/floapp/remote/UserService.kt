package cd.zgeniuscoders.floapp.remote

import cd.zgeniuscoders.floapp.models.User
import cd.zgeniuscoders.floapp.utilis.Response
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class UserService(
    db: FirebaseFirestore
) {

    private var collection = db.collection("users")

    fun getUser(id: String): Flow<Response<User>> = callbackFlow {
        try {
            collection
                .document(id)
                .addSnapshotListener { value, error ->

                    if (error != null) {
                        trySend(
                            Response.Error(
                                message = error.message.toString()
                            )
                        )
                    }

                    if (value != null) {
                        val user = value.toObject(User::class.java)
                        trySend(
                            Response.Success(
                                user
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

}