package com.example.playlist_maker_android.ui.search.components

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.setTextAndSelectAll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.playlist_maker_android.R
import com.example.playlist_maker_android.data.Word
import com.example.playlist_maker_android.ui.theme.Dimensions

@Composable
fun SearchBar(
    textState: TextFieldState,
    historyList: List<Word>,
    onSearchClick: () -> Unit,
    onClearClick: () -> Unit,
    onHistoryClick: (Word) -> Unit
) {
    var isFocused by remember { mutableStateOf(false) }
    val placeholder = stringResource(R.string.search_bar_placeholder)

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .height(36.dp)
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.secondary,
                    shape = RoundedCornerShape(Dimensions.SearchBarRadius)
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {

            BasicTextField(
                state = textState,
                modifier = Modifier
                    .fillMaxSize()
                    .onFocusChanged { isFocused = it.isFocused },
                lineLimits = TextFieldLineLimits.SingleLine,
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onTertiary
                ),
                cursorBrush = SolidColor(MaterialTheme.colorScheme.background),
                keyboardOptions = KeyboardOptions.Default,
                decorator = { innerTextField ->

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Icon(
                            painter = painterResource(R.drawable.ic_search_bar),
                            contentDescription = "Поиск",
                            modifier = Modifier
                                .padding(8.dp)
                                .size(Dimensions.SearchBarIconSize)
                                .clickable { onSearchClick() },
                            tint = MaterialTheme.colorScheme.onSecondary
                        )

                        Box(
                            modifier = Modifier.weight(1f),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            if (textState.text.isEmpty()) {
                                Text(
                                    text = placeholder,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = MaterialTheme.colorScheme.onSecondary
                                    )
                                )
                            }
                            innerTextField()
                        }

                        if (textState.text.isNotEmpty()) {
                            IconButton(
                                onClick = onClearClick
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Очистить",
                                    tint = MaterialTheme.colorScheme.onSecondary,
                                    modifier = Modifier.size(Dimensions.ClearIconSize)
                                )
                            }
                        }
                    }
                }
            )
        }

        AnimatedVisibility(
            visible = isFocused &&
                    textState.text.isEmpty() &&
                    historyList.isNotEmpty()
        ) {
            HistoryRequests(
                historyList = historyList,
                onClick = { word ->
                    textState.setTextAndSelectAll(word.word)
                    onHistoryClick(word)
                }
            )
        }
    }
}


//@Preview(device = "spec:width=411dp,height=891dp", showSystemUi = true, showBackground = true)
//@Composable
//private fun SearchPreview() {
//    SearchBar()
//}
