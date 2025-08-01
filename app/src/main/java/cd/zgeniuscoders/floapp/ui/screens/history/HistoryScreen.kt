package cd.zgeniuscoders.floapp.ui.screens.history

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import cd.zgeniuscoders.floapp.ui.screens.history.components.TransactionCard

@Composable
fun HistoryScreen() {
    val vm = hiltViewModel<HistoryViewModel>()
    val state = vm.state

    HistoryBody(state)
}

@Composable
fun HistoryBody(state: HistoryState) {

    val transactions = state.histories

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "Historique des transactions",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        items(transactions) { transaction ->
            TransactionCard(transaction = transaction)
        }
    }
}
