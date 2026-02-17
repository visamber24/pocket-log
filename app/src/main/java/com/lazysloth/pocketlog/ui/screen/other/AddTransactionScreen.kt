package com.lazysloth.pocketlog.ui.screen.other

import android.text.Selection
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.SelectionContainer
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lazysloth.pocketlog.R
import com.lazysloth.pocketlog.database.data.TransactionType
import com.lazysloth.pocketlog.ui.theme.PocketLogTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionScreen() {
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
            AddItems(modifier = Modifier)
            Spacer(Modifier.height(8.dp))
            Button(onClick = {}) {
                Text("Save")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItems(modifier: Modifier) {
    OutlinedTextField(
        value = "add amount",
        onValueChange = { },
        label = { Text(stringResource(R.string.add_amount)) },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        modifier = Modifier.fillMaxWidth(),
        enabled = true,
        singleLine = true
    )
    val options = listOf("Option 1", "Option 2", "Option 3", "Option 4", "Option 5")
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[0]) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            readOnly = true,
            value = selectedOptionText,
            onValueChange = {},
            label = { Text("Category") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption) },
                    onClick = {
                        selectedOptionText = selectionOption
                        expanded = false
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
    var selectedType by remember { mutableStateOf(TransactionType.DEBIT) }
    TransactionTypeRadioGroup(selectedType = selectedType, onTypeSelected = {selectedType = it} )

    OutlinedTextField(
        value = "note",
        onValueChange = { },
        label = { Text(stringResource(R.string.note)) },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
        ),
        modifier = Modifier.fillMaxWidth(),
        enabled = true,
        singleLine = true
    )
    OutlinedTextField(
        value = "description",
        onValueChange = { },
        label = { Text(stringResource(R.string.description)) },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
        ),
        modifier = Modifier.fillMaxWidth(),
        enabled = true,
        singleLine = true
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
        AddTransactionScreen()
    }
}