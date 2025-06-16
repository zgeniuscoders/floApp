package cd.zgeniuscoders.floapp.ui.screens.dashboard

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cd.zgeniuscoders.floapp.remote.AuthenticationService
import cd.zgeniuscoders.floapp.remote.UserService
import cd.zgeniuscoders.floapp.utilis.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val userService: UserService,
    private val authenticationService: AuthenticationService
) : ViewModel() {

    var state by mutableStateOf(DashboardState())
        private set


    fun onTriggerEvent(event: DashboardEvent) {
        when (event) {
            DashboardEvent.OnRefreshPage -> getUser()
            DashboardEvent.OnLoadData -> getUser()
        }
    }

    private fun getUser() {
        state = state.copy(isLoading = true, errorMessage = "")

        val currentUserUuid = authenticationService.getCurrentUserUuid()

        viewModelScope.launch {
            currentUserUuid?.let {
                userService
                    .getUser(it)
                    .onEach { res ->

                        state = when (res) {
                            is Response.Error -> {
                                state.copy(isLoading = false, errorMessage = res.message.toString())
                            }

                            is Response.Success -> {
                                Log.i("FLOAPP", res.data.toString())
                                state.copy(isLoading = false, user = res.data)
                            }
                        }

                    }
                    .launchIn(viewModelScope)
            }

        }
    }
}