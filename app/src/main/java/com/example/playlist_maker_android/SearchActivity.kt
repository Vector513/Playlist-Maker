package com.example.playlist_maker_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.playlist_maker_android.ui.theme.AppColors
import com.example.playlist_maker_android.ui.theme.AppTypography
import com.example.playlist_maker_android.ui.theme.Dimensions
import com.example.playlist_maker_android.ui.theme.PlaylistmakerandroidTheme

class SearchActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlaylistmakerandroidTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .background(AppColors.White)
                            .padding(innerPadding)
                    ) {
                        SearchPanelHeader()
                        SearchBar()
                    }
                }
            }
        }
    }
}

@Composable
private fun SearchPanelHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimensions.SearchPanelHeaderHeight)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(start = 4.dp, top = 4.dp, bottom = 4.dp)
        ) {
            ArrowBackButton()
            Text(
                text = stringResource(R.string.search_panel_header_text),
                color = AppColors.Black,
                style = AppTypography.PanelHeaderText,
                modifier = Modifier
                    .padding(start = 12.dp, top = 10.dp, bottom = 12.dp)
            )
        }
    }
}

@Preview(device = "spec:width=411dp,height=891dp", showSystemUi = true, showBackground = true)
@Composable
private fun SearchPanelHeaderPreview() {
    SearchPanelHeader()
}

@Composable
private fun ArrowBackButton() {
    Button(
        onClick = {},
        shape = RectangleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = AppColors.White,
        ),
        modifier = Modifier.size(Dimensions.BoxSize),
        contentPadding = PaddingValues(0.dp)
    ) {
            Box(
                modifier = Modifier
                    .size(Dimensions.IconSize),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_arrow_back_light_mode),
                    contentDescription = null,
                    modifier = Modifier.size(Dimensions.ArrowBackIconSize)
                )
            }
    }
}

@Preview(device = "spec:width=411dp,height=891dp", showSystemUi = true, showBackground = true)
@Composable
private fun ArrowBackButtonPreview() {
    ArrowBackButton()
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
                    color = AppColors.LightGray,
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
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    color = AppColors.Black,
                    lineHeight = 20.sp
                ),
                keyboardOptions = KeyboardOptions.Default,
                cursorBrush = SolidColor(AppColors.PrimaryBlue),

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
                                painter = painterResource(R.drawable.ic_search_bar_light_mode),
                                contentDescription = null,
                                modifier = Modifier.size(Dimensions.SearchBarIconSize)
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
                                    color = AppColors.Gray,
                                    fontSize = 16.sp
                                )
                            }
                            innerTextField()
                        }

                        if (textState.text.isNotEmpty()) {
                            IconButton(onClick = { textState.clearText() }) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Очистить",
                                    tint = AppColors.Gray,
                                    modifier = Modifier.size(Dimensions.ClearIconSize)
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