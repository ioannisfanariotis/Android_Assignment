package com.example.ioannisfanariotis.network

import com.example.ioannisfanariotis.models.RequestItem
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface APIInterface {

    @GET("{year}/GR")
    fun getYear(@Path("year") year: String): Call<List<RequestItem>>

    companion object {
        var BASE_URL = "https://date.nager.at/api/v2/publicholidays/"
        private var retrofitService: APIInterface? = null

        fun getInstance(): APIInterface { //creates a REST adapter that points nager.date
            if (retrofitService == null){
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(APIInterface::class.java)
            }
            return retrofitService!!
        }
    }
}