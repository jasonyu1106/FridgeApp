package com.flarefridges.fridgeapp.model.domain

data class DrawerData(
    val itemCount: Int,
    val items: List<ItemData>,
)

data class ItemData(
    val name: String,
    val freshQuantity: Int,
    val rottenQuantity: Int
)
