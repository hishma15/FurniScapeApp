package com.example.furniscape.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
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
import com.example.furniscape.R
import com.example.furniscape.data.DataSource
import com.example.furniscape.model.RoomCategory

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
){
//    State to hold the current text of the search bar
    var searchText by remember { mutableStateOf("") }

    Column (
        modifier = modifier.fillMaxSize()
    ){
//        AppBar already exist above this composible

        Column (
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primaryContainer)   //same as app bar
                .padding(
                    start = dimensionResource(id = R.dimen.padding_small),
                    end = dimensionResource(id = R.dimen.padding_small),
                    top = dimensionResource(id = R.dimen.padding_small),
                    bottom = dimensionResource(id = R.dimen.padding_small)
                )
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

//      Banner Image section
        Image(
            painter = painterResource(id = R.drawable.homeimg),
            contentDescription = "Promotional Banner",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = dimensionResource(id = R.dimen.padding_small)
                ),

        )

//        Category section

        val roomCategories = DataSource().loadRoomCategories()
        ShopByRoomSection(categories = roomCategories)



    }

}


//Function for Horizontal Scrollable list
@Composable
fun ShopByRoomSection(categories: List<RoomCategory>){
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
                RoomCategoryItem(category)
            }
        }

    }
}

//Function for each category card
@Composable
fun RoomCategoryItem(category: RoomCategory) {
    Column (
        modifier = Modifier
            .padding(end = 12.dp)
            .width(120.dp)
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