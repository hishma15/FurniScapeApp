package com.example.furniscape.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.furniscape.FurniScape
import com.example.furniscape.R
import com.example.furniscape.data.sampleProducts
import com.example.furniscape.model.Product
import com.example.furniscape.ui.component.OtherScreenAppBar
import com.example.furniscape.ui.component.ProductCard
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ExploreScreen(
    navController: NavController, //To navigate to product details screen
    modifier: Modifier = Modifier,
    categoryFilter: String = "all"

    ) {

    val filteredProducts = if (categoryFilter == "all") {
        sampleProducts
    }else {
        sampleProducts.filter { it.categoryId == categoryFilter }
    }

    //if it's a category specific screen (not "all"), a seperate layout
    if (categoryFilter != "all") {

        // Category drill-down screen → no bottom nav, has back button
        var visible by remember { mutableStateOf(false) }
        val coroutineScope = rememberCoroutineScope()

        LaunchedEffect(Unit) {
            visible = true
        }

        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(animationSpec = tween(400)) + scaleIn(initialScale = 0.8f),
            exit = fadeOut(animationSpec = tween(300)) + scaleOut(targetScale = 0.8f)
        ) {
            Scaffold(
                topBar = {
                    OtherScreenAppBar(
                        isLiked = false,
                        title = categoryFilter.replaceFirstChar { it.uppercaseChar() },
                        onBackClick = {
                            visible = false
                            coroutineScope.launch {
                                delay(300)
                                navController.popBackStack()
                            }
                        },
                        onShareClick = {} // Optional
                    )
                }
            ) { innerPadding ->
                ExploreGrid(
                    products = filteredProducts,
                    navController = navController,
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(dimensionResource(id = R.dimen.padding_medium))
                )
            }
        }

    } else {
        // Main Explore Screen → use normal column layout (wrapped in Scaffold by parent)
        Column(
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

            ExploreGrid(
                products = filteredProducts,
                navController = navController
            )
        }

    }

//    Column (
//        modifier = modifier
//            .fillMaxSize()
//            .padding(dimensionResource(id = R.dimen.padding_medium))
//    ) {
//        Text(
//            text = stringResource(id = R.string.explore_products),
//            style = MaterialTheme.typography.headlineSmall,
//            textAlign = TextAlign.Center,
//            modifier = Modifier.padding(bottom = 8.dp)
//        )
//
//        LazyVerticalGrid(
//            columns = GridCells.Fixed(2),
//            contentPadding = PaddingValues(4.dp)
//        ) {
//            items(filteredProducts) { product ->
//                ProductCard(
//                    product = product,
//                    onViewDetails = {
//                        navController.navigate("productDetails/${product.id}")
//                    },
//                    onAddToCart = { /* TODO */ }
//                )
//            }
//        }
//    }
}

@Composable
fun ExploreGrid(
    products: List<Product>,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(4.dp)
    ) {
        items(products) { product ->
            ProductCard(
                product = product,
                onViewDetails = {
                    navController.navigate("productDetails/${product.id}")
                },
                onAddToCart = {
                    navController.navigate(FurniScape.Cart.name)
                    {

                    // Pop up to the start destination of nav graph (or the Explore route),
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true  // Prevent multiple instances of Cart in back stack
                    restoreState = true     // Restore saved state when reselecting bottom nav item

                    }
                }
            )
        }
    }
}
