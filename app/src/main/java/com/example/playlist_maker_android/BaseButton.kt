package com.example.playlist_maker_android

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp

@Composable
internal fun BaseButton(
    onClick: () -> Unit,
    modifier: Modifier,
    content: @Composable () -> Unit
) {
    val baseModifier = Modifier
        .fillMaxWidth()
        .height(66.dp)
//      .padding(horizontal = 12.dp, vertical = 18.dp)

    Button(
        onClick = onClick,
        modifier = baseModifier.then(modifier),
        shape = RectangleShape,
        colors = ButtonColors(
            Color.White,
            Color.Blue,
            Color.Green,
            Color.Magenta
        )
    ) {
        content()
    }
}
