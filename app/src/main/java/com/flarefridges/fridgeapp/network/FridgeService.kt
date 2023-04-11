package com.flarefridges.fridgeapp.network

import com.flarefridges.fridgeapp.model.entity.DrawerDataEntity
import com.flarefridges.fridgeapp.model.entity.FridgeStatusEntity
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface FridgeApi {
    @GET("status")
    suspend fun getFridgeStatus(): Response<FridgeStatusEntity>

    @GET("unlock/{id}")
    suspend fun unlockFridge(@Path("id") id: Int): Response<Unit>

    @GET("lock/{id}")
    suspend fun lockFridge(@Path("id") id: Int): Response<Unit>

    @GET("camera")
    suspend fun getSnapshot(): Response<ResponseBody>

    @GET("groceries")
    suspend fun getGroceries(): Response<DrawerDataEntity>
}