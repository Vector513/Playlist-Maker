package com.example.playlist_maker_android

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.playlist_maker_android.ui.theme.Dimensions
import com.example.playlist_maker_android.ui.theme.PlaylistmakerandroidTheme

@Composable
internal fun SearchScreen(
    onBack: () -> Unit
) {
    PlaylistmakerandroidTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(innerPadding)
            ) {
                SearchPanelHeader(onBack)
                SearchBar()
            }
        }
    }
}


@Composable
private fun SearchPanelHeader(onBack: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimensions.PanelHeaderHeight)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(start = 4.dp, top = 4.dp, bottom = 4.dp)
        ) {
            ArrowBackButton(onBack)
            Text(
                text = stringResource(R.string.search_panel_header_text),
                modifier = Modifier
                    .padding(start = 12.dp),
                style = MaterialTheme.typography.titleLarge.copy(
                    color = MaterialTheme.colorScheme.onPrimary,
                    textAlign = TextAlign.Center
                ),
            )
        }
    }
}

@Preview(device = "spec:width=411dp,height=891dp", showSystemUi = true, showBackground = true)
@Composable
private fun SearchPanelHeaderPreview() {
    PlaylistmakerandroidTheme() {
        SearchPanelHeader {  }
    }
}

@Composable
private fun ArrowBackButton(onBack: () -> Unit) {
    Button(
        onClick = onBack,
        shape = RectangleShape,
//        colors = ButtonDefaults.buttonColors(
//            containerColor = MaterialTheme.colorScheme.primary,
//        ),
        modifier = Modifier.size(Dimensions.BoxSize),
        contentPadding = PaddingValues(0.dp)
    ) {
            Box(
                modifier = Modifier
                    .size(Dimensions.IconSize),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_arrow_back),
                    contentDescription = null,
                    modifier = Modifier.size(Dimensions.ArrowBackIconSize),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary)
                )
            }
    }
}

@Preview(device = "spec:width=411dp,height=891dp", showSystemUi = true, showBackground = true)
@Composable
private fun ArrowBackButtonPreview() {
    PlaylistmakerandroidTheme() {
        ArrowBackButton {  }
    }
}

@Composable
private fun SearchBar() {
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
            val textState = rememberTextFieldState()
            val placeholder = stringResource(R.string.search_bar_placeholder)

            BasicTextField(
                state = textState,
                modifier = Modifier
                    .fillMaxSize(),
                enabled = true,
                readOnly = false,
                lineLimits = TextFieldLineLimits.SingleLine,
                textStyle = MaterialTheme.typography.bodyLarge.copy(
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
                                contentDescription = null,
                                modifier = Modifier
                                    .size(Dimensions.SearchBarIconSize),
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
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        color = MaterialTheme.colorScheme.onSecondary
                                    )
                                )
                            }
                            innerTextField()
                        }

                        if (textState.text.isNotEmpty()) {
                            IconButton(onClick = { textState.clearText() }) {
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

@Preview(device = "spec:width=411dp,height=891dp", showSystemUi = true, showBackground = true)
@Composable
private fun SearchPreview() {
    SearchBar()
}