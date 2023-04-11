package com.flarefridges.fridgeapp.model.entity

import com.flarefridges.fridgeapp.model.domain.DrawerStatus
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FridgeStatusEntity(
    val temperature: Float,
    val drawerData: List<DrawerInfo>
)

data class DrawerInfo(
    val id: Int,
    val name: String,
    val lock: Boolean,
    val camera: Boolean
)
