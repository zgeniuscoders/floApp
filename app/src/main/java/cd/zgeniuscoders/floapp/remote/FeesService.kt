package cd.zgeniuscoders.floapp.remote

import cd.zgeniuscoders.floapp.models.Fees
import cd.zgeniuscoders.floapp.models.User
import cd.zgeniuscoders.floapp.utilis.Response
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class FeesService(
    db: FirebaseFirestore
) {

    private var collection = db.collection("users")

    fun getFees(): Flow<Response<List<Fees>>> = callbackFlow {
        try {
            collection
                .addSnapshotListener { value, error ->

                    if (error != null) {
                        trySend(
                            Response.Error(
                                message = error.message.toString()
                            )
                        )
                    }

                    if (value != null) {
                        val fees = value.toObjects(Fees::class.java)
                        trySend(
                            Response.Success(
                                fees
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