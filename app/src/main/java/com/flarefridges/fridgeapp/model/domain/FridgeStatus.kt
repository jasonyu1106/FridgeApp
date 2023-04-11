package com.flarefridges.fridgeapp.model.domain

data class FridgeStatus(
    val temperature: Float,
    val drawerData: List<DrawerStatus>
)

data class DrawerStatus(
    val id: Int,
    val name: String,
    val isUnlocked: Boolean,
    val isCameraSupported: Boolean,
)

