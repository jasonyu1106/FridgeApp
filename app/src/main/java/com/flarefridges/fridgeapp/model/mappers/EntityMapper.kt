package com.flarefridges.fridgeapp.model.mappers

interface EntityMapper<DomainModel, EntityModel> {
    fun mapFromEntity(entity: EntityModel): DomainModel
    fun mapToEntity(domain: DomainModel): EntityModel
}