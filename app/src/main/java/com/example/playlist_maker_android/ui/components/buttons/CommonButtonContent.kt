package com.example.playlist_maker_android.ui.components.buttons

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.playlist_maker_android.R
import com.example.playlist_maker_android.ui.theme.Dimensions

@Composable
internal fun CommonButtonContent(
    iconResource: Painter,
    buttonText: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
//                .background(MaterialTheme.colorScheme.primary)
        ) {
            Box(
                modifier = Modifier
                    .padding(start = Dimensions.ButtonContentStartPadding)
                    .width(Dimensions.ButtonContentWidth)
                    .fillMaxHeight()
//                    .background(MaterialTheme.colorScheme.primary)
,
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
                            modifier = Modifier
                                .size(Dimensions.IconSizeSmall)
//                                .background(MaterialTheme.colorScheme.primary)
                                ,
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary)
                        )
                    }
                    Text(
                        text = buttonText,
                        modifier = Modifier
                            .padding(start = 8.dp),
                        style = MaterialTheme.typography.labelLarge.copy(
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                }
            }

            Box(
                modifier = Modifier
                    .padding(end = Dimensions.ButtonContentEndPadding)
                    .width(Dimensions.IconSize)
                    .height(Dimensions.IconSize)
//                    .background(MaterialTheme.colorScheme.primary)
                    ,
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_arrow_forward),
                    contentDescription = null,
                    modifier = Modifier
                        .width(Dimensions.ArrowIconWidth)
                        .height(Dimensions.ArrowIconHeight)
//                        .background(MaterialTheme.colorScheme.primary)
                        ,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary)
                )
            }
        }
    }
}