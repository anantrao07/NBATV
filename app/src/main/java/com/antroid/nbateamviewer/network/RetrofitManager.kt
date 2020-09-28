package com.antroid.nbateamviewer.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitManager private constructor() {
    private var okHttpClient: OkHttpClient? = null

    private fun retrofitInstanceBuilder(url: String): Retrofit {
        return Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
    }

    val getTeamsService: Retrofit
        get() {
            val TEAMS_BASE_URL = "https://raw.githubusercontent.com/"

            return retrofitInstanceBuilder(TEAMS_BASE_URL)
        }

    companion object {
        private var instance: RetrofitManager? = null

        @Synchronized
        fun getInstance(): RetrofitManager {
            if (instance == null) {
                instance = RetrofitManager()
            }
            return instance!!
        }
    }

    init {
        if (okHttpClient == null) {
            val timeout = 60
            okHttpClient = OkHttpClient.Builder()
                    .connectTimeout(timeout.toLong(), TimeUnit.SECONDS)
                    .writeTimeout(timeout.toLong(), TimeUnit.SECONDS)
                    .readTimeout(timeout.toLong(), TimeUnit.SECONDS)
                    .build()
        }
    }
}
