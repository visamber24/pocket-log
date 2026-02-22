package com.lazysloth.pocketlog.ui.screen.other

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lazysloth.pocketlog.R
import com.lazysloth.pocketlog.database.data.Category
import com.lazysloth.pocketlog.database.data.TransactionType
import com.lazysloth.pocketlog.di.AppViewModelProvider
import com.lazysloth.pocketlog.ui.screen.home.uiState.Account
import com.lazysloth.pocketlog.ui.screen.home.uiState.AddTransactionUiState
import com.lazysloth.pocketlog.ui.screen.home.viewmodel.AddTransactionScreenViewmodel
import com.lazysloth.pocketlog.ui.theme.PocketLogTheme
import com.lazysloth.pocketlog.ui.theme.inputFieldShape
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Date


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionScreen(
    modifier: Modifier = Modifier,popBackStack : () -> Unit,
) {
    val context = LocalContext.current.applicationContext
    // Manually create a factory to provide the ViewModel with its required repository.
    val viewModel: AddTransactionScreenViewmodel = viewModel(
        factory = AppViewModelProvider.Factory
//        factory = object : ViewModelProvider.Factory {
//            override fun <T : ViewModel> create(modelClass: Class<T>): T {
//                if (modelClass.isAssignableFrom(AddTransactionScreenViewmodel::class.java)) {
//                    val repository = AppDataContainer(context).transactionRepository
//                    @Suppress("UNCHECKED_CAST")
//                    return AddTransactionScreenViewmodel(repository) as T
//                }
//                throw IllegalArgumentException("Unknown ViewModel class")
//            }
//        }
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.add_transaction)) }
            )
        }
    ) { innerPadding ->
        Column(
//            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            val uiState by viewModel.uiState.collectAsState()
            AddItems(
                modifier = Modifier, state = uiState,
                viewModel::onAccountSelected,
                viewModel::onAmountChange,
                viewModel::onExpandedChange,
                viewModel::onOptionSelected,
                viewModel::onTransactionTypeSelected,
                viewModel::onNoteValueChange,
                viewModel::onDescriptionChange,
                viewModel::onClickDate,
                viewModel::onDateChange
            )


            Spacer(Modifier.height(8.dp))
            Button(
                onClick = {
                    viewModel.saveTransaction()
                    Toast.makeText(context, "Transaction Saved", Toast.LENGTH_LONG).show()
                    popBackStack()
                },
                enabled = (uiState.addAmount.isNotBlank() && uiState.options.isNotEmpty())
            ) {
                Text("Save")
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItems(
    modifier: Modifier,
    state: AddTransactionUiState,
    onAccountSelected: (Account) -> Unit,
    onAmountChange: (String) -> Unit,
    onExpandedChange: (Boolean) -> Unit,
    onOptionSelected: (Category) -> Unit,
    onTransactionTypeSelected: (TransactionType) -> Unit,
    onNoteValueChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onClickDate: (Boolean) -> Unit,
    onDateChange: (Date) -> Unit
) {

    Text(
        text = "Account :",
        style = MaterialTheme.typography.titleMedium,
        textAlign = TextAlign.Start,
    )
    RadioGroup(
        options = state.accounts,
        selectedOption = state.account,
        onOptionSelected = { account -> onAccountSelected(account) },
//        optionToText =
    )
    OutlinedTextField(
        value = state.addAmount,
        onValueChange = { onAmountChange(it) },
        label = { Text("add amount") },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.transaction_input_item_padding)),
        enabled = true,
        shape = inputFieldShape,
        singleLine = true
    )



    ExposedDropdownMenuBox(
        expanded = state.expanded,
        onExpandedChange = { onExpandedChange(it) }
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.transaction_input_item_padding))
                .menuAnchor(),
            readOnly = true,
            value = state.selectCategoryOption,
            onValueChange = {},
            label = { Text("Category") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = state.expanded) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            shape = inputFieldShape
        )
        ExposedDropdownMenu(
            expanded = state.expanded,
            onDismissRequest = { onExpandedChange(false) }) {
            state.options.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption.name) },
                    onClick = {
                        onOptionSelected(selectionOption)
                        onExpandedChange(false)
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )

            }
        }
    }
    Spacer(Modifier.height(4.dp))
    Text(
        text = "Transaction option :",
        style = MaterialTheme.typography.titleMedium,
        textAlign = TextAlign.Start,

        )

    RadioGroup(
        options = state.transactionType,
        selectedOption = state.selectedType,
        onOptionSelected = {
            onTransactionTypeSelected(it)
        })


    OutlinedTextField(
        value = state.inputNote,
        onValueChange = { onNoteValueChange(it) },
        label = { Text(stringResource(R.string.note) + "..") },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.transaction_input_item_padding)),
        enabled = true,
        shape = inputFieldShape,
        singleLine = true
    )

    OutlinedTextField(
        value = state.inputDescription,
        onValueChange = { onDescriptionChange(it) },
        label = { Text(stringResource(R.string.description) + "..") },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(dimensionResource(R.dimen.description_dimension))
            .padding(dimensionResource(R.dimen.transaction_input_item_padding)),
        enabled = true,
        shape = inputFieldShape,
    )
//    var dateOpen by remember { mutableStateOf(false) }
    val formatter = remember { DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM) }
    Box(Modifier.clickable(enabled = true, onClick = {onClickDate(true)})){
        OutlinedTextField(
            value = state.dateTime.format(formatter),
            onValueChange = {},
            readOnly = true,
            enabled = false,
            modifier = Modifier
                .width(dimensionResource(R.dimen.date_button_padding))
        )
    }


    val datePickerState = rememberDatePickerState()
    println("dateOpen = ${state.dateOpen}")
    if (state.dateOpen) {
        DatePickerDialog(
            onDismissRequest = { onClickDate(false)},
            confirmButton = {
                TextButton(onClick = { onClickDate(false)}) {
                    datePickerState.selectedDateMillis?.let {millis ->
                        onDateChange(Date(millis))
                    }
                    Text("OK")
                }
            }
        ) {
            DatePicker(
                showModeToggle = true,
                state = datePickerState,
                title = { Text("Date") },
                headline = { Text("Select a date") })
        }
    }


}

@Composable
fun <T : Enum<T>> RadioGroup(
    options: List<T>,
    selectedOption: T,
    onOptionSelected: (T) -> Unit = {},
//    optionToText: (T) -> String
) {

    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        options.forEach { option ->

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable { onOptionSelected(option) } // make whole row clickable
            ) {

                RadioButton(
                    selected = selectedOption == option,
                    onClick = { onOptionSelected(option) } // required
                )

                Text(
                    text = option.name,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun AddTransactionPreview() {
    PocketLogTheme() {

        val fakeUiState = AddTransactionUiState(
            addAmount = "123.45",
            inputNote = "Groceries",
            inputDescription = "Weekly shopping at the supermarket",
            account = Account.Cash,
            selectedType = TransactionType.DEBIT,
        )
        Column(
//            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize()
        ) {
            AddItems(
                modifier = Modifier.padding(16.dp),
                state = fakeUiState,

                onAmountChange = {},
                onAccountSelected = {},
                onTransactionTypeSelected = {},
                onOptionSelected = {},
                onNoteValueChange = {},
                onDescriptionChange = {},
                onExpandedChange = {},
                onClickDate = {},
                onDateChange = {}
            )
        }
    }
}