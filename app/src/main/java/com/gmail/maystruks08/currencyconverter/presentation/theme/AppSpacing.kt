package com.gmail.maystruks08.currencyconverter.presentation.theme

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class AppSpacing(
    val default: Dp = 0.dp,
    val extraSmall: Dp = 4.dp,
    val small: Dp = 8.dp,
    val medium: Dp = 16.dp,
    val large: Dp = 32.dp,
    val extraLarge: Dp = 64.dp,
)

val LocalSpacing = compositionLocalOf { AppSpacing() }

val Spacing: AppSpacing
    @Composable
    @ReadOnlyComposable
    get() = LocalSpacing.current

@Composable
@NonRestartableComposable
fun ExtraSmallSpacer(horizontal: Boolean = true, vertical: Boolean = true) {
    Spacer(
        modifier = Modifier
            .width(if (horizontal) Spacing.extraSmall else 0.dp)
            .height(if (vertical) Spacing.extraSmall else 0.dp)
    )
}

@Composable
@NonRestartableComposable
fun SmallSpacer(horizontal: Boolean = true, vertical: Boolean = true) {
    Spacer(
        modifier = Modifier
            .width(if (horizontal) Spacing.small else 0.dp)
            .height(if (vertical) Spacing.small else 0.dp)
    )
}

@Composable
@NonRestartableComposable
fun MediumSpacer(horizontal: Boolean = true, vertical: Boolean = true) {
    Spacer(
        modifier = Modifier
            .width(if (horizontal) Spacing.medium else 0.dp)
            .height(if (vertical) Spacing.medium else 0.dp)
    )
}

@Composable
@NonRestartableComposable
fun LargeSpacer(horizontal: Boolean = true, vertical: Boolean = true) {
    Spacer(
        modifier = Modifier
            .width(if (horizontal) Spacing.large else 0.dp)
            .height(if (vertical) Spacing.large else 0.dp)
    )
}

@Composable
@NonRestartableComposable
fun ExtraLargeSpacer(horizontal: Boolean = true, vertical: Boolean = true) {
    Spacer(
        modifier = Modifier
            .width(if (horizontal) Spacing.extraLarge else 0.dp)
            .height(if (vertical) Spacing.extraLarge else 0.dp)
    )
}

