package com.example.playlist_maker_android.ui.playlists

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import com.example.playlist_maker_android.R
import com.example.playlist_maker_android.ui.search.components.ArrowBackButton
import com.example.playlist_maker_android.ui.theme.Dimensions
import com.example.playlist_maker_android.ui.viewmodel.NewPlaylistViewModel
import org.koin.androidx.compose.koinViewModel
import java.io.File

@Composable
fun NewPlaylistScreen(
    viewModel: NewPlaylistViewModel = koinViewModel(),
    onBack: () -> Unit
) {
    val name by viewModel.name.collectAsState()
    val description by viewModel.description.collectAsState()
    val coverImagePath by viewModel.coverImageUri.collectAsState()
    val playlistCreated by viewModel.playlistCreated.collectAsState()

    LaunchedEffect(playlistCreated) {
        if (playlistCreated == true) {
            onBack()
            viewModel.resetCreationState()
        }
    }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary)
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
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
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = stringResource(id = R.string.new_playlist_title),
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(26.dp))

            val context = LocalContext.current

            val imagePickerLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.GetContent()
            ) { uri: Uri? ->
                uri?.let { viewModel.saveImageToStorage(context, it) }
            }

            val permissionLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    imagePickerLauncher.launch("image/*")
                }
            }

            Box(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .height(312.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .clickable {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            imagePickerLauncher.launch("image/*")
                        } else {
                            when {
                                ContextCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.READ_EXTERNAL_STORAGE
                                ) == PackageManager.PERMISSION_GRANTED -> {
                                    imagePickerLauncher.launch("image/*")
                                }
                                else -> {
                                    permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                                }
                            }
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = coverImagePath?.let { File(it) },
                    contentDescription = "",
                    modifier = Modifier.then(
                        if (coverImagePath == null) Modifier.size(79.dp) else Modifier.fillMaxSize()
                    ),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(R.drawable.ic_playlist_default_image),
                    error = painterResource(R.drawable.ic_playlist_default_image)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            ) {
                var isNameFocused by remember { mutableStateOf(false) }

                OutlinedTextField(
                    value = name,
                    onValueChange = { viewModel.onNameChanged(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusChanged { isNameFocused = it.isFocused },
                    textStyle = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onPrimary
                    ),
                    label = {
                        Text(
                            text = stringResource(R.string.new_playlist_name_placeholder),
                            style = if (isNameFocused) {
                                MaterialTheme.typography.labelSmall
                            } else {
                                MaterialTheme.typography.bodyMedium
                            }
                        )
                    },
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.new_playlist_name_placeholder),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        cursorColor = MaterialTheme.colorScheme.background,
                        focusedBorderColor = MaterialTheme.colorScheme.background,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                        focusedLabelColor = MaterialTheme.colorScheme.background,
                        unfocusedPlaceholderColor = MaterialTheme.colorScheme.onPrimary,
                        focusedPlaceholderColor = MaterialTheme.colorScheme.onPrimary
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                var isDescriptionFocused by remember { mutableStateOf(false) }
                OutlinedTextField(
                    value = description,
                    onValueChange = { viewModel.onDescriptionChanged(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusChanged { isDescriptionFocused = it.isFocused },
                    textStyle = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onPrimary
                    ),
                    label = {
                        Text(
                            text = stringResource(R.string.new_playlist_description_placeholder),
                            style = if (isDescriptionFocused) {
                                MaterialTheme.typography.labelSmall
                            } else {
                                MaterialTheme.typography.bodyMedium
                            }
                        )
                    },
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.new_playlist_description_placeholder),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        cursorColor = MaterialTheme.colorScheme.background,
                        focusedBorderColor = MaterialTheme.colorScheme.background,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                        focusedLabelColor = MaterialTheme.colorScheme.background,
                        unfocusedPlaceholderColor = MaterialTheme.colorScheme.onPrimary,
                        focusedPlaceholderColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { viewModel.createNewPlayList() },
                enabled = name.isNotBlank(),
                modifier = Modifier
                    .padding(horizontal = 17.dp)
                    .fillMaxWidth()
                    .height(44.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    disabledContainerColor = MaterialTheme.colorScheme.tertiary,
                ),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(
                    text = stringResource(id = R.string.new_playlist_create_button),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
            }

            Spacer(Modifier.height(32.dp))
        }
    }
}
