package com.sergionaude.marvelheroes.data.api

import com.sergionaude.marvelheroes.BuildConfig
import com.sergionaude.marvelheroes.utils.getHash
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {
    const val BASE_URL = "http://gateway.marvel.com/v1/public/"

    private fun getRetrofit() : Retrofit{

        val ts = System.currentTimeMillis().toString()
        val apiSecret = BuildConfig.MARVEL_SECRET
        val apiKey = BuildConfig.MARVEL_KEY
        val hash = getHash(timeStamp = ts, privateKey = apiSecret, publicKey = apiKey)

        val clientInterceptor = Interceptor { chain ->
            var request: Request = chain.request()
            val url: HttpUrl = request.url.newBuilder()
                .addQueryParameter("ts", ts)
                .addQueryParameter("apikey", apiKey)
                .addQueryParameter("hash", hash)
                .build()
            request = request.newBuilder().url(url = url).build()
            chain.proceed(request = request)
        }

        val client = OkHttpClient.Builder().addInterceptor(interceptor = clientInterceptor).build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    val api : MarvelAPI = getRetrofit().create(MarvelAPI::class.java)
}