package com.lazysloth.pocketlog.ui.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.lazysloth.pocketlog.R
import com.lazysloth.pocketlog.database.data.Account
import com.lazysloth.pocketlog.database.data.Account1
import com.lazysloth.pocketlog.ui.navigationitem.ApplicationBottomNavigation
import com.lazysloth.pocketlog.ui.screen.contentscreen.AccountScreenContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(onClickAdd: ()->Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = ApplicationBottomNavigation.ACCOUNT.name,
//                        modifier = Modifier.padding(20.dp)
                    )
                },
            )
        },
        floatingActionButton = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.End,
            ) {
                FloatingActionButton(onClick = {onClickAdd}){
                    Icon(
                        painter = painterResource(R.drawable.add_24px),
                        contentDescription = "add account"
                    )
                }
            }


        }
    ) {
        val sampleList = listOf(
            Account1(1, 1, "Cash", Account.Cash, 5000),
            Account1(2, 1, "HDFC Debit", Account.DEBIT_CARD, 12000),
            Account1(3, 1, "SBI Credit", Account.CREDIT_CARD, -3000),
            Account1(4, 1, "GPay", Account.UPI, 2500)
        )
        AccountScreenContent(accounts = sampleList, modifier = Modifier.padding(it))
    }
}
