package com.devspace.myapplication

import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://api.spoonacular.com/recipes/";

object RetrofitClient {

    // Devo configurar dessa forma quando a api key tiver que ser passada na url
    private val httpClient: OkHttpClient
        get() {
            val clientBuilder = OkHttpClient.Builder()

            val token = BuildConfig.API_KEY

            clientBuilder.addInterceptor { chain ->
                val original: Request = chain.request()
                val request: Request.Builder = original.newBuilder()
                val originalHttpUrl = chain.request().url;
                val newUrl = originalHttpUrl.newBuilder()
                    .addQueryParameter("apiKey", token).build();
                chain.proceed(request.url(newUrl).build())
            }

            return clientBuilder.build();
        }

    val retrofitInstance: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}