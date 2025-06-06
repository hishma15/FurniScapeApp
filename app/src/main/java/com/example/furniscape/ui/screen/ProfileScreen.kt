package com.example.furniscape.ui.screen

import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.PersonPin
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.furniscape.R

@Composable
fun ProfileScreen(
    onLogOutClick: () -> Unit,
    windowWidthSizeClass: WindowWidthSizeClass,
    modifier: Modifier = Modifier

) {

    val isLandscapeOrWide = windowWidthSizeClass != WindowWidthSizeClass.Compact
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.padding_medium)),
        verticalArrangement = Arrangement.SpaceBetween  //Pushes the log out button to bottom
    ){

        Column (
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
        ) {

            //            Header
            Text (
                text = stringResource(id = R.string.my_profile),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_medium))
            )

//            Profile Info section
            ProfileInfoSection(
                name = stringResource(id = R.string.profile_name),
                email = stringResource(id = R.string.profile_email)
            )

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_large)))

//            Options Section
//            ProfileOptionItem(icon = Icons.Default.ShoppingCart , title = stringResource(id = R.string.my_orders))
//            ProfileOptionItem(icon = Icons.Default.Favorite , title = stringResource(id = R.string.wishlist))
//            ProfileOptionItem(icon = Icons.Default.Password , title = stringResource(id = R.string.change_password))
//            ProfileOptionItem(icon = Icons.Default.Settings , title = stringResource(id = R.string.settings))

            val options = listOf(
                Icons.Default.ShoppingCart to stringResource(id = R.string.my_orders),
                Icons.Default.Favorite to stringResource(id = R.string.wishlist),
                Icons.Default.Password to stringResource(id = R.string.change_password),
                Icons.Default.Settings to stringResource(id = R.string.settings)

            )

            if (isLandscapeOrWide) {
//                Two column layout using Rows
                for (i in options.indices step 2) {
                    Row (modifier = Modifier.fillMaxWidth()) {
                        ProfileOptionItem(
                            icon = options[i].first,
                            title = options[i].second,
                            modifier = Modifier.weight(1f)
                        )
                        if (i + 1 < options.size) {
                            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_large))) // Add space between items
                            ProfileOptionItem(
                                icon = options [i+1].first,
                                title = options [i + 1].second,
                                modifier = Modifier.weight(1f)
                            )
                        } else {
                            Spacer(modifier = Modifier.weight(1f))  //Empty space if add number
                        }
                    }

                }
            }
            else {
                //single column layout
                options.forEach{
                    ProfileOptionItem(icon = it.first, title = it.second)
                }
            }

        }

        //Logout Button
        Button(
            onClick = onLogOutClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = dimensionResource(id = R.dimen.padding_small)),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red,
                contentColor = Color.White
            )
        ) {
            Text(text = stringResource(id = R.string.log_out))
        }


    }

}

//Profile Info section function
@Composable
fun ProfileInfoSection(name: String, email: String) {
    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon (
            imageVector = Icons.Default.PersonPin,
            contentDescription = "User Image",
            modifier = Modifier
                .size(72.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_medium)))

        Column {
            Text (text = name, style = MaterialTheme.typography.titleMedium)
            Text (text = email, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
        }
    }

}

//Profile option section function
@Composable
fun ProfileOptionItem(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {

    Row (
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = dimensionResource(id = R.dimen.padding_large))
            .clickable { onClick() }
            .clip(RoundedCornerShape(0))
            .background(color = MaterialTheme.colorScheme.surface),
//            .background(color = MaterialTheme.colorScheme.secondaryContainer),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row (verticalAlignment = Alignment.CenterVertically) {

            Icon(
                imageVector = icon,
                contentDescription = title,
                modifier = Modifier.size(dimensionResource(id =R.dimen.padding_large)),
//                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_medium)))

            Text(text = title, style = MaterialTheme.typography.bodyLarge)

        }

        Icon(
            imageVector = Icons.Default.ArrowForwardIos,
            contentDescription = "Go",
            modifier = Modifier.size(dimensionResource(id = R.dimen.padding_medium))
        )
    }

}

