package com.example.darren.borutoapp.presentation.common

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import com.example.darren.borutoapp.R
import com.example.darren.borutoapp.domain.model.Goat
import com.example.darren.borutoapp.domain.model.Hero
import com.example.darren.borutoapp.navigation.Screen
import com.example.darren.borutoapp.presentation.components.RatingWidget
import com.example.darren.borutoapp.presentation.components.ShimmerEffect
import com.example.darren.borutoapp.ui.theme.*
import com.example.darren.borutoapp.util.Constants

@Composable
fun ListContentGoat(
    goats: LazyPagingItems<Goat>,
    navController: NavHostController
){
    val result = handlePagingResultGoat(goats = goats)

    if (result){
        LazyColumn(
            contentPadding = PaddingValues(all = SMALL_PADDING),
            verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)
        ){
            items(
                items = goats,
                key = {
                        goat ->
                    goat.id
                }
            ){
                    goat ->
                goat?.let {
                    GoatItem(goat = it, navController = navController)
                }
            }
        }
    }

}

@Composable
fun GoatItem(
    goat: Goat,
    navController: NavHostController
){
    Box(
        modifier = Modifier
            .height(GOAT_ITEM_HEIGHT)
            .clickable {
                navController.navigate(Screen.Details.passHeroId(heroId = goat.id))
            },
        contentAlignment = Alignment.BottomStart
    ) {
        Surface(shape = RoundedCornerShape(size = LARGE_PADDING)){
            Surface(shape = RoundedCornerShape(size = LARGE_PADDING)) {
                AsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    model = "${Constants.BASE_URL}${goat.image}",
                    contentDescription ="",
                    placeholder = painterResource(id = R.drawable.ic_placeholder),
                    contentScale = ContentScale.Crop
                )
            }
        }

        Surface(
            modifier = Modifier
                .fillMaxHeight(0.4f)
                .fillMaxWidth(),
            color = Color.Black.copy(alpha = ContentAlpha.medium),
            shape = RoundedCornerShape(
                bottomStart = LARGE_PADDING,
                bottomEnd = LARGE_PADDING
            ),

        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = MEDIUM_PADDING)
            ) {
                Text(
                    text = goat.name,
                    color = MaterialTheme.colors.topAppBarContentColor,
                    fontSize = MaterialTheme.typography.h5.fontSize,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = goat.about,
                    color = Color.White.copy(alpha = ContentAlpha.medium),
                    fontSize = MaterialTheme.typography.subtitle1.fontSize,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    modifier = Modifier.padding(top = SMALL_PADDING),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RatingWidget(
                        modifier = Modifier.padding(end = SMALL_PADDING),
                        rating = goat.rating
                    )
                    Text(
                        text = "(${goat.rating})",
                        textAlign = TextAlign.Center,
                        color = Color.White.copy(alpha = ContentAlpha.medium)
                    )
                }
            }
        }
    }
    
}

@Composable
fun handlePagingResultGoat(
    goats: LazyPagingItems<Goat>
): Boolean {
    goats.apply {
        val error = when {
            loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
            loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
            loadState.append is LoadState.Error -> loadState.append as LoadState.Error
            else -> null
        }

        return when {

            loadState.refresh is LoadState.Loading -> {
                ShimmerEffect()
                false
            }
            error != null -> {
                EmptyScreenGoat(error = error, goats = goats)
                false
            }
            goats.itemCount < 1 -> {
                EmptyScreenGoat()
                false
            }
            else -> true
        }
    }
}