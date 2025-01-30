package com.devspace.myapplication.detail.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.devspace.myapplication.common.data.RetrofitClient
import com.devspace.myapplication.common.model.RecipeDto
import com.devspace.myapplication.detail.data.RecipeDetailService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RecipeDetailViewModel(
    private val recipeDetailService: RecipeDetailService
) : ViewModel() {

    private val _uiRecipe = MutableStateFlow<RecipeDto?>(null);
    val uiRecipe: StateFlow<RecipeDto?> = _uiRecipe;

    fun fetchRecipeById(recipeId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = recipeDetailService.getRecipeById(recipeId);
            if(response.isSuccessful) {
                _uiRecipe.value = response.body();
            }
            else {
                Log.d("RecipeDetailViewModel", "Request error :: ${response.errorBody()}")
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val detailService = RetrofitClient.retrofitInstance.create(RecipeDetailService::class.java);
                return RecipeDetailViewModel(detailService) as T
            }
        }
    }

}