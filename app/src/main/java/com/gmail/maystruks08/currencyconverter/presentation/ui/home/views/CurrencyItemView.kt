package com.gmail.maystruks08.currencyconverter.presentation.ui.home.views


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gmail.maystruks08.currencyconverter.data.memory.DefaultData
import com.gmail.maystruks08.currencyconverter.presentation.theme.*
import com.gmail.maystruks08.currencyconverter.presentation.ui.home.models.ActionButton
import com.gmail.maystruks08.currencyconverter.presentation.ui.home.models.CurrencyItemView
import com.gmail.maystruks08.currencyconverter.presentation.ui.utils.commonviews.LineSpacer

private val itemCardHeight = 60.dp

@Composable
@ExperimentalMaterialApi
fun CurrencyItemView(
    itemView: CurrencyItemView,
    navigateToConvertCurrency: (currencyItemView: CurrencyItemView) -> Unit,
    onAddButtonClicked: (currencyItemView: CurrencyItemView) -> Unit,
    onRemoveButtonClicked: (currencyItemView: CurrencyItemView) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navigateToConvertCurrency.invoke(itemView) }
            .background(MaterialTheme.colors.background)
            .size(itemCardHeight),
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            MediumSpacer()
            Text(
                modifier = Modifier.weight(2.5f),
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.W900)) {
                        append(itemView.code)
                    }
                }
            )
            if (itemView.actionButtonState != null) {
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        if (itemView.actionButtonState is ActionButton.Add) {
                            onAddButtonClicked.invoke(itemView)
                        } else {
                            onRemoveButtonClicked.invoke(itemView)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = getActionButtonColor(itemView.actionButtonState)
                    ),
                    shape = Shapes.large,
                ) {
                    Text(
                        text = getActionButtonText(itemView.actionButtonState),
                        style = Typography.button
                    )
                }
            }
        }
        LineSpacer()
    }
}


@Composable
fun ShimmerItem(brush: Brush) {
    Card(
        modifier = Modifier
            .padding(
                start = Spacing.medium,
                top = Spacing.medium,
                end = Spacing.medium,
                bottom = 0.dp
            ),
        elevation = Spacing.medium
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .size(itemCardHeight)
                .background(brush = brush)
        )
    }
}

private fun getActionButtonColor(buttonState: ActionButton): Color {
    return when (buttonState) {
        ActionButton.Add -> Green
        ActionButton.Remove -> Purple500
    }
}

private fun getActionButtonText(buttonState: ActionButton?): String {
    return when (buttonState) {
        ActionButton.Add -> "Add"
        ActionButton.Remove -> "Remove"
        else -> ""
    }
}

@Composable
@ExperimentalMaterialApi
@Preview(showBackground = true)
fun CurrencyItemCardView_Preview() {
    CurrencyItemView(
        itemView = DefaultData.currencyViews.first(),
        {}, {}, {}
    )

}