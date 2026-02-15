package com.example.playlist_maker_android.ui.playlists

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import com.example.playlist_maker_android.R
import com.example.playlist_maker_android.ui.search.components.ArrowBackButton
import com.example.playlist_maker_android.ui.theme.Dimensions
import com.example.playlist_maker_android.ui.viewmodel.PlaylistsViewModel
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.playlist_maker_android.ui.viewmodel.NewPlaylistViewModel
import com.example.playlist_maker_android.ui.viewmodel.PlaylistViewModel
import java.io.File
import java.io.FileOutputStream

@Composable
fun NewPlaylistScreen(
    viewModel: NewPlaylistViewModel = viewModel(
        factory = NewPlaylistViewModel.getViewModelFactory()
    ),
    onBack: () -> Unit
) {
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
            val coverImagePath by viewModel.coverImageUri.collectAsState() // путь к локальному файлу

// Launcher для выбора изображения
            val imagePickerLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.GetContent()
            ) { uri: Uri? ->
                uri?.let { selectedUri ->
                    // Генерируем уникальное имя файла
                    val uniqueFileName = "cover_${System.currentTimeMillis()}.png"

                    val savedPath = copyImageToInternalStorage(context, selectedUri, uniqueFileName)
                    savedPath?.let {
                        viewModel.setCoverImageUri(it) // сохраняем путь к локальной копии
                    }
                }
            }

// Launcher для запроса разрешения (только для старых версий Android)
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
                        // Для Android 13+ (API 33+) разрешения не нужны
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            imagePickerLauncher.launch("image/*")
                        } else {
                            // Для старых версий проверяем разрешение
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
                // Показываем выбранное изображение из внутреннего хранилища
                AsyncImage(
                    model = coverImagePath?.let { File(it) }, // путь к локальному файлу
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

            var name by remember { mutableStateOf("") }
            var description by remember { mutableStateOf("") }

            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            ) {
                var isNameFocused by remember { mutableStateOf(false) }

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
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
                    onValueChange = { description = it },
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
                onClick = {
                    viewModel.createNewPlayList(name, description)
                    onBack()
                },
                enabled = name.isNotBlank(),
                modifier = Modifier
                    .padding(horizontal = 17.dp)
                    .fillMaxWidth()
                    .height(44.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.background,
//                    contentColor = MaterialTheme.colorScheme.,
                    disabledContainerColor = MaterialTheme.colorScheme.tertiary,
//                    disabledContentColor = Color.White

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

// --- Функция для копирования изображения во внутреннее хранилище ---
fun copyImageToInternalStorage(context: Context, uri: Uri, fileName: String): String? {
    return try {
        val inputStream = context.contentResolver.openInputStream(uri)
        val file = File(context.filesDir, fileName) // сохраняем под уникальным именем
        inputStream?.use { input ->
            FileOutputStream(file).use { output ->
                input.copyTo(output)
            }
        }
        file.absolutePath
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}