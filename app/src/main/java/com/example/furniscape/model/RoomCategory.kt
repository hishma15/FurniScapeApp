package com.example.furniscape.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class RoomCategory(
    @StringRes val labelResId: Int,
    @DrawableRes val imageResId: Int
)
