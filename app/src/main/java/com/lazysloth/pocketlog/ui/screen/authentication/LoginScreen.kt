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
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lazysloth.pocketlog.R

@Composable
fun LoginScreen( modifier: Modifier = Modifier) {
    var usernameInput by remember {mutableStateOf("")}
    var defaultUsername by remember { mutableStateOf("") }
    Column(

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ){
        TextField(
            value = defaultUsername,
            onValueChange = {defaultUsername= it},

            leadingIcon = {
                Icon(painter = painterResource(R.drawable.person_24px),
                contentDescription = null)
                          },
            modifier = modifier
                .width(280.dp),
            shape = RoundedCornerShape(20.dp),
            singleLine = true,
            label = { Text(stringResource(R.string.username) )},
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
            OutlinedTextField(
                value = usernameInput,
                onValueChange = { usernameInput = it },
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.password_person_24px),
                        contentDescription = null
                    )
                },
                shape = RoundedCornerShape(20.dp),
                label = { Text(stringResource(R.string.password)) },
                singleLine = true,
                modifier = Modifier.width(210.dp)

            )
            Spacer(modifier = Modifier.width(10.dp))
            Button(
                onClick = {},
                modifier = Modifier
                    .width(59.dp)
                    .height(50.dp)
                    .padding(0.dp)
                    ,
                contentPadding = PaddingValues(8.dp)

//                border = BorderStroke(width = 1.dp, brush = Brush.linearGradient() )

            ){
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
            onClick = {},
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 62.dp)
        ){
            Text(
                text = "forgot pass?"
            )
        }
        Button(
            onClick = {},
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
fun LoginPreview()
{
    LoginScreen()
}
