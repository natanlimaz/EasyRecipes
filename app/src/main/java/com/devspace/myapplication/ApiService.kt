package com.devspace.myapplication

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("random?number=20")
    fun getRandomRecipes(): Call<RecipeResponse>

    @GET("{id}/information?includeNutrition=false")
    fun getRecipeById(@Path("id") id: Int): Call<RecipeDto>

    @GET("complexSearch?")
    fun getSearchRecipes(@Query("query") query: String): Call<SearchRecipeResponse>
}