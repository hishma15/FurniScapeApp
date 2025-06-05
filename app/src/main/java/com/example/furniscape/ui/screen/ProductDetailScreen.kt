package com.example.furniscape.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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

    Scaffold (
        topBar = {
            OtherScreenAppBar(
                onBackClick = onBackClick,
                onLikeClick = { /* Add to favourites */ },
                onShareClick = { /* Share Product */ }
            )
        }
    ){ innerPadding ->
        Column (
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(dimensionResource(id = R.dimen.padding_medium))
        ) {
            Image(
                painter = painterResource(id = product.imageRes),
                contentDescription = product.name,
                modifier = modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )

            Spacer(modifier = modifier.height(dimensionResource(id =R.dimen.padding_medium)))

            Text(
                text = product.name,
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = stringResource(id = R.string.price) + "${product.price}",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = stringResource(id = R.string.category) + "${product.categoryId}",
                style = MaterialTheme.typography.bodyMedium
            )
        }

    }



}