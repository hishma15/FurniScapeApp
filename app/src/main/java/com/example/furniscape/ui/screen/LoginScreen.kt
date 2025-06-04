package com.example.furniscape.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RenderEffect
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.furniscape.R

@Composable
fun LoginScreen(
    onLoginClicked: () -> Unit,
    onRegisterClicked: () -> Unit,
    modifier: Modifier = Modifier
) {

//    Input persist and will reset every frame
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }  // Hide password default

//  Dart theme
    val isDarkTheme = isSystemInDarkTheme()

    Box(modifier = modifier.fillMaxSize()) {
//        Backgorund Image fill whole screen
        Image(
            painter = painterResource(id = R.drawable.backgroundimg),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )

//        Form Container - Box contains bottom half with rounded top corners
        Surface(
          color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.6f),
          shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .align(Alignment.BottomCenter)
            )
        {
            Column (
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_large))
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    text = stringResource(id = R.string.login),
                    style = MaterialTheme.typography.headlineLarge.copy (
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary  // Text color will be visible according to theme color
                    )
                )

                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_large)))

//                Email or Username input field
                TextField(
                    value = email,
                    onValueChange = {email = it},
                    label = { Text(text = stringResource(id = R.string.email_username))},
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))

//                Password input field
                TextField(
                    value = password,
                    onValueChange = {password = it},
                    label = { Text(text = stringResource(id = R.string.password)) },
                    modifier = Modifier.fillMaxWidth(),
//                    Invisible password
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        val description = if (passwordVisible) "Hide Password" else "Show Password"

                        IconButton(onClick = { passwordVisible = !passwordVisible}) {
                            Icon(
                                imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = description,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                )

                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))

                Button(
                    onClick = onLoginClicked,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.padding_large)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary)
                ){
                    Text(
                        text = stringResource(id = R.string.login)
                    )
                }

                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))

                TextButton(
                    onClick = onRegisterClicked
                ) {
                    Text(
                        text = stringResource(id = R.string.dont_have_account),
//                        color = MaterialTheme.colorScheme.primary
                        color = Color.White
                    )
                    Text(
                        text = stringResource(id = R.string.simple_register),
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Light),
//                        color = MaterialTheme.colorScheme.primary
                        color = Color.White
                    )
                }

            }
        }
    }
}