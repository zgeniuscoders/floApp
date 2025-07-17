package cd.zgeniuscoders.floapp.ui.screens.payement_details

import PaymentDetailsState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import cd.zgeniuscoders.floapp.remote.AuthenticationService
import cd.zgeniuscoders.floapp.remote.PendingPaimentService
import cd.zgeniuscoders.floapp.ui.navigation.Screen
import cd.zgeniuscoders.floapp.utilis.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentDetailsViewModel @Inject constructor(
    private val authenticationService: AuthenticationService,
    private val pendingPaymentService: PendingPaimentService
) : ViewModel() {

    private var _state = MutableStateFlow(PaymentDetailsState())
    var state = _state
        .onStart {

        }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            _state.value
        )

    fun onEvent(event: PaymentDetailsEvent) {
        when (event) {
            is PaymentDetailsEvent.OnSelectedPaymentMethod ->
                _state.update { it.copy(selectedPaymentMethod = event.selectedPaymentMethod) }

            PaymentDetailsEvent.OnProcessCheckout -> onProcessCheckout()
        }
    }

    fun getPaymentDetails(paymentId: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            val currentUserUuid = authenticationService.getCurrentUserUuid()

            try {
                pendingPaymentService
                    .getPayment(currentUserUuid ?: "", paymentId)
                    .onEach { res ->

                        when (res) {
                            is Response.Error -> {
                                _state.update { it.copy(error = res.message.toString()) }
                            }

                            is Response.Success -> {
                                _state.update { it.copy(payment = res.data) }
                            }
                        }

                        _state.update { it.copy(isLoading = false) }

                    }.launchIn(viewModelScope)
            } catch (e: NullPointerException) {
                _state.update { it.copy(error = e.message.toString()) }
            }
        }
    }

    fun onProcessCheckout() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, isProcessing = true) }
            if (state.value.payment != null) {
                val payment = state.value.payment?.copy(isPaid = true)
                val currentUserUuid = authenticationService.getCurrentUserUuid()

                pendingPaymentService
                    .onProcessCheckout(currentUserUuid ?: "", payment!!)
                    .onEach { res ->

                        when (res) {
                            is Response.Error -> {
                                _state.update { it.copy(error = res.message.toString()) }
                            }

                            is Response.Success -> {

                            }
                        }

                        _state.update { it.copy(isLoading = false, isProcessing = false) }

                    }.launchIn(viewModelScope)
            }

        }
    }

}