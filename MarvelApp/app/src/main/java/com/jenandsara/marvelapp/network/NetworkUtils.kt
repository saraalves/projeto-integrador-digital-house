package com.jenandsara.marvelapp.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkUtils {
    companion object {
        private const val BASE_URL = "https://gateway.marvel.com/"

        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss-SSSS").create()

        fun getRetrofitInstance(): Retrofit {
            val client = OkHttpClient
                .Builder()
                .addInterceptor(NetworkInterceptor())
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
        }
    }
}