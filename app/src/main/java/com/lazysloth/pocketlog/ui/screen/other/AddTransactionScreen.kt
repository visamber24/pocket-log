package com.lazysloth.pocketlog.ui.screen.other

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
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
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lazysloth.pocketlog.R
import com.lazysloth.pocketlog.database.AppDataContainer
import com.lazysloth.pocketlog.database.data.TransactionType
import com.lazysloth.pocketlog.ui.screen.home.uiState.AddTransactionUiState
import com.lazysloth.pocketlog.ui.screen.home.viewmodel.AddTransactionScreenViewmodel
import com.lazysloth.pocketlog.ui.theme.PocketLogTheme
import com.lazysloth.pocketlog.ui.theme.inputFieldShape


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current.applicationContext
    // Manually create a factory to provide the ViewModel with its required repository.
    val viewModel: AddTransactionScreenViewmodel = viewModel(
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
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            val uiState by viewModel.uiState.collectAsState()
            AddItems(modifier = Modifier, viewModel = viewModel, state = uiState)
            Spacer(Modifier.height(8.dp))
            Button(onClick = { viewModel.saveTransaction() }) {
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
    viewModel: AddTransactionScreenViewmodel
) {


    OutlinedTextField(
        value = state.addAmount,
        onValueChange = { viewModel.onAmountChange(it) },
//        label = { Text("add amount") },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.transaction_input_item_padding)),
        enabled = true,
        shape = inputFieldShape,
        singleLine = true
    )

    val options = listOf("Option 1", "Option 2", "Option 3", "Option 4", "Option 5")
    var selectedOptionText by remember { mutableStateOf(options[0]) }
    ExposedDropdownMenuBox(
        expanded = state.expanded,
        onExpandedChange = { viewModel.onExpandedChange(it) }
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.transaction_input_item_padding))
                .menuAnchor(),
            readOnly = true,
            value = selectedOptionText,
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
            onDismissRequest = { viewModel.onExpandedChange(false) }) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption) },
                    onClick = {
                        selectedOptionText = selectionOption
                        viewModel.onExpandedChange(false)
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )

            }
        }
    }
    Spacer(Modifier.height(4.dp))
    Text(
        text = "Transaction Type :",
        style = MaterialTheme.typography.titleMedium,
        textAlign = TextAlign.Start,

        )

    TransactionTypeRadioGroup(selectedType = state.selectedType, onTypeSelected = {
        viewModel.onTransactionTypeSelected(
            it
        )
    })


    OutlinedTextField(
        value = state.inputNote,
        onValueChange = { viewModel.onNoteValueChange(it) },
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
        onValueChange = { viewModel.onDescriptionChange(it) },
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


}

@Composable
fun TransactionTypeRadioGroup(
    selectedType: TransactionType,
    onTypeSelected: (TransactionType) -> Unit
) {

    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        TransactionType.entries.forEach { type ->

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable { onTypeSelected(type) } // make whole row clickable
            ) {

                RadioButton(
                    selected = selectedType == type,
                    onClick = { onTypeSelected(type) } // required
                )

                Text(
                    text = type.name,
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
        // This preview will not work because it cannot create the ViewModel
        // without a dependency injection framework that can provide a fake repository.
        // For now, you can test the UI in an emulator.
        AddTransactionScreen(Modifier)
    }
}