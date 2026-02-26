package com.lazysloth.pocketlog.ui.screen.authentication

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lazysloth.pocketlog.R
import com.lazysloth.pocketlog.di.AppViewModelProvider
import com.lazysloth.pocketlog.ui.screen.authentication.viewmodel.AuthViewModel
import com.lazysloth.pocketlog.ui.screen.authentication.viewmodel.SignupViewModel
import com.lazysloth.pocketlog.ui.theme.PocketLogTheme
import com.lazysloth.pocketlog.ui.theme.inputFieldShape

@Composable
fun CreateNewPasswordScreen(
    onClickNext: () -> Unit
) {
    val context = LocalContext.current
    val viewModel: SignupViewModel = viewModel()
    val authViewModel: AuthViewModel = viewModel(
        factory = AppViewModelProvider.Factory
    )
    val uiState by viewModel.uiState.collectAsState()
    val focusManager = LocalFocusManager.current

    val onDone = {
        if (uiState.password.isNotEmpty() && uiState.password == uiState.confirmPassword) {
            authViewModel.saveUser(uiState)
            Toast.makeText(context, "Password Saved!", Toast.LENGTH_SHORT).show()
            onClickNext()
        } else {
            Toast.makeText(context, "Passwords do not match or are empty.", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = uiState.password,
            onValueChange = { viewModel.onPasswordChange(it) },
            label = {
                Text(
                    stringResource(R.string.create_new_password)
                )
            },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            ),
            shape = inputFieldShape,
            singleLine = true,
            modifier = Modifier.width(280.dp)
        )
        Spacer(Modifier.height(10.dp))
        TextField(
            value = uiState.confirmPassword,
            onValueChange = {viewModel.onConfirmPasswordChange(it) },
            label = {
                Text(
                    stringResource(R.string.confirm_password)
                )
            },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { onDone() }
            ),
            shape = inputFieldShape,
            singleLine = true,
            modifier = Modifier.width(280.dp)
        )
        Spacer(Modifier.height(10.dp))
        Button(onClick = onDone) {
            Text(
                stringResource(R.string.next)
            )
        }
    }
}

@Preview
@Composable
fun CreateNewPasswordScreenPreview() {
    PocketLogTheme {
        CreateNewPasswordScreen(onClickNext = {})
    }
}