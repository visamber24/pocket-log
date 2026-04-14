package com.lazysloth.pocketlog.ui.screen.home

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.lazysloth.pocketlog.ui.navigationitem.ApplicationBottomNavigation
import com.lazysloth.pocketlog.ui.screen.contentscreen.viewmodel.ProfileScreenViewmodel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(){
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = ApplicationBottomNavigation.PROFILE.name,
//                        modifier = Modifier.padding(20.dp)
                    )
                },
            )
        }
    ) {
        ProfileContentScreen(Modifier.padding(it))
    }
}@Composable
fun ProfileContentScreen(modifier : Modifier = Modifier)
{
    val viewModel : ProfileScreenViewmodel = koinViewModel()
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = viewModel.username,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold

        )
        Log.d("Usernamee", viewModel.username + "haha")
    }
}