package com.example.ioannisfanariotis

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface APIInterface {

    @GET("{year}/GR")
    fun getYear(@Path("year") year: String): Call<List<RequestItem>>
}