package com.example.furniscape.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.furniscape.R
import com.example.furniscape.data.sampleProducts
import com.example.furniscape.model.Product

@Composable
fun ExploreScreen(
    navController: NavController, //To navigate to product details screen
    modifier: Modifier = Modifier

    ) {

    Column (
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.padding_medium))
    ) {
        Text(
            text = stringResource(id = R.string.explore_products),
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(4.dp)
        ) {
            items(sampleProducts) { product ->
                ProductCard(
                    product = product,
                    onViewDetails = {
                        navController.navigate("productDetails/${product.id}")
                    },
                    onAddToCart = { /* TODO */ }
                )
            }
        }
    }
}
