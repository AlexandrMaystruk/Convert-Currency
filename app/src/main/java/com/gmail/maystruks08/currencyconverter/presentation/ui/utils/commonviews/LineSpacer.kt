package com.gmail.maystruks08.currencyconverter.presentation.ui.utils.commonviews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.gmail.maystruks08.currencyconverter.presentation.theme.Shapes

@Composable
fun LineSpacer() {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(0.5.dp)
            .background(Color.Black.copy(alpha = 0.2f), Shapes.large)
    )
}