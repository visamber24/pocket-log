package com.lazysloth.pocketlog.ui.screen.contentscreen

import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lazysloth.pocketlog.R
import com.lazysloth.pocketlog.database.Transaction
import com.lazysloth.pocketlog.database.data.Category
import com.lazysloth.pocketlog.database.data.TransactionType
import com.lazysloth.pocketlog.ui.screen.home.DashboardScreen
import com.lazysloth.pocketlog.ui.screen.home.uiState.Account
import com.lazysloth.pocketlog.ui.theme.PocketLogTheme

@Composable
fun DashboardScreenContent(transList: List<Transaction>,modifier: Modifier = Modifier) {

        val listState = rememberLazyListState()
        LazyColumn(
            flingBehavior = ScrollableDefaults.flingBehavior(),
            modifier = modifier
                .fillMaxSize()
        ) {
            items(transList) { item ->

                RecordContent(
                    icon = painterResource(R.drawable.donut_large_24px),
                    typeOfTransaction = item.transactionType,
                    amount = item.amount.toInt(),
                    category = item.category,
                    account = item.account,
                    note = item.note,
                    description = item.description,
                )

            }
        }

}

@Composable
fun RecordContent(
    icon: Painter,
    typeOfTransaction: TransactionType,
    amount: Int,
    category: Category,
    account: Account,
    note: String,
    description: String,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Icon
            Icon(
                painter = icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            // Middle content
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = category.name,
                    fontSize = 14.sp,
                    maxLines = 1
                )
                Text(
                    text = note,
                    fontSize = 11.sp,
                    color = Color.Gray,
                    maxLines = 1
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Right content
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "₹$amount",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = account.name,
                    fontSize = 11.sp,
                    color = Color.Gray,
                    maxLines = 1
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun DashboardPreview() {
    PocketLogTheme { DashboardScreen(onClickAdd = {}) }
}