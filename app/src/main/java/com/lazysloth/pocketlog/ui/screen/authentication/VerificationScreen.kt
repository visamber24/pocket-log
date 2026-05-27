package com.lazysloth.pocketlog.ui.screen.authentication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lazysloth.pocketlog.R
import com.lazysloth.pocketlog.data.VerificationType
import com.lazysloth.pocketlog.ui.screen.authentication.viewmodel.AuthViewModel
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerificationScreen(
    authViewModel: AuthViewModel = koinViewModel(),
    verificationType: VerificationType = VerificationType.EMAIL, // EMAIL or PHONE_OTP
    emailOrPhone: String = "", // Pass email or phone number
    onVerificationSuccess: () -> Unit,
    onBackClick: () -> Unit
) {
    val uiState by authViewModel.signUpUiState.collectAsState()
    val context = LocalContext.current

    var otpCode by remember { mutableStateOf("") }
    var isResendEnabled by remember { mutableStateOf(false) }
    var countdown by remember { mutableStateOf(60) }

    // Countdown for Resend
    LaunchedEffect(key1 = uiState.isOtpSent) {
        if (uiState.isOtpSent && verificationType == VerificationType.PHONE_OTP) {
            countdown = 60
            while (countdown > 0) {
                delay(1000)
                countdown--
            }
            isResendEnabled = true
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Verify Account") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(painterResource(R.drawable.arrow_back_24px), contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Icon(
                painter = painterResource(R.drawable.mail_24px), // or Icons.Default.Sms for OTP
                contentDescription = null,
                modifier = Modifier.size(80.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = when (verificationType) {
                    VerificationType.EMAIL -> "Check your email"
                    VerificationType.PHONE_OTP -> "Enter OTP"
                },
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = when (verificationType) {
                    VerificationType.EMAIL ->
                        "We sent a verification link to\n$emailOrPhone\nPlease check your inbox (and spam folder)."
                    VerificationType.PHONE_OTP ->
                        "We sent a 6-digit OTP to\n$emailOrPhone"
                },
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(32.dp))

            // OTP Input Field (only for Phone OTP)
            if (verificationType == VerificationType.PHONE_OTP) {
                OutlinedTextField(
                    value = otpCode,
                    onValueChange = {
                        if (it.length <= 6) otpCode = it
                    },
                    label = { Text("Enter 6-digit OTP") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { authViewModel.verifyOtp(otpCode) },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = otpCode.length == 6 && !uiState.isLoading
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp))
                    } else {
                        Text("Verify OTP")
                    }
                }
            }

            // For Email Verification - Check Status Button
            if (verificationType == VerificationType.EMAIL) {
                Button(
                    onClick = { authViewModel.checkEmailVerification() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("I have verified my email")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Resend Button
            TextButton(
                onClick = {
                    if (verificationType == VerificationType.EMAIL) {
                        authViewModel.resendVerificationEmail()
                    } else {
                        authViewModel.resendOtp(emailOrPhone)
                    }
                    isResendEnabled = false
                },
                enabled = when (verificationType) {
                    VerificationType.EMAIL -> true
                    VerificationType.PHONE_OTP -> isResendEnabled
                }
            ) {
                Text(
                    text = if (verificationType == VerificationType.PHONE_OTP && !isResendEnabled) {
                        "Resend OTP in $countdown s"
                    } else {
                        "Resend Code"
                    }
                )
            }

            // Status Messages
            if (uiState.error != null) {
                Text(
                    text = uiState.error!!,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            if (uiState.message != null) {
                Text(
                    text = uiState.message!!,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }

    // Auto navigate when verified
    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
//            authViewModel.getUserIdByIdentifier(uiState.username)
            onVerificationSuccess()
        }
    }
}