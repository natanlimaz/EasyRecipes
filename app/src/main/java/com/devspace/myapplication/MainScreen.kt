package com.devspace.myapplication

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.devspace.myapplication.designsystem.HtmlText
import com.devspace.myapplication.designsystem.SearchBar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun MainScreen(
    navController: NavHostController
) {
    var randomRecipes by remember {
        mutableStateOf<List<RecipeDto>>(emptyList())
    }

    val apiService = RetrofitClient.retrofitInstance.create(ApiService::class.java);

    LaunchedEffect(Unit) {
        val callRandomRecipes = apiService.getRandomRecipes();

        callRandomRecipes.enqueue(object : Callback<RecipeResponse> {
            override fun onResponse(call: Call<RecipeResponse>, response: Response<RecipeResponse>) {
                if(response.isSuccessful) {
                    val recipes = response.body()?.recipes;
                    if(recipes !== null) {
                        randomRecipes = recipes;
                    }
                }
                else {
                    Log.d("MainScreen", "Request error :: ${response.errorBody().toString()}")
                }
            }

            override fun onFailure(call: Call<RecipeResponse>, t: Throwable) {
                Log.d("MainScreen", "Network error :: ${t.message}")
            }

        })
    }

    MainScreenContent(
        recipes = randomRecipes,
        onSearchClicked = { query ->
            val tempQuery = query.trim();
            if (tempQuery.isNotEmpty()) {
                navController.navigate(route = "searchRecipes/$tempQuery")
            }
        },
        onClick = { recipeClicked ->
            navController.navigate(route = "recipeDetail/${recipeClicked.id}");
        }
    )
}

@Composable
fun MainScreenContent(
    recipes: List<RecipeDto>,
    onClick: (RecipeDto) -> Unit,
    onSearchClicked: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        var query by remember { mutableStateOf("") }
        SearchSession(
            label = "Find best",
            query = query,
            onValueChange = { newValue ->
                query = newValue
            },
            onSearchClicked = onSearchClicked
        )

        Text(
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            text = "Recipes"
        )

        RecipeList(
            recipes = recipes,
            onClick = onClick
        )

    }
}

@Composable
fun SearchSession(
    label: String,
    query: String,
    onValueChange: (String) -> Unit,
    onSearchClicked: (String) -> Unit
) {
    Text(
        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        text = label
    )

    SearchBar(
        query = query,
        placeHolder = "Search recipes",
        onValueChange = onValueChange,
        onSearchClicked = {
            onSearchClicked.invoke(query)
        }
    )
}

@Composable
fun RecipeList(
    recipes: List<RecipeDto>,
    onClick: (RecipeDto) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(recipes) {
            RecipeItem(
                recipeDto = it,
                onClick = onClick
            )
        }
    }
}

@Composable
fun RecipeItem(
    recipeDto: RecipeDto,
    onClick: (RecipeDto) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick.invoke(recipeDto)
            }
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Crop,
            model = recipeDto.image,
            contentDescription = "${recipeDto.title} Poster image"
        )
        Text(
            modifier = Modifier.padding(8.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            text = recipeDto.title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        HtmlText(
            modifier = Modifier.padding(start = 8.dp, end = 8.dp),
            text = recipeDto.summary,
            maxLine = 3
        )

    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview(modifier: Modifier = Modifier) {
    val recipes = listOf(
        RecipeDto(
            1,
            "Titulo",
            "Imagem",
            "asdasdjhabsdjhbashjkdba daskhjd asjbd asbdkbn askdbnaksj bdkjasbd kjasbkdbnaskd bnkajsb dkaskhdb ashifb asasd kjasbd kjasbkdbnaskd bnkajsb dkaskhdb ashifb asasd",
        )
    )
    MainScreenContent(
        recipes,
        onClick = {

        },
        onSearchClicked = {

        }
    )
}