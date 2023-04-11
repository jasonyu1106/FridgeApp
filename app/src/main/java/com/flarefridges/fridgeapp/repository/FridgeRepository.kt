package com.flarefridges.fridgeapp.repository

import android.graphics.Bitmap
import com.flarefridges.fridgeapp.model.domain.DrawerData
import com.flarefridges.fridgeapp.model.domain.FridgeStatus

interface FridgeRepository {
    suspend fun fetchStatus(): FridgeStatus?
    suspend fun unlockDrawer(drawerId: Int): Boolean
    suspend fun lockDrawer(drawerId: Int): Boolean
    suspend fun fetchDrawerImage(): Bitmap?
    suspend fun fetchDrawerList(): DrawerData?
}