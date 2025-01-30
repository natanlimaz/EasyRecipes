package com.devspace.myapplication

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.devspace.myapplication.detail.presentation.RecipeDetailViewModel
import com.devspace.myapplication.detail.presentation.ui.RecipeDetailScreen
import com.devspace.myapplication.list.presentation.ListRecipesViewModel
import com.devspace.myapplication.list.presentation.ui.MainScreen
import com.devspace.myapplication.search.presentation.SearchRecipeViewModel
import com.devspace.myapplication.search.presentation.ui.SearchRecipeScreen
import com.devspace.myapplication.welcome.presentation.ui.WelcomeScreen

@Composable
fun EasyRecipesApp(
    listRecipesViewModel: ListRecipesViewModel,
    recipeDetailViewModel: RecipeDetailViewModel,
    searchRecipeViewModel: SearchRecipeViewModel
) {
    val navController = rememberNavController();
    NavHost(navController = navController, startDestination = "welcome") {
        composable(route = "welcome") {
            WelcomeScreen(navController);
        }

        composable(route = "main_screen") {
            MainScreen(navController, listRecipesViewModel);
        }

        composable(
            route = "recipeDetail" + "/{recipeId}",
            arguments = listOf(navArgument("recipeId") {
                type = NavType.IntType;
            })
        ) { navBackStackEntry ->
            val recipeId = requireNotNull(navBackStackEntry.arguments?.getInt("recipeId"));
            RecipeDetailScreen(recipeId, navController, recipeDetailViewModel);
        }

        composable(
            route = "searchRecipes" + "/{query}",
            arguments = listOf(navArgument("query") {
                type = NavType.StringType
            })
        ){ navBackStackEntry ->
            val query = requireNotNull(navBackStackEntry.arguments?.getString("query"));
            SearchRecipeScreen(query, navController, searchRecipeViewModel);
        }
    }
}