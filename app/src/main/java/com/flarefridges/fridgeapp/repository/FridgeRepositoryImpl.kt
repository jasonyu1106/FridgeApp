package com.flarefridges.fridgeapp.repository

import android.graphics.Bitmap
import com.flarefridges.fridgeapp.model.domain.DrawerData
import com.flarefridges.fridgeapp.model.domain.FridgeStatus
import com.flarefridges.fridgeapp.model.mappers.DrawerDataMapper
import com.flarefridges.fridgeapp.model.mappers.FridgeStatusMapper
import com.flarefridges.fridgeapp.network.FridgeDataSource
import javax.inject.Inject

class FridgeRepositoryImpl @Inject constructor(
    private val dataSource: FridgeDataSource,
    private val fridgeStatusMapper: FridgeStatusMapper,
    private val drawerDataMapper: DrawerDataMapper
) : FridgeRepository {
    override suspend fun fetchStatus(): FridgeStatus? =
        dataSource.fetchFridgeStatus()?.let {
            fridgeStatusMapper.mapFromEntity(it)
        }

    override suspend fun unlockDrawer(drawerId: Int): Boolean =
        dataSource.unlockFridge(drawerId)

    override suspend fun lockDrawer(drawerId: Int): Boolean =
        dataSource.lockFridge(drawerId)

    override suspend fun fetchDrawerImage(): Bitmap? =
        dataSource.fetchSnapshot()

    override suspend fun fetchDrawerList(): DrawerData? =
        dataSource.fetchGroceries()?.let {
            drawerDataMapper.mapFromEntity(it)
        }
}