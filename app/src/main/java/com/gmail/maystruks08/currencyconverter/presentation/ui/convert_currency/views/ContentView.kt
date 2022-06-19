package com.gmail.maystruks08.currencyconverter.presentation.ui.convert_currency.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.gmail.maystruks08.currencyconverter.presentation.theme.Grey
import com.gmail.maystruks08.currencyconverter.presentation.theme.Spacing
import com.gmail.maystruks08.currencyconverter.presentation.theme.Typography
import com.gmail.maystruks08.currencyconverter.presentation.ui.convert_currency.models.ConvertCurrencyView
import com.gmail.maystruks08.currencyconverter.presentation.ui.utils.commonviews.LineSpacer

@Composable
internal fun ContentView(
    fromCurrencyItemView: ConvertCurrencyView,
    toCurrencyItemView: ConvertCurrencyView,
    onConvertCurrencyClicked: (amount: Double) -> Unit
) {
    Column {
        val amountValue = remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.medium)
                .weight(1f)
        ) {
            CurrencyRowItem(
                true,
                fromCurrencyItemView.symbol,
                fromCurrencyItemView.code,
                amountValue.value
            )
            LineSpacer()
            CurrencyRowItem(
                false,
                toCurrencyItemView.symbol,
                toCurrencyItemView.code,
                toCurrencyItemView.total
            )
            LineSpacer()
        }
        CustomKeyboard(amountValue, onConvertCurrencyClicked)
    }
}

@Composable
fun CurrencyRowItem(
    isEditMode: Boolean,
    symbol: String,
    code: String,
    amountValue: String
) {
    Row(
        modifier = Modifier.padding(Spacing.medium),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = symbol,
            modifier = Modifier.weight(1f),
            style = Typography.h1
        )
        Text(
            text = code,
            modifier = Modifier.weight(1f),
            style = Typography.h2
        )

        Text(
            text = amountValue,
            modifier = Modifier.weight(3f),
            textAlign = TextAlign.End,
            style = Typography.h2
        )
    }
}


@Composable
fun CustomKeyboard(
    amountValue: MutableState<String>,
    onConvertCurrencyClicked: (amount: Double) -> Unit
) {
    //TODO move key code handling to different class and handle all cases
    val onClick: (String) -> Unit = remember {
        { keyCode ->
            if (keysArray.firstOrNull { it == keyCode } != null) {
                amountValue.value += keyCode
            }
            when (keyCode) {
                ACTION_KEY_CODE_CLEAR -> amountValue.value = ""
                KEY_CODE_DOT -> {
                    if (amountValue.value.isEmpty()) {
                        amountValue.value = keysArray[0] + keyCode
                    } else {
                        if (!amountValue.value.contains(KEY_CODE_DOT)) {
                            amountValue.value += keyCode
                        }
                    }
                }
                KEY_CODE_00 -> {
                    if (amountValue.value.contains(KEY_CODE_DOT)) {

                    }
                }
                ACTION_KEY_CODE_OK -> onConvertCurrencyClicked.invoke(amountValue.value.toDouble())
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxHeight(0.35f)
            .background(Grey)
    ) {
        Row(modifier = Modifier.padding(Spacing.medium)) {
            Column(modifier = Modifier.weight(3f)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    for (number in 7..9) {
                        NumberButton(
                            number = number.toString(),
                            onClick = onClick,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    for (number in 4..6) {
                        NumberButton(
                            number = number.toString(),
                            onClick = onClick,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    for (number in 1..3) {
                        NumberButton(
                            number = number.toString(),
                            onClick = onClick,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    NumberButton(
                        number = KEY_CODE_0,
                        onClick = onClick,
                        modifier = Modifier.weight(2f)
                    )
                    NumberButton(
                        number = KEY_CODE_DOT,
                        onClick = onClick,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Column(modifier = Modifier.weight(1f)) {
                NumberButton(
                    number = ACTION_KEY_CODE_CLEAR,
                    onClick = onClick,
                    modifier = Modifier.weight(1f)
                )
                NumberButton(
                    number = KEY_CODE_00,
                    onClick = onClick,
                    modifier = Modifier.weight(1f)
                )
                NumberButton(
                    number = ACTION_KEY_CODE_OK,
                    onClick = onClick,
                    modifier = Modifier.weight(2f)
                )
            }
        }
    }
}

@Composable
private fun NumberButton(
    number: String,
    onClick: (digit: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = { onClick(number) },
        modifier = modifier
            .fillMaxSize()
            .padding(Spacing.extraSmall),
    ) {
        Text(
            text = number,
            style = Typography.button
        )
    }
}


@Preview(showBackground = false)
@Composable
fun ContentView_Preview() {
    ContentView(
        ConvertCurrencyView("$", "100", "0"),
        ConvertCurrencyView("%", "200", "200")
    ) {}
}

val keysArray = Array(10) { "" }
    .apply {
        for (index in 0..9) this[index] = index.toString()
    }


const val KEY_CODE_DOT = "."
const val KEY_CODE_0 = "0"
const val KEY_CODE_00 = "00"
const val ACTION_KEY_CODE_CLEAR = "<"
const val ACTION_KEY_CODE_OK = "ok"