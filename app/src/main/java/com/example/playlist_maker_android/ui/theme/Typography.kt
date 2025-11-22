package com.example.playlist_maker_android.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.playlist_maker_android.R

val YSDisplay = FontFamily(
    Font(R.font.ys_display_medium, FontWeight.Medium)
)

object AppTypography {
    val ButtonText = TextStyle(
        fontFamily = YSDisplay,
        fontWeight = FontWeight.Medium,
        fontSize = 22.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.sp
    )
    
    val PanelHeaderText = TextStyle(
        fontFamily = YSDisplay,
        fontWeight = FontWeight.Medium,
        fontSize = 22.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.sp
    )
}

