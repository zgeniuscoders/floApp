package cd.zgeniuscoders.floapp.ui.screens.history

import cd.zgeniuscoders.floapp.models.History

data class HistoryState(
    val isLoading: Boolean = false,
    var histories: List<History> = emptyList(),
    var errorMessage: String = ""
)
