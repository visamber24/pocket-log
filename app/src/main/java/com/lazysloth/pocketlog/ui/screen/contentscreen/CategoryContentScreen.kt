package com.lazysloth.pocketlog.ui.screen.contentscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.lazysloth.pocketlog.R
import com.lazysloth.pocketlog.database.data.Category1
import com.lazysloth.pocketlog.ui.screen.home.viewmodel.CategoryScreenViewModel

@Composable
fun CategoryContentScreen(
    modifier: Modifier, categoryList: List<Category1>, viewModel: CategoryScreenViewModel
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth()
    ) {
        items(categoryList) { category ->
            CategoryItem(
                icon = painterResource(viewModel.getIconRes(category.icon)),
                name = category.name,

                )
        }
    }
}

@Composable
fun CategoryItem(
    icon: Painter, name: String,
    onEdit: () -> Unit = {},
    onDelete: () -> Unit = {},
    onIgnore: () -> Unit = {},
    modifier: Modifier = Modifier,

    ) {
    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier = modifier
            .fillMaxWidth()
//            .padding(8.dp)
            .height(60.dp), elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                contentAlignment = Alignment.CenterStart
            ) {
                Row(horizontalArrangement = Arrangement.Start){
                    Icon(
                        painter = icon,
                        contentDescription = "category icon",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = name, style = MaterialTheme.typography.titleLarge
                    )
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.width(200.dp)
                ) {
                    DropdownMenuItem(text = { Text("Edit") }, onClick = {
//                        vm.getAccountId(account.id)
//                        println("account id -> ${account.id}")
                        expanded = false
                        onEdit()
                    })

                    DropdownMenuItem(text = { Text("Delete") }, onClick = {
                        expanded = false
                        onDelete()
                    })

                    DropdownMenuItem(text = { Text("Ignore") }, onClick = {
                        expanded = false
                        onIgnore()
                    })
                }
            }
            IconButton(
                onClick = { expanded = true },
//                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Icon(
                    painter = painterResource(R.drawable.more_horiz_24px),
                    contentDescription = "actions"
                )
            }


        }

    }
}