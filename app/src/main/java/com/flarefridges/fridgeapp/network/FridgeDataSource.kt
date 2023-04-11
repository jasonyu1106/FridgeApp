package com.flarefridges.fridgeapp.network

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.flarefridges.fridgeapp.model.entity.DrawerDataEntity
import com.flarefridges.fridgeapp.model.entity.FridgeStatusEntity
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException
import javax.inject.Inject


class FridgeDataSource @Inject constructor(
    private val fridgeService: FridgeApi
) {
    private val handler = CoroutineExceptionHandler { _, exception ->
        Log.e("FridgeDataSource", "Coroutine Exception: ${exception.message}")
    }

    suspend fun fetchFridgeStatus(): FridgeStatusEntity? =
        withContext(Dispatchers.IO + handler) {
            try {
                val response = fridgeService.getFridgeStatus()
                if (response.isSuccessful) {
                    response.body()
                } else {
                    null
                }
            } catch (e: SocketTimeoutException) {
                Log.e("FridgeDataSource", "SocketTimeoutException: ${e.message}")
                null
            } catch (e: Exception) {
                // Handle other exceptions
                Log.e("FridgeDataSource", "Exception: ${e.message}")
                null
            }
        }

    suspend fun unlockFridge(id: Int): Boolean =
        withContext(Dispatchers.IO + handler) {
            try {
                val response = fridgeService.unlockFridge(id)
                if (!response.isSuccessful) {
                    Log.e("FridgeDataSource", "Error code: ${response.code()}")
                    Log.e("FridgeDataSource", "Error body: ${response.errorBody()?.string()}")
                }
                response.isSuccessful
            } catch (e: SocketTimeoutException) {
                Log.e("FridgeDataSource", "SocketTimeoutException: ${e.message}")
                false
            } catch (e: Exception) {
                // Handle other exceptions
                Log.e("FridgeDataSource", "Exception: ${e.message}")
                false
            }
        }

    suspend fun lockFridge(id: Int): Boolean =
        withContext(Dispatchers.IO + handler) {
            try {
                val response = fridgeService.lockFridge(id)
                if (!response.isSuccessful) {
                    Log.e("FridgeDataSource", "Error code: ${response.code()}")
                    Log.e("FridgeDataSource", "Error body: ${response.errorBody()?.string()}")
                }
                response.isSuccessful
            } catch (e: SocketTimeoutException) {
                Log.e("FridgeDataSource", "SocketTimeoutException: ${e.message}")
                false
            } catch (e: Exception) {
                Log.e("FridgeDataSource", "Exception: ${e.message}")
                false
            }

        }

    suspend fun fetchSnapshot(): Bitmap? =
        withContext(Dispatchers.IO + handler) {
            try {
                val response = fridgeService.getSnapshot()
                if (response.isSuccessful) {
                    val imageBytes = response.body()?.bytes()
                    BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes?.size ?: 0)
                } else {
                    null
                }
            } catch (e: SocketTimeoutException) {
                Log.e("FridgeDataSource", "SocketTimeoutException: ${e.message}")
                null
            } catch (e: Exception) {
                Log.e("FridgeDataSource", "Exception: ${e.message}")
                null
            }
        }

    suspend fun fetchGroceries(): DrawerDataEntity? =
        withContext(Dispatchers.IO + handler) {
            try {
                val response = fridgeService.getGroceries()
                if (response.isSuccessful) {
                    response.body()
                } else {
                    null
                }
            } catch (e: SocketTimeoutException) {
                Log.e("FridgeDataSource", "SocketTimeoutException: ${e.message}")
                null
            } catch (e: Exception) {
                Log.e("FridgeDataSource", "Exception: ${e.message}")
                null
            }
        }
}