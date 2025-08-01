package cd.zgeniuscoders.floapp.ui.screens.payement_details

import PaymentDetailsState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import cd.zgeniuscoders.floapp.models.History
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
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
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
            is PaymentDetailsEvent.OnCardNumberChange -> _state.update { it.copy(cardNumber = event.card) }
            is PaymentDetailsEvent.OnPhoneNumberChange -> _state.update { it.copy(phoneNumber = event.phone) }
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
                val payment = state.value.payment?.copy(paid = true)
                val currentUserUuid = authenticationService.getCurrentUserUuid()

                pendingPaymentService
                    .onProcessCheckout(currentUserUuid ?: "", payment!!)
                    .onEach { res ->

                        when (res) {
                            is Response.Error -> {
                                _state.update {
                                    it.copy(
                                        error = res.message.toString(),
                                        isProcessing = false,
                                        isLoading = false
                                    )
                                }
                            }

                            is Response.Success -> {
                                saveHistory(currentUserUuid)
                            }
                        }


                    }.launchIn(viewModelScope)
            }

        }
    }

    private fun saveHistory(currentUserUuid: String?) {
        viewModelScope.launch {
            val currentDate = LocalDate.now()
            val dateFormat = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.FRENCH)
            val date = currentDate.format(dateFormat)

            val paymentNumber = if (_state.value.selectedPaymentMethod == "card") {
                _state.value.cardNumber
            } else {
                _state.value.phoneNumber
            }

            val data = History(
                generateRandomUuid(),
                paymentMethod = _state.value.selectedPaymentMethod,
                paymentNumber = paymentNumber,
                name = _state.value.payment?.title ?: "",
                amount = _state.value.payment?.amount ?: 0,
                paidAt = date
            )
            pendingPaymentService.saveHistory(currentUserUuid ?: "", data)
                .onEach { res ->
                    when (res) {
                        is Response.Error -> {
                            _state.update {
                                it.copy(
                                    error = res.message.toString(),
                                    isLoading = false,
                                    isProcessing = false
                                )
                            }
                        }

                        is Response.Success -> {
                            _state.update { it.copy(isLoading = false, isProcessing = false) }
                        }
                    }
                }.launchIn(viewModelScope)
        }
    }

    fun generateRandomUuid(length: Int = 4): String {
        val allowCases = "ABCDEFGGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890"
        return (1..length)
            .map { allowCases.random() }
            .joinToString("")
    }

}