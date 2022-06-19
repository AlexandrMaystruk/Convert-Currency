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
import androidx.compose.ui.unit.dp
import com.gmail.maystruks08.currencyconverter.presentation.theme.*
import com.gmail.maystruks08.currencyconverter.presentation.ui.convert_currency.models.ConvertCurrencyView
import com.gmail.maystruks08.currencyconverter.presentation.ui.utils.DecimalInputProcessor
import com.gmail.maystruks08.currencyconverter.presentation.ui.utils.DecimalInputProcessor.Companion.COMMAND_CLEAR
import com.gmail.maystruks08.currencyconverter.presentation.ui.utils.DecimalInputProcessor.Companion.COMMAND_OK
import com.gmail.maystruks08.currencyconverter.presentation.ui.utils.DecimalInputProcessor.Companion.KEY_0
import com.gmail.maystruks08.currencyconverter.presentation.ui.utils.DecimalInputProcessor.Companion.KEY_DOUBLE_ZERO
import com.gmail.maystruks08.currencyconverter.presentation.ui.utils.DecimalInputProcessor.Companion.KEY_POINT
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
            LineSpacer(0.2.dp)
            CurrencyRowItem(
                false,
                toCurrencyItemView.symbol,
                toCurrencyItemView.code,
                toCurrencyItemView.total
            )
            LineSpacer(0.2.dp)
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
            style = Typography.h3
        )

        if (isEditMode) {
            LargeSpacer()
            Column(
                modifier = Modifier.weight(3f),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = amountValue,
                    textAlign = TextAlign.End,
                    style = Typography.h3
                )
                LineSpacer()
            }
        } else {
            Text(
                text = amountValue,
                modifier = Modifier.weight(3f),
                textAlign = TextAlign.End,
                style = Typography.h3
            )
        }
    }
}


@Composable
fun CustomKeyboard(
    amountValue: MutableState<String>,
    onConvertCurrencyClicked: (amount: Double) -> Unit
) {
    val inputProcessor = remember { DecimalInputProcessor() }
    val onClick: (String) -> Unit = remember {
        { keyCode ->
            amountValue.value = inputProcessor.nextValue(keyCode).toString()
            when (keyCode) {
                COMMAND_CLEAR -> amountValue.value = ""
                COMMAND_OK -> onConvertCurrencyClicked.invoke(amountValue.value.toDouble())
                else -> Unit
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxHeight(0.45f)
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
                        number = KEY_0,
                        onClick = onClick,
                        modifier = Modifier.weight(2f)
                    )
                    NumberButton(
                        number = KEY_POINT,
                        onClick = onClick,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Column(modifier = Modifier.weight(1f)) {
                NumberButton(
                    number = COMMAND_CLEAR,
                    onClick = onClick,
                    modifier = Modifier.weight(1f)
                )
                NumberButton(
                    number = KEY_DOUBLE_ZERO,
                    onClick = onClick,
                    modifier = Modifier.weight(1f)
                )
                NumberButton(
                    number = COMMAND_OK,
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
        shape = Shapes.large,
        modifier = modifier
            .fillMaxSize()
            .padding(Spacing.extraSmall),
    ) {
        Text(
            text = number,
            style = Typography.h2
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