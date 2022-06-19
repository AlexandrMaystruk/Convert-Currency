package com.gmail.maystruks08.currencyconverter.presentation.ui.utils.animations


import androidx.compose.animation.core.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color


val simmerGreyEffectColors = listOf(
    Color.LightGray.copy(0.5f),
    Color.LightGray.copy(0.1f),
    Color.LightGray.copy(0.5f)
)

@Composable
fun ShimmerAnimation(
    simmerEffectColors: List<Color>,
    content: @Composable (brush: Brush) -> Unit
) {

    val transition = rememberInfiniteTransition()
    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1200,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )
    val brush = Brush.linearGradient(
        colors = simmerEffectColors,
        start = Offset(10f, 10f),
        end = Offset(translateAnim, translateAnim)
    )
    content.invoke(brush)
}