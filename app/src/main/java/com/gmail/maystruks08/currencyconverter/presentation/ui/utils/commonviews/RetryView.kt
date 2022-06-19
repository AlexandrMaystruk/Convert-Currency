package com.gmail.maystruks08.currencyconverter.presentation.ui.utils.commonviews

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Error
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gmail.maystruks08.currencyconverter.presentation.theme.Spacing


@Composable
fun RetryView(
    title: String,
    icon: ImageVector,
    retryButtonClicked: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.medium),
            elevation = 4.dp
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Spacing.medium),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        alignment = Alignment.Center,
                        imageVector = icon,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.padding(Spacing.medium))
                    Text(
                        text = title,
                        textAlign = TextAlign.Center
                    )
                }
                Button(
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .padding(Spacing.medium)
                        .align(Alignment.CenterHorizontally)
                        .height(50.dp),
                    onClick = { retryButtonClicked.invoke() }) {
                    Text(text = "Retry")
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun RetryView_Preview() {
    RetryView("Get currency error =(", Icons.Rounded.Error) {}
}