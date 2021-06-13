package com.example.shualeduri.api

import com.example.shualeduri.model.IpModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface Request {

    companion object {
        private const val KEY = "2c7863a34f2f06"
    }

    @GET("{ipaddress}?token=$KEY")
    suspend fun getPost(@Path("ipaddress") ipAddress: String): Response<IpModel>


}