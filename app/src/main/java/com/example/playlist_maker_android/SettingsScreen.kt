package com.example.playlist_maker_android

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.playlist_maker_android.ui.theme.Dimensions
import com.example.playlist_maker_android.ui.theme.PlaylistmakerandroidTheme
import androidx.core.net.toUri

@Composable
internal fun SettingsScreen(
    onBack: () -> Unit
) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.primary)
                .padding(innerPadding)
            ) {
            SettingsPanelHeader(onBack)
            ListOfButtons()
        }
    }
}

@Composable
private fun SettingsPanelHeader(
    onBack: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimensions.PanelHeaderHeight)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(start = 4.dp, top = 4.dp, bottom = 4.dp)
        ) {
            ArrowBackButton(onBack)
            Text(
                text = stringResource(R.string.settings_panel_header_text),
                modifier = Modifier
                    .padding(start = 12.dp),
                style = MaterialTheme.typography.titleLarge.copy(
                    color = MaterialTheme.colorScheme.onPrimary,
                    textAlign = TextAlign.Center
                )
            )
        }
    }
}


@Preview(device = "spec:width=411dp,height=891dp", showSystemUi = true, showBackground = true)
@Composable
private fun SettingsPanelHeaderPreview() {
    PlaylistmakerandroidTheme {
        SettingsPanelHeader {  }
    }
}

@Composable
private fun ArrowBackButton(
    onBack: () -> Unit
) {
    Button(
        onClick = onBack,
        shape = RectangleShape,
//        colors = ButtonDefaults.buttonColors(
//            containerColor = ,
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
    PlaylistmakerandroidTheme {
        ArrowBackButton {  }
    }
}

@Composable
private fun ListOfButtons() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 24.dp)
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimensions.SettingsButtonHeight)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.dark_theme_text),
                    modifier = Modifier.padding(start = 16.dp),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )

                Box(
                    modifier = Modifier.padding(end = Dimensions.ButtonContentEndPadding),
                    contentAlignment = Alignment.Center
                ) {
                    // TODO: смена темы свитчом, хз как это правильно сделать
                    Switch(
                        checked = isSystemInDarkTheme(),
                        onCheckedChange = null,
                        enabled = false,

                    )

                }
            }
        }

        ShareAppButton()
        SupportButton()
        UserAgreementButton()
    }
}

@Preview(device = "spec:width=411dp,height=891dp", showSystemUi = true, showBackground = true)
@Composable
private fun ListOfButtonsPreview() {
    PlaylistmakerandroidTheme {
        ListOfButtons()
    }
}

@Composable
private fun ShareAppButton() {
    val context = LocalContext.current
    val message = stringResource(R.string.share_app_message)

    Button(
        onClick = {
            shareApp(context, message)
        },
        shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimensions.SettingsButtonHeight),
        contentPadding = PaddingValues(0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.share_app_text),
                modifier = Modifier
                    .padding(start=16.dp),
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
            Box(
                modifier = Modifier
                    .padding(end = Dimensions.ButtonContentEndPadding)
                    .width(Dimensions.IconSize)
                    .height(Dimensions.IconSize),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_share),
                    contentDescription = null,
                    modifier = Modifier
                        .width(Dimensions.ShareIconWidth)
                        .height(Dimensions.ShareIconHeight),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurfaceVariant)
                )
            }
        }
    }
}

fun shareApp(context: Context, message: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, message)
    }
    context.startActivity(
        Intent.createChooser(intent, "Поделиться приложением через")
    )
}

@Composable
private fun SupportButton() {
    val context = LocalContext.current
    val studentEmail = stringResource(R.string.student_email_text)
    val emailSubject = stringResource(R.string.email_subject_text)
    val emailText = stringResource(R.string.email_text)

    Button(
        onClick = { sendEmail(context,
            studentEmail,
            emailSubject,
            emailText
        ) },
        shape = RectangleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimensions.SettingsButtonHeight),
        contentPadding = PaddingValues(0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.support_text),
                modifier = Modifier
                    .padding(start=16.dp),
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
            Box(
                modifier = Modifier
                    .padding(end = Dimensions.ButtonContentEndPadding)
                    .width(Dimensions.IconSize)
                    .height(Dimensions.IconSize),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_support),
                    contentDescription = null,
                    modifier = Modifier
                        .width(Dimensions.ShareIconWidth)
                        .height(Dimensions.ShareIconHeight),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurfaceVariant)
                )
            }
        }
    }
}

fun sendEmail(context: Context,
              studentEmail: String,
              emailSubject: String,
              emailText: String
) {
    val emailIntent = Intent(Intent.ACTION_SEND).apply {
        type = "message/rfc822"
        putExtra(
            Intent.EXTRA_EMAIL,
            arrayOf(studentEmail)
        )
        putExtra(
            Intent.EXTRA_SUBJECT,
            emailSubject
        )
        putExtra(
            Intent.EXTRA_TEXT,
            emailText
        )
    }

    try {
        context.startActivity(
            Intent.createChooser(emailIntent, "Выберите почтовый клиент")
        )
    } catch (ex: ActivityNotFoundException) {
        Toast.makeText(
            context,
            "На устройстве нет почтового клиента",
            Toast.LENGTH_SHORT
        ).show()
    }
}


@Composable
private fun UserAgreementButton() {
    val context = LocalContext.current
    val url = stringResource(R.string.user_agreement_url)


    Button(
        onClick = { openUserAgreement(context, url) },
        shape = RectangleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimensions.SettingsButtonHeight),
        contentPadding = PaddingValues(0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.user_agreement_text),
                modifier = Modifier
                    .padding(start=16.dp),
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
            Box(
                modifier = Modifier
                    .padding(end = Dimensions.ButtonContentEndPadding)
                    .width(Dimensions.IconSize)
                    .height(Dimensions.IconSize),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_arrow_forward),
                    contentDescription = null,
                    modifier = Modifier
                        .width(Dimensions.ShareIconWidth)
                        .height(Dimensions.ShareIconHeight),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurfaceVariant)
                )
            }
        }
    }
}

fun openUserAgreement(
    context: Context,
    url: String
) {
    val intent = Intent(Intent.ACTION_VIEW, url.toUri())

    val chooser = Intent.createChooser(intent, "Выберите браузер")
    chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

    try {
        context.startActivity(chooser)
    } catch (e: Exception) {
        Toast.makeText(context, "Не удалось открыть браузер", Toast.LENGTH_SHORT).show()
    }
}

