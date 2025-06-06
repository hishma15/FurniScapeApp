package com.example.furniscape.ui.screen

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.furniscape.R
import com.example.furniscape.data.sampleProducts
import com.example.furniscape.ui.component.OtherScreenAppBar

@Composable
fun ProductDetailScreen(
    productId: Int,
    onBackClick: () -> Unit,
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
    val isLandscape = configuration.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE

    Scaffold (
        topBar = {
            OtherScreenAppBar(
                onBackClick = onBackClick,
                onLikeClick = { /* Add to favourites */ },
                onShareClick = { /* Share Product */ }
            )
        },
        bottomBar = {
//            Add to cart button
            Button(
                onClick = {
                    //handle add to cart logic

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