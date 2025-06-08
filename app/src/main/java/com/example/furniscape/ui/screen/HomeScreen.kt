package com.example.furniscape.ui.screen

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.furniscape.R
import com.example.furniscape.data.DataSource
import com.example.furniscape.data.sampleProducts
import com.example.furniscape.model.Product
import com.example.furniscape.model.RoomCategory
import com.example.furniscape.ui.component.ProductCard

@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier
){
//    State to hold the current text of the search bar
    var searchText by remember { mutableStateOf("") }

//    Category
    val roomCategories = DataSource().loadRoomCategories()

    LazyColumn (
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(bottom = 64.dp)  //allows bottom space for nav bar
    ){
//        AppBar already exist above this composible

        item {

            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primaryContainer)   //same as app bar
                    .padding(dimensionResource(id = R.dimen.padding_small))
            ){
                //        Search Bar container
                TextField(
                    value = searchText,
                    onValueChange = {searchText = it},  //update state when user types
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = dimensionResource(id = R.dimen.padding_medium),
                            vertical = dimensionResource(id = R.dimen.padding_small)
                        ),
                    placeholder = {Text(text = "Search Sofas, Beds, Decor Items...")},
                    singleLine = true,
                    shape = RoundedCornerShape(32.dp),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search Icon",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    trailingIcon = {
                        if (searchText.isNotEmpty()){
                            IconButton(onClick = { searchText = "" }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Clear Search",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                        focusedIndicatorColor = Color.Transparent,  //Hide Underline
                        unfocusedIndicatorColor = Color.Transparent, //Hide Underline
                        cursorColor = MaterialTheme.colorScheme.primary
                    )
                )

            }

        }

        item {
            //      Banner Image section
            Image(
                painter = painterResource(id = R.drawable.homeimg),
                contentDescription = "Promotional Banner",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(8f / 3f)
                    .padding(
                        vertical = dimensionResource(id = R.dimen.padding_small)
                    ),
                )

        }

        item {
            //      Category section
            ShopByRoomSection(categories = roomCategories, navController = navController)
        }

        item {
            //      Featured Product Section

            FeaturedProductsList(
                products = sampleProducts,
                onProductClick = { selectedProduct ->
                    //TODO: Naviagte to product details
                },
                onAddToCart = { product ->
                    //TODO: Handle add to cart logic
                }
            )
        }

    }

}


//Function for Horizontal Scrollable list -Category Section
@Composable
fun ShopByRoomSection(categories: List<RoomCategory>, navController: NavController){
    Column (modifier = Modifier.padding(16.dp)) {

        Text (
            text = stringResource(id = R.string.shop_by_room),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .padding(horizontal = 8.dp , vertical = 4.dp)
        )

        LazyRow (
            modifier = Modifier.padding(top = 8.dp)
        ){
            items(categories) { category ->

                val categoryKey = when (category.labelResId) {
                    R.string.living_room -> "living_room"
                    R.string.dining_room -> "dining_room"
                    R.string.bed_room -> "bedroom"
                    R.string.kitchen -> "kitchen"
                    R.string.office -> "office"
                    R.string.home_decor -> "home_decor"
                    else -> "all"
                }

                RoomCategoryItem(
                    category = category,
                    onClick = {navController.navigate("explore/$categoryKey")}
                )
            }
        }

    }
}

//Function for each category card
@Composable
fun RoomCategoryItem(
    category: RoomCategory,
    onClick: () -> Unit
) {
    Column (
        modifier = Modifier
            .padding(end = 12.dp)
            .width(120.dp)
            .clickable{ onClick() }
    ) {
        Image (
            painter = painterResource(id = category.imageResId),
            contentDescription = stringResource(category.labelResId),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))  //rounded corners
        )
        Text(
            text = stringResource(category.labelResId),
            modifier = Modifier.padding(top = 4.dp),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )
    }
}

//Function for Scrollable list - Products Section
@Composable
fun FeaturedProductsList(
    products: List<Product>,
    onProductClick: (Product) -> Unit,
    onAddToCart: (Product) -> Unit
) {

//    val scrollState = rememberScrollState()

    Column (modifier = Modifier
        .padding(16.dp)
//        .verticalScroll(scrollState) //scrollable manually
    ) {
        Text (
            text = stringResource(id = R.string.featured_products),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .padding(horizontal = 8.dp , vertical = 4.dp)
        )

//        to make verticale scrollable list
//        products.forEach { product ->
//            ProductCard(
//                product = product,
//                onViewDetails = { onProductClick(product) },
//                onAddToCart = { onAddToCart(product) }
//            )
//        }

        LazyRow (
            modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_small))
        ) {
            items (products) { product ->
                ProductCard(
                    product = product,
                    onViewDetails = {onProductClick(product)},
                    onAddToCart = {onAddToCart(product)}
                )
            }
        }
    }
}
//
////Function for each product card
//@Composable
//fun ProductCard(
//    product: Product,
//    onViewDetails: () -> Unit,
//    onAddToCart: () -> Unit,
//    modifier:Modifier = Modifier
//) {
//    Card(
//        modifier = modifier
//            .padding(dimensionResource(id = R.dimen.padding_small))
//            .width(220.dp)
//            .wrapContentHeight(),
//        shape = RoundedCornerShape(dimensionResource(id = R.dimen.padding_medium)),
//        elevation = CardDefaults.cardElevation(4.dp),
//        colors = CardDefaults.cardColors(
//            containerColor = MaterialTheme.colorScheme.surfaceVariant,
//            contentColor = MaterialTheme.colorScheme.onSurface
//            )
//    ) {
//        Column (
//            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
//        ) {
//            Image(
//                painter = painterResource(id = product.imageRes),
//                contentDescription = product.name,
//                contentScale = ContentScale.Fit,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(50.dp)
//                    .clip(RoundedCornerShape(12.dp))
//            )
//
//            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
//
//            Text(
//                text = product.name,
//                style = MaterialTheme.typography.titleMedium
//            )
//
//            Text (
//                text = "Rs. ${product.price}",
//                style = MaterialTheme.typography.bodyMedium,
//                color = MaterialTheme.colorScheme.primary
//            )
//
//            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
//
//            Row(
//                modifier = Modifier
//                   .fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Button(
//                    onClick = onViewDetails
//                ) {
//                    Text(
//                        text = stringResource(id = R.string.view_details),
//                        textAlign = TextAlign.Center
//                    )
//                }
//                Button (
//                    onClick = onAddToCart,
//                    modifier = Modifier
//                        .width(48.dp) // Set a fixed width
//                        .height(48.dp),
//                    contentPadding = PaddingValues(0.dp) // Remove inner padding
//                ) {
//                    Icon(
//                        imageVector = Icons.Default.ShoppingCart,
//                        contentDescription = stringResource(id = R.string.add_to_cart),
//                        tint = MaterialTheme.colorScheme.onPrimary,
//                        modifier = Modifier.padding(4.dp)
//                    )
//                }
//            }
//
//        }
//    }
//}