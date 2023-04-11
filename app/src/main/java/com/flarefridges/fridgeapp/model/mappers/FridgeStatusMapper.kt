package com.flarefridges.fridgeapp.model.mappers

import com.flarefridges.fridgeapp.model.domain.DrawerStatus
import com.flarefridges.fridgeapp.model.domain.FridgeStatus
import com.flarefridges.fridgeapp.model.entity.DrawerInfo
import com.flarefridges.fridgeapp.model.entity.FridgeStatusEntity
import javax.inject.Inject

class FridgeStatusMapper @Inject constructor() : EntityMapper<FridgeStatus, FridgeStatusEntity> {
    override fun mapFromEntity(entity: FridgeStatusEntity) = FridgeStatus(
        temperature = entity.temperature,
        drawerData = entity.drawerData.map {
            DrawerStatus(
                id = it.id,
                name = it.name,
                isUnlocked = !it.lock,
                isCameraSupported = it.camera
            )
        }
    )

    override fun mapToEntity(domain: FridgeStatus) = FridgeStatusEntity(
        temperature = domain.temperature,
        drawerData = domain.drawerData.map {
            DrawerInfo(
                id = it.id,
                name = it.name,
                lock = !it.isUnlocked,
                camera = it.isCameraSupported
            )
        }
    )
}