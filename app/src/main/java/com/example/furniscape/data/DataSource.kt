package com.example.furniscape.data

import com.example.furniscape.R
import com.example.furniscape.model.RoomCategory

class DataSource {
    fun loadRoomCategories(): List<RoomCategory> {
        return  listOf(
            RoomCategory(R.string.living_room,R.drawable.livingroom),
            RoomCategory(R.string.dining_room,R.drawable.diningroom),
            RoomCategory(R.string.bed_room,R.drawable.bedroom),
            RoomCategory(R.string.kitchen,R.drawable.kitchen_image),
            RoomCategory(R.string.office,R.drawable.officefurniture),
            RoomCategory(R.string.home_decor,R.drawable.homedecor)

        )
    }
}