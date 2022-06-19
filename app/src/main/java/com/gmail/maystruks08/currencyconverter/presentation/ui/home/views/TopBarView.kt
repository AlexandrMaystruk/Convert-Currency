package com.gmail.maystruks08.currencyconverter.presentation.ui.home.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.gmail.maystruks08.currencyconverter.presentation.theme.White


@Composable
internal fun TopBarView(
    title: String,
    onSearchTextChanged: (filterQuery: String) -> Unit,
    onSearchClosed: () -> Unit
) {
    val isSearchMode = remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            ToolbarTitle(
                title,
                isSearchMode,
                onSearchTextChanged,
                onSearchClosed
            )
        },
        actions = {
            MenuActions(isSearchMode)
        },
        elevation = AppBarDefaults.TopAppBarElevation
    )
}

@Composable
internal fun ToolbarTitle(
    title: String,
    isSearchMode: MutableState<Boolean>,
    onSearchTextChanged: (filterQuery: String) -> Unit,
    onSearchClosed: () -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    val searchValue = remember { mutableStateOf("") }

    AnimatedVisibility(visible = isSearchMode.value) {
        TextField(
            value = searchValue.value,
            onValueChange = {
                searchValue.value = it
                onSearchTextChanged.invoke(searchValue.value)
            },
            leadingIcon = {
                IconButton(
                    onClick = {
                        searchValue.value = ""
                        isSearchMode.value = false
                        onSearchClosed.invoke()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft,
                        tint = White,
                        contentDescription = ""
                    )
                }
            },
            placeholder = {
                Text(
                    text = "Type query",
                    color = White.copy(ContentAlpha.disabled)
                )
            },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester = focusRequester)
                .background(Color.Transparent),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text
            )
        )
        SideEffect { focusRequester.requestFocus() }
    }
    AnimatedVisibility(visible = !isSearchMode.value) {
        Text(text = title)
    }
}

@Composable
internal fun MenuActions(
    isSearchMode: MutableState<Boolean>
) {
    val expanded = remember { mutableStateOf(false) }

    if (isSearchMode.value.not()) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Localized description",
                tint = White,
                modifier = Modifier.clickable {
                    isSearchMode.value = true
                }
            )
        }

    Box(
        Modifier.wrapContentSize(Alignment.TopEnd)
    ) {
        IconButton(onClick = { expanded.value = true }) {
            Icon(
                Icons.Filled.MoreVert,
                contentDescription = "Localized description"
            )
        }

        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
        ) {
            DropdownMenuItem(onClick = {
                expanded.value = false
            }) {
                Text("Menu action")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenTopBar_Preview() {
    TopBarView("All currencies", {}, {})
}