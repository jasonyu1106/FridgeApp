package com.flarefridges.fridgeapp.model.mappers

import com.flarefridges.fridgeapp.model.domain.DrawerData
import com.flarefridges.fridgeapp.model.domain.ItemData
import com.flarefridges.fridgeapp.model.entity.DrawerDataEntity
import com.flarefridges.fridgeapp.model.entity.Item
import javax.inject.Inject

class DrawerDataMapper @Inject constructor() : EntityMapper<DrawerData, DrawerDataEntity>  {
    override fun mapFromEntity(entity: DrawerDataEntity) = DrawerData(
        itemCount = entity.totalCount,
        items = entity.data.map {
            ItemData(
                name = it.name,
                freshQuantity = it.freshCount,
                rottenQuantity = it.rottenCount
            )
        }
    )

    override fun mapToEntity(domain: DrawerData) = DrawerDataEntity(
        totalCount = domain.itemCount,
        data = domain.items.map {
            Item(
                name = it.name,
                freshCount = it.freshQuantity,
                rottenCount = it.rottenQuantity
            )
        }
    )
}