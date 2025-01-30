package com.devspace.myapplication.search.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.devspace.myapplication.common.data.RetrofitClient
import com.devspace.myapplication.search.data.SearchRecipeService
import com.devspace.myapplication.search.model.SearchRecipeDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchRecipeViewModel(
    private val searchRecipeService: SearchRecipeService
) : ViewModel() {

    private val _uiRecipes = MutableStateFlow<List<SearchRecipeDto>>(emptyList());
    val uiRecipes: StateFlow<List<SearchRecipeDto>> = _uiRecipes;

    fun fetchSearchRecipes(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = searchRecipeService.getSearchRecipes(query);
            if(response.isSuccessful) {
                val recipes = response.body()?.results;
                if(recipes != null) {
                    _uiRecipes.value = recipes
                }
            }
            else {
                Log.d("SearchRecipeViewModel", "Request error :: ${response.errorBody().toString()}")
            }
        }
    }

    companion object {
        val Factory : ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val searchService = RetrofitClient.retrofitInstance.create(SearchRecipeService::class.java);
                return SearchRecipeViewModel(searchService) as T
            }
        }
    }
}