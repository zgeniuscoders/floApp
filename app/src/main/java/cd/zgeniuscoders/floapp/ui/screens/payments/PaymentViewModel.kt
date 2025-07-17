package cd.zgeniuscoders.floapp.ui.screens.payments

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cd.zgeniuscoders.floapp.remote.AuthenticationService
import cd.zgeniuscoders.floapp.remote.PendingPaimentService
import cd.zgeniuscoders.floapp.utilis.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val pendingPayment: PendingPaimentService,
    private val authenticationService: AuthenticationService
) : ViewModel() {

    var state by mutableStateOf(PaymentState())
        private set

    init {
        getPendingPayments()
    }

    private fun getPendingPayments() {
        state = state.copy(isLoading = true, errorMessage = "")

        val currentUserUuid = authenticationService.getCurrentUserUuid()

        viewModelScope.launch {
            currentUserUuid?.let {
                pendingPayment
                    .getPendingPayment("sVm0qQ4lC0SiSqZhZmAt821Lim03")
                    .onEach { res ->

                        state = when (res) {
                            is Response.Error -> {
                                state.copy(isLoading = false, errorMessage = res.message.toString())
                            }

                            is Response.Success -> {
                                state.copy(
                                    isLoading = false,
                                    pendingPayments = res.data ?: emptyList()
                                )
                            }
                        }

                    }
                    .launchIn(viewModelScope)
            }

        }
    }

}