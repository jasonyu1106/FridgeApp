package com.flarefridges.fridgeapp.di

import com.flarefridges.fridgeapp.model.mappers.DrawerDataMapper
import com.flarefridges.fridgeapp.model.mappers.FridgeStatusMapper
import com.flarefridges.fridgeapp.network.FridgeDataSource
import com.flarefridges.fridgeapp.repository.FridgeRepository
import com.flarefridges.fridgeapp.repository.FridgeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun provideFridgeRepository(
        dataSource: FridgeDataSource,
        fridgeStatusMapper: FridgeStatusMapper,
        drawerDataMapper: DrawerDataMapper
    ): FridgeRepository {
        return FridgeRepositoryImpl(dataSource, fridgeStatusMapper, drawerDataMapper)
    }
}