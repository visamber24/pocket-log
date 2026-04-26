package com.lazysloth.pocketlog.ui.screen.contentscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.lazysloth.pocketlog.R
import com.lazysloth.pocketlog.database.data.AccountType
import com.lazysloth.pocketlog.ui.screen.contentscreen.viewmodel.AddAccountViewModel
import com.lazysloth.pocketlog.ui.screen.home.uiState.AddAccountUiState
import com.lazysloth.pocketlog.ui.theme.inputFieldShape
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddAccountScreen(popBackStack: () -> Unit) {
    val vm: AddAccountViewModel = koinViewModel()
    val uiState by vm.uiState.collectAsState()
    AddAccountScreenImpl(
        uiState = uiState,
        onSave = {
            popBackStack()
            vm.saveAccount()
        },
        onInitialBalanceChange = vm::onInitialBalanceChange,
        onAccountNameChange = vm::onAccountNameChange,
        onExpandedAccountType = vm::onExpandAccountType,
        onAccountSelected = vm::onAccountSelected
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAccountScreenImpl(
    uiState: AddAccountUiState,
    onSave: () -> Unit,
    onInitialBalanceChange: (String) -> Unit,
    onAccountNameChange: (String) -> Unit,
    onExpandedAccountType: (Boolean) -> Unit,
    onAccountSelected: (AccountType) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Account") }
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
            AddItems(
                uiState = uiState,
                onInitialBalanceChange = onInitialBalanceChange,
                onAccountNameChange = onAccountNameChange,
                onExpandedAccountType = onExpandedAccountType,
                onAccountSelected = onAccountSelected
            )


            Spacer(Modifier.height(8.dp))
            Button(
                onClick = {
                    onSave()
                },
                enabled = (uiState.initialBalance.isNotBlank() && uiState.accountName.isNotEmpty())
            ) {
                Text("Save")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItems(
    uiState: AddAccountUiState,
    onInitialBalanceChange: (String) -> Unit,
    onAccountNameChange: (String) -> Unit,
    onExpandedAccountType:(Boolean)->Unit,
    onAccountSelected: (AccountType)-> Unit,
) {

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.transaction_input_item_padding)),
        value = uiState.initialBalance,
        onValueChange = { onInitialBalanceChange(it) },
        label = { Text("Initial Balance") },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
        ),
        shape = inputFieldShape,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.transaction_input_item_padding)),
        value = uiState.accountName,
        onValueChange = { onAccountNameChange(it) },
        label = { Text("Name of Account") },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
        ),
        shape = inputFieldShape,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
    )


    ExposedDropdownMenuBox(
        expanded = uiState.accountTypeExpanded,
        onExpandedChange = { onExpandedAccountType(it) }
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.transaction_input_item_padding))
                .menuAnchor(),
            readOnly = true,
            value = uiState.accountType.name,
            onValueChange = {},
            label = { Text("Account") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = uiState.accountTypeExpanded) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            shape = inputFieldShape
        )
        ExposedDropdownMenu(
            expanded = uiState.accountTypeExpanded,
            onDismissRequest = { onExpandedAccountType(false) }) {
            uiState.accountTypes.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption.name) },
                    onClick = {
                        onAccountSelected(selectionOption)
                        onExpandedAccountType(false)
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )

            }
        }
    }
}