package com.devspace.myapplication

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.devspace.myapplication.designsystem.HtmlText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun RecipeDetailScreen(
    recipeId: Int,
    navController: NavHostController
) {
    var recipeDto by remember {
        mutableStateOf<RecipeDto?>(null)
    }

    val apiService = RetrofitClient.retrofitInstance.create(ApiService::class.java);

    apiService.getRecipeById(recipeId).enqueue( object : Callback<RecipeDto> {
        override fun onResponse(call: Call<RecipeDto>, response: Response<RecipeDto>) {
            if(response.isSuccessful) {
                recipeDto = response.body();
            }
            else {
                Log.d("RecipeDetailScreen", "Request error :: ${response.errorBody()}")
            }
        }

        override fun onFailure(call: Call<RecipeDto>, t: Throwable) {
            Log.d("RecipeDetailScreen", "Network error :: ${t.message}")
        }

    });

    recipeDto?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick ={
                        navController.popBackStack();
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription ="Back button"
                    )
                }

                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = it.title,
                    fontSize = 18.sp
                )
            }

            RecipeDetailContent(it)
        }
    }

}

@Composable
fun RecipeDetailContent(
    recipe: RecipeDto
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AsyncImage(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.Crop,
            model = recipe.image,
            contentDescription = "${recipe.title} Poster image"
        )
        HtmlText(
            modifier = Modifier.padding(8.dp),
            text = recipe.summary,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeDetailPreview(modifier: Modifier = Modifier) {
    val recipe: RecipeDto = RecipeDto(
        id = 123,
        title = "Titulo",
        summary = "Description is Here, Description is Here,Description is Here,Description is Here,Description is Here,Description is Here,Description is Here,Description is Here,Description is Here,Description is Here,Description is Here,Description is Here,Description is Here,Description is Here,",
        image = "imagem"
    )
    RecipeDetailContent(recipe)
}