package com.flarefridges.fridgeapp.notification

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.flarefridges.fridgeapp.repository.FridgeRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

const val EXPIRED_ITEMS = "EXPIRED_ITEMS"

@HiltWorker
class ExpiryWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val fridgeRepository: FridgeRepository
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        return try {
            var expiredItems = 0
            fridgeRepository.fetchDrawerList()?.let { drawerData ->
                drawerData.items.forEach { item ->
                    expiredItems += item.rottenQuantity
                }
            }
            val outputData = Data.Builder()
                .putInt(EXPIRED_ITEMS, expiredItems)
                .build()
            Result.success(outputData)
        } catch (e: Exception) {
            Result.failure()
        }
    }
}