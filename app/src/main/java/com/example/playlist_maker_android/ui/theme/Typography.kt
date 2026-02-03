package com.example.playlist_maker_android.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.playlist_maker_android.R

val YSDisplay = FontFamily(
    Font(R.font.ys_display_regular, FontWeight.Normal),
    Font(R.font.ys_display_medium, FontWeight.Medium)
)
object AppTypography {
    val TitleLarge = TextStyle(
        fontFamily = YSDisplay,
        fontWeight = FontWeight.Medium,
        fontSize = 22.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.sp
    )

    val LabelLarge = TextStyle(
        fontFamily = YSDisplay,
        fontWeight = FontWeight.Medium,
        fontSize = 22.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.sp,
        textAlign = TextAlign.Center
    )

    val LabelSmall = TextStyle(
        fontFamily = YSDisplay,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 14.sp,
        letterSpacing = 0.sp,
        textAlign = TextAlign.Center
    )

    val BodyLarge = TextStyle(
        fontFamily = YSDisplay,
        fontWeight = FontWeight.Normal,
        fontSize = 19.sp,
        lineHeight = 19.sp,
        letterSpacing = 0.sp
    )

    val BodyMedium = TextStyle(
        fontFamily = YSDisplay,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.sp
    )

    val BodySmall = TextStyle(
        fontFamily = YSDisplay,
        fontWeight = FontWeight.Normal,
        fontSize = 11.sp,
        lineHeight = 11.sp,
        letterSpacing = 0.sp
    )

    val DisplaySmall =  TextStyle(
        fontFamily = YSDisplay,
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp,
        lineHeight = 13.sp,
        letterSpacing = 0.sp,
        textAlign = TextAlign.Center
    )
}

