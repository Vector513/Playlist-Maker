package com.example.playlist_maker_android.ui.search.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.playlist_maker_android.R
import com.example.playlist_maker_android.ui.theme.Dimensions

@Composable
fun SearchBar(
    textState: TextFieldState,
    onSearchClick: () -> Unit,
    onClearClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimensions.SearchBarHeight)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxSize()
                .background(
                    color = MaterialTheme.colorScheme.secondary,
                    shape = RoundedCornerShape(Dimensions.SearchBarRadius)
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val placeholder = stringResource(R.string.search_bar_placeholder)

            BasicTextField(
                state = textState,
                modifier = Modifier
                    .fillMaxSize(),
                enabled = true,
                readOnly = false,
                lineLimits = TextFieldLineLimits.SingleLine,
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onTertiary
                ),
                keyboardOptions = KeyboardOptions.Default,
                cursorBrush = SolidColor(MaterialTheme.colorScheme.background),

                decorator = { innerTextField ->
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(Dimensions.SearchBarWrapperSize),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(R.drawable.ic_search_bar),
                                contentDescription = "Search Icon",
                                modifier = Modifier
                                    .size(Dimensions.SearchBarIconSize)
                                    .clickable {
                                        onSearchClick()
                                    },
                                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSecondary)
                            )
                        }

                        Box(
                            modifier = Modifier
                                .weight(1f),
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
                            IconButton(onClick = {
                                onClearClick()
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Очистить",
                                    tint = MaterialTheme.colorScheme.onSecondary,
                                    modifier = Modifier
                                        .size(Dimensions.ClearIconSize)
                                )
                            }
                        }
                    }
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
