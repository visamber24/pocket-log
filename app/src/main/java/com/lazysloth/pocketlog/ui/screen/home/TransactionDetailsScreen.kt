package com.lazysloth.pocketlog.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lazysloth.pocketlog.ui.screen.home.viewmodel.TransactionDetailsUiState
import com.lazysloth.pocketlog.ui.theme.PocketLogTheme


@Composable
fun TransactionDetailDialog(uiState : TransactionDetailsUiState){
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {

        Text(
            text = "Transaction #${uiState.id}",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
        )


        DetailsRow(label = "Amount", value = uiState.amount)
        DetailsRow(label = "Account", value = uiState.account)
        DetailsRow(label = "Category", value = uiState.category)
        DetailsRow(label = "Type", value = uiState.transactionType)
        DetailsRow(label = "Note", value = uiState.note)
        DetailsRow(label = "Description", value = uiState.description)
        DetailsRow(label = "Date", value = uiState.dateTime)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
        ) {
            TextButton(onClick = {}) {
                Text(text = "Close")
            }
        }
    }

}

@Composable
fun TransactionDetailsScreen(onBack:()->Unit) {


}


@Composable
private fun DetailsRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
    ) {
        Text(
            text = "$label:",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.weight(0.35f),
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(0.65f),
        )
    }
}

@Composable
@Preview(showSystemUi = true)
fun DetailsScreenPreview() {
    PocketLogTheme() {
        TransactionDetailsScreen {  }
    }
}

@Preview(showBackground = true)
@Composable
private fun TransactionDetailsDialogPreview() {
    PocketLogTheme {
        Box(modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()) {
            TransactionDetailDialog(
                uiState = TransactionDetailsUiState(
                    id = 42,
                    amount = "₹1,250.00",
                    account = "Cash",
                    category = "Groceries",
                    transactionType = "Expense",
                    note = "Weekly run",
                    description = "Bought vegetables, fruits and dairy items",
                    dateTime = "05 Sep 2026, 07:30 PM",
                ),
//                onDismiss = {},
            )
        }
    }
}
