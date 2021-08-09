package com.dhopa.dhopaservice.network

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class ApiPostalCode {

    companion object {
        var BASE_URL = "https://omninos.xyz/Dhopa/api/Dhopa/"
        var retrofit: Retrofit? = null

        fun getClient1(): Retrofit? {
            val logging = HttpLoggingInterceptor { message -> Log.d("Data", "log: $message") }
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            val client: OkHttpClient = OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.MINUTES)
                .readTimeout(10, TimeUnit.MINUTES)
                .writeTimeout(10, TimeUnit.MINUTES)
                .build()

            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL1)
                    .client(client)
                    .addConverterFactory(
                        GsonConverterFactory.create()
                    )
                    .build()
            }
            return retrofit
        }
    }
}