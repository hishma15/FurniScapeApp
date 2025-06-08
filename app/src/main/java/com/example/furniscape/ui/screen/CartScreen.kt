package com.example.furniscape.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.furniscape.R
import com.example.furniscape.data.sampleProducts
import com.example.furniscape.model.Product

@Composable
fun CartScreen(
    modifier: Modifier = Modifier,

) {
    val cartItems = sampleProducts.take(2)  //use 2 dummy items

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    Column (
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.padding_medium))
            .verticalScroll(rememberScrollState())
    ){
        Text(
            text = stringResource(id = R.string.my_cart),
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))

        cartItems.forEach{ product ->
            CartItem(product = product)
            Spacer(modifier = Modifier.height(12.dp))

        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_large)))

//        Price Summary

        PriceSummarySection(
            subtotal = 320000.00,
            delivery = 15000.00,
            tax = 32000.00
        )

        Spacer(modifier = Modifier.height(dimensionResource( id = R.dimen.padding_medium)))

        Button(
            onClick = { /* Proceed to checkout */ },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = stringResource(id = R.string.proceed_to_checkout)
            )
        }

    }


}


//Cart items function
@Composable
fun CartItem(product: Product) {
    var quantity by remember { mutableStateOf(1) }

    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.padding_small)),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = painterResource(id = product.imageRes),
            contentDescription = product.name,
            modifier = Modifier
                .width(50.dp)
                .height(50.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier
                .weight(1f)
        ) {

            Text(product.name, style = MaterialTheme.typography.bodyLarge)
            Text("Rs. ${product.price}", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))

        }

        Column (
            horizontalAlignment = Alignment.End
        )
        {
//            Quantity Selector
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

            Spacer(modifier = Modifier.height(4.dp))

            Row {

                Text(
                    "Rs. ${product.price * quantity}"
                )

                Spacer(modifier = Modifier.width(4.dp))

                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    modifier = Modifier.padding(end = 4.dp),
                    tint = Color.Red
                )



            }

        }

    }


}

//Price Summary section function
@Composable
fun PriceSummarySection(
    subtotal: Double,
    delivery: Double,
    tax: Double
)  {

    val total = subtotal + delivery + tax

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(dimensionResource(id = R.dimen.padding_medium))
    ){
        SummaryRow(
            stringResource(id = R.string.subtotal), subtotal
        )
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_smaller)))
        SummaryRow(
            stringResource( id = R.string.delivery_fee), delivery
        )
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_smaller)))
        SummaryRow(
            stringResource( id = R.string.tax),tax
        )
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_smaller)))

        Divider (
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
            modifier = Modifier
                .padding(vertical = dimensionResource(id = R.dimen.padding_small))
        )

        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_smaller)))

        SummaryRow(
            stringResource(id = R.string.total), total, isBold = true
        )
    }

}

@Composable
fun SummaryRow(label: String, amount: Double, isBold: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = if (isBold) MaterialTheme.typography.titleMedium else MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Rs. ${amount.toInt()}",
            style = if (isBold) MaterialTheme.typography.titleMedium else MaterialTheme.typography.bodyMedium
        )
    }
}