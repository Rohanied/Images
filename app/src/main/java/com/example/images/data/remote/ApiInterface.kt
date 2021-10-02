package com.example.images.data.remote

import com.example.images.beans.Response
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("api/")
    fun getImages(
        @Query("key") key: String,
        @Query("image_type") imageType: String,
        @Query("orientation") orientation: String
    ): Call<Response>

    companion object {

        private var BASE_URL = "https://pixabay.com/"

        fun create(): ApiInterface {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)

        }
    }
}