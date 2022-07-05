package com.example.darren.borutoapp.presentation.screens.search

import android.util.Log
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.darren.borutoapp.presentation.common.ListContent
import com.example.darren.borutoapp.presentation.common.ListContentGoat
import com.example.darren.borutoapp.ui.theme.statusBarColor
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun SearchScreen(
    navController: NavHostController,
    searchViewModel: SearchViewModel = hiltViewModel()
){
    val searchQuery by searchViewModel.searchQuery
    val heroes = searchViewModel.searchedHeroes.collectAsLazyPagingItems()
    val goats = searchViewModel.searchGoats.collectAsLazyPagingItems()
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = MaterialTheme.colors.statusBarColor
    )



    Scaffold(
        topBar = {
            SearchTopBar(
                text = searchQuery,
                onTextChange = {
                     searchViewModel.updateSearchQuery(it)
                },
                onSearchClicked = {
                    searchViewModel.searchGoats(it)
                },
                onCloseClicked = {
                    navController.popBackStack()
                }
            )
        }
    ) {
        ListContentGoat(goats = goats, navController = navController)
    }
}