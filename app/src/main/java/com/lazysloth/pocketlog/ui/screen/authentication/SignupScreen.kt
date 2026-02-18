package com.lazysloth.pocketlog.ui.screen.authentication

import android.widget.Toast
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lazysloth.pocketlog.R
import com.lazysloth.pocketlog.database.data.PasswordManager
import com.lazysloth.pocketlog.ui.screen.authentication.viewmodel.AuthViewModel
import com.lazysloth.pocketlog.ui.screen.authentication.viewmodel.AuthViewModelFactory
import com.lazysloth.pocketlog.ui.screen.authentication.viewmodel.SignupViewModel
import com.lazysloth.pocketlog.ui.theme.PocketLogTheme

@Composable
fun SignupScreen(
    onClickGo: () -> Unit,
    onClickAlreadyAUser: () -> Unit,
    modifier: Modifier = Modifier,

    ) {
    val context = LocalContext.current
    val viewModel: SignupViewModel = viewModel()
    val authViewModel: AuthViewModel = viewModel(
            factory = AuthViewModelFactory(PasswordManager(context))
            )
    val focusManager = LocalFocusManager.current
    val uiState by viewModel.uiState.collectAsState()
    val onGo = {
        if(uiState.username.isNotEmpty() && uiState.firstName.isNotEmpty() && uiState.lastName.isNotEmpty() && uiState.email.isNotEmpty()){
            authViewModel.savePassword(uiState.password)
            Toast.makeText(context, "Account Created", Toast.LENGTH_SHORT).show()
            onClickGo()
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .width(280.dp),
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
            value = uiState.username,
            onValueChange = { viewModel.onUsernameChange(it) },
            label = {
                Text(
                    text = stringResource(R.string.username)
                )
            },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(10.dp),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,

                ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            ),
            modifier = modifier,
            singleLine = true
        )
        Spacer(Modifier.height(10.dp))
        TextField(
            value = uiState.firstName,
            onValueChange = { viewModel.onFirstNameChange(it) },
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
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,

                ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            ),
            modifier = modifier,
            singleLine = true


        )
        Spacer(Modifier.height(10.dp))
        TextField(
            value = uiState.lastName,
            onValueChange = { viewModel.onLastNameChange(it) },
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
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            ),
            modifier = modifier,
            singleLine = true,


            )
        Spacer(Modifier.height(10.dp))
        TextField(
            value = uiState.email,
            onValueChange = { viewModel.onEmailChange(it) },
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
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Email
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            ),
            modifier = modifier,
            singleLine = true

        )
        Spacer(Modifier.height(10.dp))
        TextField(
            value = uiState.password,
            onValueChange = { viewModel.onPasswordChange(it) },
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
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            ),

            keyboardActions = KeyboardActions(
                onDone = {
                    onClickGo()
                }
            ),
            modifier = modifier,
            singleLine = true

        )

        Spacer(Modifier.height(12.dp))
        Row(
            Modifier.align(Alignment.Start)
        ) {
            TextButton(
                onClick = { onClickAlreadyAUser() },
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
                onClick = { onGo() }
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
        SignupScreen(onClickGo = {}, onClickAlreadyAUser = {}, Modifier.width(280.dp))
    }
}