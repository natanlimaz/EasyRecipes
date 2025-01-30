package com.devspace.myapplication.search.data

import com.devspace.myapplication.search.model.SearchRecipeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchRecipeService {

    @GET("complexSearch?")
    suspend fun getSearchRecipes(@Query("query") query: String): Response<SearchRecipeResponse>

}