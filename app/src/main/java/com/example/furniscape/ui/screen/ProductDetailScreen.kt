package com.example.furniscape.ui.screen

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.furniscape.R
import com.example.furniscape.data.sampleProducts
import com.example.furniscape.ui.component.OtherScreenAppBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ProductDetailScreen(
    productId: Int,
    onBackClick: () -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier
){
    val product = sampleProducts.find { it.id == productId }

    if (product == null){
        Text(
            text = stringResource(id = R.string.product_not_found)
        )
        return
    }

//    To malke changable used var
    var quantity by remember { mutableStateOf(1) }

    //ORIENTATION CHANGE
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    //-------ANIMATION------
    var visible by remember { mutableStateOf(false) }  //1
    val coroutineScope = rememberCoroutineScope() //1

    var isLiked by remember { mutableStateOf(false) }  //2

    //1
    LaunchedEffect(Unit) {
        visible = true
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(400)) + scaleIn(initialScale = 0.8f, animationSpec = tween(400)),
        exit = fadeOut(animationSpec = tween(300)) + scaleOut(targetScale = 0.8f, animationSpec = tween(300))
    ){

        Scaffold (
            topBar = {
                OtherScreenAppBar(
                    title = "Product Details",

//                    onBackClick = onBackClick,
                    //--animation -- fades out and scales out //1
                    onBackClick = {
                        visible = false
                        coroutineScope.launch {
                            delay(300) // after the animation completes the navigation happens
                            onBackClick()
                        }
                    },

                    //--animation //2
                    isLiked = isLiked,
                    onLikeClick = { isLiked = !isLiked },

                    onShareClick = { /* Share Product */ }
                )
            },
            bottomBar = {
//            Add to cart button
                Button(
                    onClick = {
                        navController.navigate("cart") {
                            // Pop up to the start destination of nav graph (or the Explore route),
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true  // Prevent multiple instances of Cart in back stack
                            restoreState = true     // Restore saved state when reselecting bottom nav item
                        }

                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = dimensionResource(id = R.dimen.padding_medium),
                            end = dimensionResource(id = R.dimen.padding_medium),
                            top = dimensionResource(id = R.dimen.padding_medium),
                            bottom = 48.dp
                        ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text (text = stringResource(id = R.string.add_to_cart))
                }
            }
        ){ innerPadding ->
            Column (
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(dimensionResource(id = R.dimen.padding_medium))
                    .verticalScroll(rememberScrollState()) //MAKE SCROLLABLE
            ) {

                if (isLandscape) {
                    Row (modifier = Modifier.fillMaxWidth()) {

                        Image(
                            painter = painterResource(id = product.imageRes),
                            contentDescription = product.name,
                            modifier = modifier
                                .weight(1f)
                                .height(250.dp)
                        )

                        Spacer(modifier = modifier.height(dimensionResource(id =R.dimen.padding_medium)))

                        Column (
                            modifier = Modifier
                                .weight(1f)
                                .align(Alignment.CenterVertically)
                        ){

                            //            Product Name
                            Text(
                                text = product.name,
                                style = MaterialTheme.typography.headlineSmall
                            )

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {

                                Text(
                                    text = stringResource(id = R.string.price) + "${product.price}",
                                    style = MaterialTheme.typography.bodyLarge
                                )

                                Row {
                                    Button(
                                        onClick = {
                                            if (quantity > 1) quantity--
                                        },
                                        contentPadding = PaddingValues(4.dp),
                                        modifier = Modifier.width(36.dp)

                                    ) {
                                        Text("-")
                                    }

                                    Text(
                                        text = quantity.toString(),
                                        modifier = Modifier
                                            .padding(horizontal = 12.dp)
                                            .align(Alignment.CenterVertically),
                                        style = MaterialTheme.typography.titleMedium
                                    )

                                    Button(
                                        onClick = { quantity ++ },
                                        contentPadding = PaddingValues(4.dp),
                                        modifier = Modifier.width(36.dp)
                                    ) {
                                        Text ("+")
                                    }

                                }

                            }

                            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))


                        }

                    }

                    // Description Section
                    Text(
                        text = stringResource(id = R.string.description), // Example: "Description"
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        text = "This is a high-quality ${product.name.lowercase()} perfect for modern home decor. Made from durable materials and built to last.",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 4.dp)
                    )

                }
                else
                {
                    Image(
                        painter = painterResource(id = product.imageRes),
                        contentDescription = product.name,
                        modifier = modifier
                            .fillMaxWidth()
                            .height(250.dp)
                    )

                    Spacer(modifier = modifier.height(dimensionResource(id =R.dimen.padding_medium)))

//            Product Name
                    Text(
                        text = product.name,
                        style = MaterialTheme.typography.headlineSmall
                    )

                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))

//            Price and Quantity Selector
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Text(
                            text = stringResource(id = R.string.price) + "${product.price}",
                            style = MaterialTheme.typography.bodyLarge
                        )

                        Row {
                            Button(
                                onClick = {
                                    if (quantity > 1) quantity--
                                },
                                contentPadding = PaddingValues(4.dp),
                                modifier = Modifier.width(36.dp)

                            ) {
                                Text("-")
                            }

                            Text(
                                text = quantity.toString(),
                                modifier = Modifier
                                    .padding(horizontal = 12.dp)
                                    .align(Alignment.CenterVertically),
                                style = MaterialTheme.typography.titleMedium
                            )

                            Button(
                                onClick = { quantity ++ },
                                contentPadding = PaddingValues(4.dp),
                                modifier = Modifier.width(36.dp)
                            ) {
                                Text ("+")
                            }

                        }

                    }

                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))

                    // Description Section
                    Text(
                        text = stringResource(id = R.string.description), // Example: "Description"
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        text = "This is a high-quality ${product.name.lowercase()} perfect for modern home decor. Made from durable materials and built to last.",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }




//            Text(
//                text = stringResource(id = R.string.category) + "${product.categoryId}",
//                style = MaterialTheme.typography.bodyMedium
//            )
            }

        }

    }






}