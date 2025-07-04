package com.example.furniscape.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.content.MediaType.Companion.Text
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.furniscape.R
import com.example.furniscape.ui.theme.FurniScapeTheme
import kotlinx.coroutines.delay

@Composable
fun  WelcomeScreen(
    onGetStartedClicked: () ->Unit,
    modifier: Modifier = Modifier
) {

    //--------ANIMATION---------
    var visible by remember { mutableStateOf(false) }

    //--Trigger the animation after a short delay
    LaunchedEffect(Unit) {
        delay(300)
        visible = true
    }


    //Background Image
    Box (
        modifier = modifier
            .fillMaxSize()
    ) {
        Image (
            painter = painterResource(id = R.drawable.back),
            contentDescription = stringResource(R.string.welcome_background_desc),
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )
        
        // Overlay content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.padding_medium)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            //--aniamtion
            AnimatedVisibility(
                visible = visible,
                //after 300ms the column containing all text and button appears with a fade-in _ vertical slide from top
                enter = fadeIn() + slideInVertically (initialOffsetY = { it / 2 })  //slides from top, 2- slides the content in from halfway up the screen
            ) {

                Column (horizontalAlignment = Alignment.CenterHorizontally) {

                    Text(
                        text = stringResource(id = R.string.welcome_message),
                        style = MaterialTheme.typography.displayLarge.copy(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_large)))

                    Text(
                        text = stringResource(id = R.string.furniscape_desc),
                        style = MaterialTheme.typography.headlineMedium.copy(
                            color = Color.Black
                        ),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_large)))

                    Button(
                        onClick = onGetStartedClicked,
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Text (
                            text = stringResource(id = R.string.get_started),
                            style =  MaterialTheme.typography.labelLarge
                        )
                    }

                }

            }



        }


    }
}

@Preview (showBackground = true)
@Composable
fun  WelcomeScreenPreview(){
    FurniScapeTheme {
        WelcomeScreen(onGetStartedClicked = {})
    }
}