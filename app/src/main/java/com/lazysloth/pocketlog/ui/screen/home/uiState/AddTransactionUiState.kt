package com.lazysloth.pocketlog.ui.screen.home.uiState

import com.lazysloth.pocketlog.data.Account
import com.lazysloth.pocketlog.data.Category1
import com.lazysloth.pocketlog.data.TransactionType
import java.time.ZonedDateTime

data class AddTransactionUiState(
    val selectedAccountId: Long? = null,
    val accounts : List<Account> = listOf(),
    val id: Int = 0,
    val addAmount: String = "",
    val accountName: String = "",
    val categoryList: List<Category1> = listOf(
//        Category1(icon = "ic_food", name = "food", type = CategoryType.EXPENSE,),
//        Category1(icon = "ic_shopping", name = "shopping", type = CategoryType.EXPENSE,),
//        Category1(icon = "ic_gift", name = "gift", type = CategoryType.EXPENSE,),
//        Category1(icon = "ic_health", name = "health", type = CategoryType.EXPENSE,),
//        Category1(icon = "ic_entertainment", name = "entertainment", type = CategoryType.EXPENSE,),
//        Category1(icon = "ic_bills", name = "bills", type = CategoryType.EXPENSE,)
    ),
    val category : Category1 = Category1(),
    val selectedCategoryId: Long? = null,
    val expandedAccount: Boolean = false,
    val expandedCategory: Boolean = false,
    val transactionType: List<TransactionType> = TransactionType.entries,
    val selectedType: TransactionType = TransactionType.TRANSFER,
    val inputNote: String = "",
    val inputDescription: String = "",
    val dateOpen : Boolean = false,
    val timeOpen : Boolean = false,
    val dateTime: ZonedDateTime = ZonedDateTime.now(),
    var isSaving: Boolean = false
)
//var addAmount by remember { mutableStateOf("0.0") }
//var expanded by remember { mutableStateOf(false) }

//var transactionType by remember { mutableStateOf(TransactionType.DEBIT) }
//var inputNote by remember {mutableStateOf("")}
//var inputDescription by remember { mutableStateOf("") }
