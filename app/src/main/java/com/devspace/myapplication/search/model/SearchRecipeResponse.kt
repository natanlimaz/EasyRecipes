package com.devspace.myapplication.search.model

data class SearchRecipeResponse (
    val results: List<SearchRecipeDto>
)

data class SearchRecipeDto (
    val id: Int,
    val title: String,
    val image: String
)