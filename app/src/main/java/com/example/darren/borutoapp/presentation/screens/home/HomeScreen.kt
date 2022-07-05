package com.example.darren.borutoapp.presentation.screens.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.darren.borutoapp.navigation.Screen
import com.example.darren.borutoapp.presentation.common.ListContent
import com.example.darren.borutoapp.presentation.common.ListContentGoat
import com.example.darren.borutoapp.presentation.components.RatingWidget
import com.example.darren.borutoapp.ui.theme.LARGE_PADDING
import com.example.darren.borutoapp.ui.theme.statusBarColor
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun HomeScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel()
){
    val allHeroes = homeViewModel.getAllHeroes.collectAsLazyPagingItems()
    val allGoats = homeViewModel.getAllGoats.collectAsLazyPagingItems()

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = MaterialTheme.colors.statusBarColor
    )

    Scaffold(
        topBar = {
            HomeTopBar{
                navController.navigate(Screen.Search.route)
            }
        },
        content = {
//            ListContent(
//                heroes = allHeroes,
//                navController = navController
//            )
            ListContentGoat(
                goats = allGoats,
                navController = navController
            )
        }
    )
}