package com.devspace.myapplication.detail.data

import com.devspace.myapplication.common.model.RecipeDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RecipeDetailService {

    @GET("{id}/information?includeNutrition=false")
    suspend fun getRecipeById(@Path("id") id: Int): Response<RecipeDto>

}