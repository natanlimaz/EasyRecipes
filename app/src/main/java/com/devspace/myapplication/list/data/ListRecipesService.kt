package com.devspace.myapplication.list.data

import com.devspace.myapplication.common.model.RecipeResponse
import retrofit2.Response
import retrofit2.http.GET

interface ListRecipesService {

    @GET("random?number=20")
    suspend fun getRandomRecipes(): Response<RecipeResponse>

}