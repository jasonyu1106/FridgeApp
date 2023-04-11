package com.flarefridges.fridgeapp.model.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DrawerDataEntity(
    @Json(name="total_count") val totalCount: Int,
    val data: List<Item>
)

data class Item(
    val name: String,
    @Json(name = "fresh_count") val freshCount: Int,
    @Json(name = "rotten_count") val rottenCount: Int
)