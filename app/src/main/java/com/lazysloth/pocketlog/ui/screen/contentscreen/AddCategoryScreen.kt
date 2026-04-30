package com.lazysloth.pocketlog.ui.screen.contentscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lazysloth.pocketlog.R
import com.lazysloth.pocketlog.database.data.CategoryType
import com.lazysloth.pocketlog.ui.screen.contentscreen.viewmodel.AddCategoryScreenViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCategoryScreen(
    popBackStack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AddCategoryScreenViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Create Category",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        // Name Input
        OutlinedTextField(
            value = viewModel.name,
            onValueChange = { viewModel.onNameChange(it) },
            label = { Text("Category Name") },
            placeholder = { Text("e.g. Groceries") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.edit_24px),
                    contentDescription = null
                )
            }
        )

        // Type Selection (Simplified Radio Group)
        Text(
            text = "Select Type",
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.align(Alignment.Start)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CategoryType.entries.forEach { type ->
                FilterChip(
                    selected = viewModel.selectedType == type,
                    onClick = { viewModel.onTypeChange(type) },
                    label = { Text(type.name) },
                    modifier = Modifier.weight(2f),
                    shape = RoundedCornerShape(8.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        val selectedIconKey by viewModel.selectedIcon.collectAsState()
        val colorScheme = MaterialTheme.colorScheme

        LazyRow() {

            items(uiState.categoryIcon) { icon ->
                val isSelected = selectedIconKey == icon.key
                Icon(
                    painter = painterResource(icon.iconRes),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .size(70.dp, 50.dp)
                        .background(
                            if (isSelected) colorScheme.primary.copy(alpha = 0.2f)
                            else Color.Transparent
                        )
                        .border(
                            width = if (isSelected) 2.dp else 0.dp,
                            color = if (isSelected) colorScheme.primary
                            else Color.Transparent,
//                            shape = RoundedCornerShape(12.dp)
                        )
                        .clickable(enabled = true, onClick = {
                            viewModel.selectedIcon.value = icon.key
                        })
                        .padding(8.dp)
                )
            }
        }

        // Save Button
        Button(
            onClick = {
                viewModel.saveCategory()
                popBackStack()
            }, // Replace with actual userId
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            enabled = viewModel.name.isNotBlank() && viewModel.selectedIcon.collectAsState().value != null
        ) {
            Icon(painter = painterResource(R.drawable.check_24px), contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text("Save Category", fontSize = 16.sp)
        }
    }
}
