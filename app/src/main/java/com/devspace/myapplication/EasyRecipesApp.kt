package com.devspace.myapplication

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun EasyRecipesApp() {
    val navController = rememberNavController();
    NavHost(navController = navController, startDestination = "welcome") {
        composable(route = "welcome") {
            WelcomeScreen(navController);
        }

        composable(route = "main_screen") {
            MainScreen(navController);
        }

        composable(
            route = "recipeDetail" + "/{recipeId}",
            arguments = listOf(navArgument("recipeId") {
                type = NavType.IntType;
            })
        ) { navBackStackEntry ->
            val recipeId = requireNotNull(navBackStackEntry.arguments?.getInt("recipeId"));
            RecipeDetailScreen(recipeId, navController);
        }

        composable(
            route = "searchRecipes" + "/{query}",
            arguments = listOf(navArgument("query") {
                type = NavType.StringType
            })
        ){ navBackStackEntry ->
            val query = requireNotNull(navBackStackEntry.arguments?.getString("query"));
            SearchRecipeScreen(query, navController);
        }
    }
}