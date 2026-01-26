package com.example.playlist_maker_android.ui.search.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.playlist_maker_android.R
import com.example.playlist_maker_android.ui.theme.Dimensions
import com.example.playlist_maker_android.ui.theme.PlaylistmakerandroidTheme

@Composable
fun ArrowBackButton(onBack: () -> Unit) {
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