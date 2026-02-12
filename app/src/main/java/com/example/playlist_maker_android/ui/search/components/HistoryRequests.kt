package com.example.playlist_maker_android.ui.search.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.playlist_maker_android.R
import com.example.playlist_maker_android.ui.theme.Dimensions

@Composable
fun HistoryRequests(
    historyList: List<String>,
    onClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
            .heightIn(max = 200.dp)
            .background(
                color = MaterialTheme.colorScheme.secondary,
                shape = RoundedCornerShape(
                    bottomStart = Dimensions.SearchBarRadius,
                    bottomEnd = Dimensions.SearchBarRadius
                ),
                )
//            .border(
//                width = 1.dp,
//                color = MaterialTheme.colorScheme.onSurfaceVariant,
//                shape = RoundedCornerShape(4.dp)
//            )
    ) {
        items(historyList.size) { index ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimensions.HistoryRequestHeight)
                    .clickable { onClick(historyList[index]) }
//                    .padding(8.dp),
                ,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(14.dp))
                Icon(
                    painter = painterResource(R.drawable.ic_recent_request),
                    contentDescription = null,
                    modifier = Modifier.size(Dimensions.RecentRequestIconSize),
                    tint = MaterialTheme.colorScheme.tertiary
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = historyList[index],
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onTertiary
                    )
                )
            }
        }
    }
}