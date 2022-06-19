package com.gmail.maystruks08.currencyconverter.presentation.ui.utils.commonviews

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable


@Composable
fun BackButtonView(onBackButtonClicked: () -> Unit) {
    IconButton(
        onClick = onBackButtonClicked
    ) {
        Icon(
            imageVector = Icons.Default.KeyboardArrowLeft,
            contentDescription = ""
        )
    }
}
