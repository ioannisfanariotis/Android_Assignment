package com.example.ioannisfanariotis

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Utils {
    var BASE_URL = "https://date.nager.at/api/v2/publicholidays/"

    fun getInstance():Retrofit{ //creates a REST adapter that points nager.date
        return Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
    }
}