package com.lazysloth.pocketlog.ui.screen.home.uiState

import com.lazysloth.pocketlog.database.data.Category
import com.lazysloth.pocketlog.database.data.TransactionType
import java.time.ZonedDateTime
import java.util.Date

data class AddTransactionUiState(
    val account: Account = Account.Cash,
    val accounts : List<Account> = Account.entries,
    val id: Int = 0,
    val addAmount: String = "0.0",
    val options: List<Category> = Category.entries,
    val option : Category = Category.HEALTHCARE,
    val selectCategoryOption : String = "",
    val expanded: Boolean = false,
    val transactionType: List<TransactionType> = TransactionType.entries,
    val selectedType: TransactionType = TransactionType.TRANSFER,
    val inputNote: String = "",
    val inputDescription: String = "",
    val dateOpen : Boolean = false,
    val timeOpen : Boolean = false,
    val dateTime: ZonedDateTime = ZonedDateTime.now()
)
//var addAmount by remember { mutableStateOf("0.0") }
//var expanded by remember { mutableStateOf(false) }

//var transactionType by remember { mutableStateOf(TransactionType.DEBIT) }
//var inputNote by remember {mutableStateOf("")}
//var inputDescription by remember { mutableStateOf("") }
enum class Account(

){
    Personal,
    Cash,
    Family
}