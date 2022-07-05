package com.example.darren.borutoapp.domain.model

import androidx.annotation.DrawableRes
import com.example.darren.borutoapp.R

sealed class OnBoardingPage(
    @DrawableRes
    val image: Int,
    val title: String,
    val description: String
){
    object First : OnBoardingPage(
        image = R.drawable.welcome,
        title = "Greetings",
        description = "Are you a NBA fan? Because if you are then we have a great news for you!"
    )

    object Second : OnBoardingPage(
        image = R.drawable.goat,
        title = "GOAT",
        description = "Have you heard of the \"GOAT\"? It means the greatest of all time. Who is your \"GOAT\"?"
    )

    object Third : OnBoardingPage(
        image = R.drawable.player,
        title = "Explore",
        description = "Check out the list of top 15 basketball player of all time and see if your favorite player is on it."
    )
}
