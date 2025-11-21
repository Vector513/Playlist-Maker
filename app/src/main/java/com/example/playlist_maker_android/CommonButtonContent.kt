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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
internal fun CommonButtonContent(
    iconResource: Painter,
    buttonText: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
        ) {
            Box(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .width(183.5.dp)
                    .fillMaxHeight()
                    .background(Color.Transparent),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(
                        modifier = Modifier
                            .width(24.dp)
                            .height(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = iconResource,
                            contentDescription = null,
                            modifier = Modifier.size(19.dp)
                        )
                    }
                    Text(
                        text = buttonText,
                        modifier = Modifier,
                        style = TextStyle(
                            fontFamily = YSDisplay,
                            fontWeight = FontWeight.Medium,
                            fontSize = 22.sp,
                            lineHeight = 22.sp,
                            letterSpacing = 0.sp,
                            textAlign = TextAlign.Center,
                            color = Color.Black
                        )
                    )
                }
            }

            Box(
                modifier = Modifier
                    .padding(end = 12.dp)
                    .width(24.dp)
                    .height(24.dp)
                    .background(Color.Transparent),
                contentAlignment = Alignment.Center
            ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_arrow_forward_light_mode),
                        contentDescription = null,
                        modifier = Modifier
                            .width(8.dp)
                            .height(14.dp)
//                            .background(Color.Transparent)
                    )
            }
        }
    }
}