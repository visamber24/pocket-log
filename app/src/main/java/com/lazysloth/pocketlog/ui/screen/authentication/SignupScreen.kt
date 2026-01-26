package com.lazysloth.pocketlog.ui.screen.authentication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lazysloth.pocketlog.R
import com.lazysloth.pocketlog.ui.theme.PocketLogTheme

@Composable
fun SignupScreen(modifier: Modifier = Modifier) {
    var createUsername by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .width(280.dp)
            ,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            stringResource(R.string.create_account),
            fontSize = 20.sp,

            modifier = Modifier
                .padding(start = 62.dp, bottom = 20.dp)
                .align(Alignment.Start)
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(16.dp)
                


        )
        TextField(
            value = createUsername,
            onValueChange = { createUsername = it },
            label = {
                Text(
                    text = "Username"
                )
            },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = modifier,
            singleLine = true
        )
        Spacer( Modifier.height(10.dp))
        TextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = {
                Text(
                    stringResource(R.string.first_name)
                )

            },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(10.dp),

            modifier = modifier,
            singleLine = true


        )
        Spacer( Modifier.height(10.dp))
        TextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = {
                Text(
                    "Last Name"
                )
            },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = modifier,
            singleLine = true

        )
        Spacer( Modifier.height(10.dp))
        TextField(
            value = email,
            onValueChange = { email = it },
            label = {
                Text(
                    stringResource(R.string.email)
                )
            },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = modifier,
            singleLine = true

        )
        Spacer( Modifier.height(10.dp))
        TextField(
            value = password,
            onValueChange = {password = it},
            label = {
                Text(
                    stringResource(R.string.password)
                )
            },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = modifier,
            singleLine = true

        )

        Spacer(Modifier.height(12.dp))
        Row(
            Modifier.align(Alignment.Start)
        ){
            TextButton(
                onClick = {},
                modifier = Modifier

                    .padding(start = 55.dp)
            ) {
                Text(
                    stringResource(R.string.login_),
//                    fontSize = 16.sp
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(Modifier.width(85.dp))
            Button(
                onClick = {}
            ) {
                Text(
                    text = stringResource(R.string.go),
                    fontSize = 20.sp
                )
            }

        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun SignupPreviewScreen() {
    PocketLogTheme {
        SignupScreen(Modifier.width(280.dp))
    }
}