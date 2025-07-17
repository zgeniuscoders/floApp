package cd.zgeniuscoders.floapp.remote

import android.util.Log
import cd.zgeniuscoders.floapp.models.PendingPayment
import cd.zgeniuscoders.floapp.utilis.Response
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class PendingPaimentService(
    db: FirebaseFirestore
) {

    private var collection = db.collection("users")

    fun getPendingPayment(userId: String): Flow<Response<List<PendingPayment>>> = callbackFlow {
        try {
            Log.i("FLOAPP", "before calling getPendingPayment")

            collection
                .document(userId)
                .collection("pendingPayments")
                .addSnapshotListener { value, error ->

                    Log.i("FLOAPP", "called from getPendingPayment")

                    if (error != null) {
                        trySend(
                            Response.Error(
                                message = error.message.toString()
                            )
                        )
                    }

                    if (value != null) {
                        Log.i("FLOAPP", "geting")
                        val paymentPending = value.toObjects(PendingPayment::class.java)
                        trySend(
                            Response.Success(
                                paymentPending
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

    fun getPayment(userId: String, paymentId: String): Flow<Response<PendingPayment>> =
        callbackFlow {
            try {
                collection
                    .document(userId)
                    .collection("pendingPayments")
                    .document(paymentId)
                    .addSnapshotListener { value, error ->

                        if (error != null) {
                            trySend(
                                Response.Error(
                                    message = error.message.toString()
                                )
                            )
                        }

                        if (value != null) {
                            val paymentPending = value.toObject(PendingPayment::class.java)
                            trySend(
                                Response.Success(
                                    paymentPending
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

    fun onProcessCheckout(userId: String, payment: PendingPayment): Flow<Response<Boolean>> =
        callbackFlow {
            try {
                collection
                    .document(userId)
                    .collection("pendingPayments")
                    .document(payment.id)
                    .set(payment)
                    .addOnCompleteListener {

                        trySend(
                            Response.Success(
                                true
                            )
                        )

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