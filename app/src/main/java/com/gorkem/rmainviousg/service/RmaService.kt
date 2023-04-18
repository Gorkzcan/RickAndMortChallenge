package com.gorkem.rmainviousg.service

import com.gorkem.rmainviousg.model.CharDetailModel
import com.gorkem.rmainviousg.model.LocationById
import com.gorkem.rmainviousg.model.LocationsModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RmaService {

    @GET("location")
    suspend fun getLocations(@Query("page") pageNumber: Int): Response<LocationsModel>


    @GET("location/{locationId}")
    suspend fun getALocationById(@Path("locationId") locationId: Int): Response<LocationById>

    @GET("character/{characterIds}")
    suspend fun getMultipleCharacter(@Path("characterIds") characterIds: ArrayList<Int>): Response<ArrayList<CharDetailModel>>

}