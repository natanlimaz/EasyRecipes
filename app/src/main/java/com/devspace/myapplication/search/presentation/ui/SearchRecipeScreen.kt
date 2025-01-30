package com.devspace.myapplication.search.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.devspace.myapplication.search.model.SearchRecipeDto
import com.devspace.myapplication.search.presentation.SearchRecipeViewModel

@Composable
fun SearchRecipeScreen(
    query: String,
    navController: NavHostController,
    viewModel: SearchRecipeViewModel
) {
    val searchedRecipes by viewModel.uiRecipes.collectAsState();
    viewModel.fetchSearchRecipes(query);

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
                text = query,
                fontSize = 18.sp
            )
        }

        SearchRecipeContent(
            recipes = searchedRecipes,
        ) { recipeClicked ->
            navController.navigate("recipeDetail/${recipeClicked.id}")
        }
    }
}

@Composable
fun SearchRecipeContent(
    recipes: List<SearchRecipeDto>,
    onClick: (SearchRecipeDto) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        RecipeList(
            recipes = recipes,
            onClick = onClick
        )
    }
}

@Composable
fun RecipeList(
    recipes: List<SearchRecipeDto>,
    onClick: (SearchRecipeDto) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(recipes) {
            RecipeSearchItem(
                recipeSearchDto = it,
                onClick = onClick
            )
        }
    }
}

@Composable
fun RecipeSearchItem(
    recipeSearchDto: SearchRecipeDto,
    onClick: (SearchRecipeDto) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick.invoke(recipeSearchDto)
            }
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Crop,
            model = recipeSearchDto.image,
            contentDescription = "${recipeSearchDto.title} Poster image"
        )
        Text(
            modifier = Modifier.padding(8.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            text = recipeSearchDto.title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview
@Composable
fun SearchRecipePreview(modifier: Modifier = Modifier) {
    val recipes = listOf(
        SearchRecipeDto(
            1,
            "Titulo",
            "Imagem"
        )
    )
    SearchRecipeContent(
        recipes,
        onClick = {

        }
    )
}