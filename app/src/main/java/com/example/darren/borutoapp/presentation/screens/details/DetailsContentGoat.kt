package com.example.darren.borutoapp.presentation.screens.details

import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.darren.borutoapp.R
import com.example.darren.borutoapp.domain.model.Goat
import com.example.darren.borutoapp.presentation.components.InfoBox
import com.example.darren.borutoapp.presentation.components.OrderedList
import com.example.darren.borutoapp.ui.theme.*
import com.example.darren.borutoapp.util.Constants
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DetailsContentGoat(
    navController: NavHostController,
    selectedGoat: Goat?,
    colors: Map<String, String>
){
    var vibrant by remember { mutableStateOf("#000000") }
    var darkVibrant by remember { mutableStateOf("#000000") }
    var onDarkVibrant by remember { mutableStateOf("#ffffff") }

    LaunchedEffect(key1 = selectedGoat) {
        vibrant = colors["vibrant"]!!
        darkVibrant = colors["darkVibrant"]!!
        onDarkVibrant = colors["onDarkVibrant"]!!
    }

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = Color(android.graphics.Color.parseColor(darkVibrant))
    )

    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    )
    val currentSheetFraction = scaffoldState.currentSheetFraction

    val radiusAnim by animateDpAsState(
        targetValue =
        if (currentSheetFraction == 1f)
            EXTRA_LARGE_PADDING
        else
            EXPANDED_RADIUS_LEVEL
    )

    BottomSheetScaffold(
        sheetShape = RoundedCornerShape(
            topStart = radiusAnim,
            topEnd = radiusAnim
        ),
        scaffoldState = scaffoldState,
        sheetPeekHeight = MIN_SHEET_HEIGHT,
        sheetContent = {
            if (selectedGoat != null) {
                BottomSheetContentGoat(
                    selectedGoat = selectedGoat,
                    infoBoxIconColor = Color(android.graphics.Color.parseColor(vibrant)),
                    sheetBackgroundColor = Color(android.graphics.Color.parseColor(darkVibrant)),
                    contentColor = Color(android.graphics.Color.parseColor(onDarkVibrant))
                )
            }
        },
        content = {
            if (selectedGoat != null) {
                BackgroundContentGoat(
                    goatImage = selectedGoat.image,
                    imageFraction = currentSheetFraction,
                    onCloseClicked = {
                        navController.popBackStack()
                    },
                    backgroundColor = Color(android.graphics.Color.parseColor(darkVibrant))
                )
            }
        }
    )
}



@Composable
fun BottomSheetContentGoat(
    selectedGoat: Goat,
    infoBoxIconColor: Color = MaterialTheme.colors.primary,
    sheetBackgroundColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = MaterialTheme.colors.titleColor
) {
    Column(
        modifier = Modifier
            .background(sheetBackgroundColor)
            .padding(all = LARGE_PADDING)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = LARGE_PADDING),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .size(INFO_ICON_SIZE)
                    .weight(2f),
                painter = painterResource(id = R.drawable.ic_ball),
                contentDescription = "",
                tint = contentColor
            )
            Text(
                modifier = Modifier
                    .weight(8f),
                text = selectedGoat.name,
                color = contentColor,
                fontSize = MaterialTheme.typography.h4.fontSize,
                fontWeight = FontWeight.Bold
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = MEDIUM_PADDING),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            InfoBox(
                icon = painterResource(id = R.drawable.ic_ranking),
                iconColor = infoBoxIconColor,
                bigText = "${selectedGoat.ranking}",
                smallText = "Rank",
                textColor = contentColor
            )
            InfoBox(
                icon = painterResource(id = R.drawable.ic_champion),
                iconColor = infoBoxIconColor,
                bigText = selectedGoat.champ.toString(),
                smallText = "Champs",
                textColor = contentColor
            )
            InfoBox(
                icon = painterResource(id = R.drawable.ic_mvp),
                iconColor = infoBoxIconColor,
                bigText = selectedGoat.mvp.toString(),
                smallText = "MVPs",
                textColor = contentColor
            )
        }
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.about),
            color = contentColor,
            fontSize = MaterialTheme.typography.subtitle1.fontSize,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier
                .alpha(ContentAlpha.medium)
                .padding(bottom = MEDIUM_PADDING),
            text = selectedGoat.about,
            color = contentColor,
            fontSize = MaterialTheme.typography.body1.fontSize,
            //maxLines = Constants.ABOUT_TEXT_MAX_LINES
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val statsList = listOf(
                "PPG: ${selectedGoat.point}",
                "RPG: ${selectedGoat.rebound}",
                "APG: ${selectedGoat.point}"
            )
            OrderedList(
                title = "Stats",
                items = statsList,
                textColor = contentColor
            )
            OrderedList(
                title = "All Team",
                items = selectedGoat.allTeam,
                textColor = contentColor
            )

        }
    }
}

@Composable
fun BackgroundContentGoat(
    goatImage: String,
    imageFraction: Float,
    backgroundColor: Color = MaterialTheme.colors.surface,
    onCloseClicked: () -> Unit
){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(fraction = imageFraction + Constants.MIN_BACKGROUND_IMAGE_HEIGHT)
                .align(Alignment.TopStart),
            model = "${Constants.BASE_URL}${goatImage}",
            contentDescription = "",
            placeholder = painterResource(id = R.drawable.ic_placeholder),
            contentScale = ContentScale.Crop
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(
                modifier = Modifier.padding(SMALL_PADDING),
                onClick = { onCloseClicked() }
            ) {
                Icon(
                    modifier = Modifier.size(INFO_ICON_SIZE),
                    imageVector = Icons.Default.Close,
                    contentDescription = "",
                    tint = Color.White
                )

            }
        }

    }

}


@ExperimentalMaterialApi
val BottomSheetScaffoldState.currentSheetFractionGoat: Float
    get() {
        val fraction = bottomSheetState.progress.fraction
        val targetValue = bottomSheetState.targetValue
        val currentValue = bottomSheetState.currentValue

        Log.d("Fraction", fraction.toString())
        Log.d("Fraction Target", targetValue.toString())
        Log.d("Fraction Current", currentValue.toString())

        return when {
            currentValue == BottomSheetValue.Collapsed && targetValue == BottomSheetValue.Collapsed -> 1f
            currentValue == BottomSheetValue.Expanded && targetValue == BottomSheetValue.Expanded -> 0f
            currentValue == BottomSheetValue.Collapsed && targetValue == BottomSheetValue.Expanded -> 1f - fraction
            currentValue == BottomSheetValue.Expanded && targetValue == BottomSheetValue.Collapsed -> 0f + fraction
            else -> fraction
        }
    }