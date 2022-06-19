package com.gmail.maystruks08.currencyconverter.presentation.ui.home.views

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.gmail.maystruks08.currencyconverter.data.memory.DefaultData
import com.gmail.maystruks08.currencyconverter.presentation.theme.Spacing
import com.gmail.maystruks08.currencyconverter.presentation.ui.home.models.CurrencyItemView


@ExperimentalFoundationApi
@Composable
@ExperimentalMaterialApi
fun CurrencyItemsView(
    currencyItems: List<CurrencyItemView>,
    isSwipeEnabled: Boolean,
    filterQuery: MutableState<String?>,
    onAddButtonClicked: (currencyItemView: CurrencyItemView) -> Unit,
    onRemoveButtonClicked: (currencyItemView: CurrencyItemView) -> Unit,
    navigateToConvertCurrency: (currencyItemView: CurrencyItemView) -> Unit,
) {
    val viewItemsStateList = remember(currencyItems) {
        currencyItems.toMutableStateList()
    }

    LazyColumn(
        modifier = Modifier.fillMaxHeight(),
        contentPadding = PaddingValues(Spacing.medium),
        verticalArrangement = Arrangement.spacedBy(Spacing.medium)
    ) {

        when {
            !filterQuery.value.isNullOrEmpty() -> {
                currencyItems.forEach { currencyItemView ->
                    filterQuery.value?.let {
                        if (!currencyItemView.code.contains(it, true)) return@forEach
                        item(key = { it.hashCode() }) {
                            if (isSwipeEnabled) {
                                SwipeToRemove(
                                    currencyItemView,
                                    viewItemsStateList,
                                    onRemoveButtonClicked,
                                    navigateToConvertCurrency
                                )
                            } else {
                                CurrencyItemView(
                                    itemView = currencyItemView,
                                    onAddButtonClicked = onAddButtonClicked,
                                    onRemoveButtonClicked = onRemoveButtonClicked,
                                    navigateToConvertCurrency = navigateToConvertCurrency
                                )
                            }
                        }
                    }
                }
            }
            else -> {
                items(
                    items = viewItemsStateList,
                    key = { listItem -> listItem.hashCode() }
                ) { item ->
                    if (isSwipeEnabled) {
                        SwipeToRemove(
                            item,
                            viewItemsStateList,
                            onRemoveButtonClicked,
                            navigateToConvertCurrency
                        )
                    } else {
                        CurrencyItemView(
                            itemView = item,
                            onAddButtonClicked = onAddButtonClicked,
                            onRemoveButtonClicked = onRemoveButtonClicked,
                            navigateToConvertCurrency = navigateToConvertCurrency
                        )
                    }
                }
            }
        }
    }
}

@Composable
@ExperimentalMaterialApi
fun SwipeToRemove(
    currencyItemView: CurrencyItemView,
    viewItemsStateList: MutableList<CurrencyItemView>,
    onRemoveButtonClicked: (currencyItemView: CurrencyItemView) -> Unit,
    navigateToConvertCurrency: (currencyItemView: CurrencyItemView) -> Unit
) {
    val dismissState = rememberDismissState(confirmStateChange = {
        if (it == DismissValue.DismissedToStart) {
            viewItemsStateList.remove(currencyItemView)
            onRemoveButtonClicked.invoke(currencyItemView)
        }
        true
    })

    SwipeToDismiss(
        state = dismissState,
        modifier = Modifier.padding(vertical = Dp(1f)),
        directions = setOf(DismissDirection.EndToStart),
        dismissThresholds = { direction ->
            FractionalThreshold(
                if (direction == DismissDirection.EndToStart) 0.1f else 0.05f
            )
        },
        background = { SwipeBackground(dismissState) },
        dismissContent = {
            CurrencyItemView(
                itemView = currencyItemView,
                onAddButtonClicked = {},
                onRemoveButtonClicked = onRemoveButtonClicked,
                navigateToConvertCurrency = navigateToConvertCurrency
            )
        }
    )
}

@Composable
@ExperimentalMaterialApi
fun SwipeBackground(dismissState: DismissState) {
    val color by animateColorAsState(
        when (dismissState.targetValue) {
            DismissValue.Default -> White
            else -> Red
        }
    )
    val alignment = Alignment.CenterEnd
    val icon = Icons.Default.Delete

    val scale by animateFloatAsState(
        if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f
    )

    Box(
        Modifier
            .fillMaxSize()
            .background(color)
            .padding(horizontal = Dp(20f)),
        contentAlignment = alignment
    ) {
        Icon(
            icon,
            contentDescription = "Delete Icon",
            modifier = Modifier.scale(scale)
        )
    }
}


@ExperimentalFoundationApi
@Composable
@ExperimentalMaterialApi
@Preview(showBackground = true)
fun CurrencyItemsView() {
    CurrencyItemsView(
        currencyItems = DefaultData.currencyViews,
        isSwipeEnabled = true,
        onAddButtonClicked = { },
        onRemoveButtonClicked = { },
        navigateToConvertCurrency = { },
        filterQuery = remember { mutableStateOf(null) },
    )
}
