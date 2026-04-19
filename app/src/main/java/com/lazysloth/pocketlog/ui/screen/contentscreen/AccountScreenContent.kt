package com.lazysloth.pocketlog.ui.screen.contentscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lazysloth.pocketlog.R
import com.lazysloth.pocketlog.database.data.Account
import com.lazysloth.pocketlog.database.data.Account1

@Composable
fun AccountScreenContent(modifier: Modifier = Modifier, accounts: List<Account1>) {
//    Column(
//        verticalArrangement = Arrangement.Top,
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = modifier
//    ) {
    LazyColumn(
        modifier = modifier.fillMaxWidth()
    ) {
        items(accounts) { account ->
            AccountItem(
                account = account,
                onEdit = { /* TODO */ },
                onDelete = { /* TODO */ },
                onIgnore = { /* TODO */ }
            )
        }
    }
}
//}

@Composable
fun AccountItem(
    account: Account1,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onIgnore: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(contentAlignment = Alignment.CenterStart) {
                Column(
                    modifier = Modifier
                        .padding(12.dp)
                ) {
                    Text(
                        text = account.name,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = account.type.name,
                        style = MaterialTheme.typography.bodySmall
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "₹${account.balance}",
                        style = MaterialTheme.typography.titleLarge
                    )
                }

                // 3-dot button


                // Dropdown menu
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .width(200.dp)
                ) {
                    DropdownMenuItem(
                        text = { Text("Edit") },
                        onClick = {
                            expanded = false
                            onEdit()
                        }
                    )

                    DropdownMenuItem(
                        text = { Text("Delete") },
                        onClick = {
                            expanded = false
                            onDelete()
                        }
                    )

                    DropdownMenuItem(
                        text = { Text("Ignore") },
                        onClick = {
                            expanded = false
                            onIgnore()
                        }
                    )
                }
            }
            IconButton(
                onClick = { expanded = true },
//                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Icon(
                    painter = painterResource(R.drawable.more_horiz_24px),
                    contentDescription = "Menu"
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun AccountItemPreview() {
    val sampleList = listOf(
        Account1(1, 1, "Cash", Account.Cash, 5000.0),
        Account1(2, 1, "HDFC Debit", Account.DEBIT_CARD, 12000.0),
        Account1(3, 1, "SBI Credit", Account.CREDIT_CARD, -3000.0),
        Account1(4, 1, "GPay", Account.UPI, 2500.0)
    )
    MaterialTheme {
        AccountScreenContent(accounts = sampleList)
    }
}