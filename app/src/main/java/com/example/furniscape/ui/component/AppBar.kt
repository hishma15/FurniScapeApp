package com.example.furniscape.ui.component

import android.graphics.drawable.Icon
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.furniscape.R


//Opting in to use Material3 TopAppBar, which is currently marked as experimental
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FurniScapeAppBar(
    modifier: Modifier = Modifier,
    onNotificationClick: () -> Unit = {},
    onLikeClick: () -> Unit = {}
){
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.furniscape),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onPrimary

            )
        },
        actions = {
            IconButton(onClick = onNotificationClick) {
                Icon(
                    imageVector = Icons.Filled.Notifications,
                    contentDescription = "Notifications"
                )
            }
            IconButton(onClick = onLikeClick) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "Favorites"
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        modifier = modifier
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtherScreenAppBar(
    title: String = "", //category name visibility

    onBackClick: () ->Unit,

    isLiked: Boolean, //animation //2
    onLikeClick: () ->Unit = {},

    onShareClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {

    //--animation //2
    val scale by animateFloatAsState(
        targetValue = if (isLiked) 1.3f else 1f,
        animationSpec = tween(200),
        label = "LikeButtonScale"
    )

    TopAppBar(
        title = {
            Text (text = title)
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onPrimary

                )
            }

        },
        actions = {

            //--aniamtion //2
            IconButton(
                onClick = onLikeClick,
                modifier = Modifier.scale(scale)
            ) {
                Icon(
//                    imageVector = Icons.Default.FavoriteBorder,
                    imageVector = if (isLiked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = "Like",
                    tint = if (isLiked) Color.Red else MaterialTheme.colorScheme.onPrimary
                )
            }


            IconButton(onClick = onShareClick) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Share"
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        modifier = modifier
    )

}

//@Composable
//fun FurniScapeApp(modifier: Modifier = Modifier){
//    AppBar(
//        title = {Text(stringResource(id = R.string.app_name))},
//        colors = TopAppBarDefaults.mediumTopAppBarColors(
//            containerColor = MaterialTheme.colorScheme.primaryContainer
//        ),
//        modifier = Modifier
//    )
//}
