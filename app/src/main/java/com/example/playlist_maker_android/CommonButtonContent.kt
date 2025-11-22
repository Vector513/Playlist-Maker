package com.example.playlist_maker_android

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.example.playlist_maker_android.ui.theme.AppColors
import com.example.playlist_maker_android.ui.theme.AppTypography
import com.example.playlist_maker_android.ui.theme.Dimensions

@Composable
internal fun CommonButtonContent(
    iconResource: Painter,
    buttonText: String
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .background(AppColors.Transparent)
        ) {
            Box(
                modifier = Modifier
                    .padding(start = Dimensions.ButtonContentStartPadding)
                    .width(Dimensions.ButtonContentWidth)
                    .fillMaxHeight()
                    .background(AppColors.Transparent),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .width(Dimensions.IconSize)
                            .height(Dimensions.IconSize),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = iconResource,
                            contentDescription = null,
                            modifier = Modifier.size(Dimensions.IconSizeSmall)
                        )
                    }
                    Text(
                        text = buttonText,
                        style = AppTypography.ButtonText.copy(
                            textAlign = TextAlign.Center,
                            color = AppColors.Black
                        )
                    )
                }
            }

            Box(
                modifier = Modifier
                    .padding(end = Dimensions.ButtonContentEndPadding)
                    .width(Dimensions.IconSize)
                    .height(Dimensions.IconSize)
                    .background(AppColors.Transparent),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_arrow_forward_light_mode),
                    contentDescription = null,
                    modifier = Modifier
                        .width(Dimensions.ArrowIconWidth)
                        .height(Dimensions.ArrowIconHeight)
                )
            }
        }
    }
}