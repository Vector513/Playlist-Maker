package com.example.playlist_maker_android

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import com.example.playlist_maker_android.ui.theme.Dimensions

@Composable
internal fun BaseButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val baseModifier = Modifier
        .fillMaxWidth()
        .height(Dimensions.ButtonHeight)

    Button(
        onClick = onClick,
        modifier = baseModifier.then(modifier),
        shape = RectangleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = com.example.playlist_maker_android.ui.theme.AppColors.White
        )
    ) {
        content()
    }
}
