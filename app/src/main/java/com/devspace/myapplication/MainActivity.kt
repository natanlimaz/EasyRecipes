package com.devspace.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.devspace.myapplication.detail.presentation.RecipeDetailViewModel
import com.devspace.myapplication.list.presentation.ListRecipesViewModel
import com.devspace.myapplication.search.presentation.SearchRecipeViewModel
import com.devspace.myapplication.ui.theme.EasyRecipesTheme

class MainActivity : ComponentActivity() {

    private val listRecipesViewModel by viewModels<ListRecipesViewModel> { ListRecipesViewModel.Factory };
    private val recipeDetailViewModel by viewModels<RecipeDetailViewModel> { RecipeDetailViewModel.Factory };
    private val searchRecipeViewModel by viewModels<SearchRecipeViewModel> { SearchRecipeViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EasyRecipesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    EasyRecipesApp(
                        listRecipesViewModel = listRecipesViewModel,
                        recipeDetailViewModel = recipeDetailViewModel,
                        searchRecipeViewModel = searchRecipeViewModel
                    );
                }
            }
        }
    }
}