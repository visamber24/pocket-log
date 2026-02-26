package com.lazysloth.pocketlog.ui.screen.authentication

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lazysloth.pocketlog.R
import com.lazysloth.pocketlog.di.AppViewModelProvider
import com.lazysloth.pocketlog.ui.screen.authentication.viewmodel.AuthViewModel
import com.lazysloth.pocketlog.ui.screen.authentication.viewmodel.SignupViewModel
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onForgetClick: () -> Unit,
    onNewUserClick: () -> Unit,
    onClickGo: () -> Unit

) {
    val context = LocalContext.current
    val viewModel : AuthViewModel = viewModel(
        factory = AppViewModelProvider.Factory
    )
    val signupViewModel: SignupViewModel = viewModel()
    val loginUiState by signupViewModel.uiState.collectAsState()
    var password by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
//    val savedPassword by viewModel.savedPassword.collectAsState()
    val onDone = {
        viewModel.viewModelScope.launch {
            if (loginUiState.password.isNotEmpty() && loginUiState.identifier.isNotEmpty() && viewModel.verifyPassword(
                    loginUiState.identifier, loginUiState.password
                )
            ) {
                Toast.makeText(context, "Login Successful!", Toast.LENGTH_LONG).show()
                onClickGo()
            } else {
                Toast.makeText(context, "Invalid password.", Toast.LENGTH_SHORT).show()
            }
        }
    }
    Column(

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TextField(
            value = loginUiState.identifier,
            onValueChange = { signupViewModel.onIdentifierChange(it) },

            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.person_24px),
                    contentDescription = null
                )
            },
            modifier = modifier
                .width(280.dp),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            ),
            shape = RoundedCornerShape(20.dp),
            singleLine = true,
            label = { Text(stringResource(R.string.username_emailId)) },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )

        )
        Spacer(modifier = Modifier.height(30.dp))
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,

            ) {
//            val visualTransform = if(password.value)
            OutlinedTextField(
                value = loginUiState.password,
                onValueChange = { signupViewModel.onPasswordChange(it) },
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.password_person_24px),
                        contentDescription = null
                    )
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        onDone()
                    },
                ),
                visualTransformation = VisualTransformation.None,
                shape = RoundedCornerShape(20.dp),
                label = { Text(stringResource(R.string.password)) },
                singleLine = true,
                modifier = Modifier.width(210.dp)

            )
            Spacer(modifier = Modifier.width(10.dp))
            Button(
                onClick = { onDone() },
                modifier = Modifier
                    .width(59.dp)
                    .height(50.dp)
                    .padding(0.dp),
                contentPadding = PaddingValues(8.dp)

//                border = BorderStroke(width = 1.dp, brush = Brush.linearGradient() )

            ) {
//                Icon(
//                    Icons.AutoMirrored.Filled.ArrowForward,
//                    contentDescription = null
//                )
//                Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                Text(
                    text = stringResource(R.string.go),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()


                )
            }
        }
        TextButton(
            onClick = { onForgetClick() },
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 62.dp)
        ) {
            Text(
                text = "forgot pass?"
            )
        }
        Button(
            onClick = { onNewUserClick() },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = "New User?"
            )
        }

    }
}

@Preview(showSystemUi = true)
@Composable
fun LoginPreview() {
    LoginScreen(onForgetClick = {}, onNewUserClick = {}, onClickGo = {})
}
