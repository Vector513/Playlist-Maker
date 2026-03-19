package com.example.playlist_maker_android.ui.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlist_maker_android.domain.Playlist
import com.example.playlist_maker_android.domain.PlaylistsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

class NewPlaylistViewModel(
    private val playlistsRepository: PlaylistsRepository
) : ViewModel() {
    private val _coverImageUri = MutableStateFlow<String?>(null)
    val coverImageUri: StateFlow<String?> = _coverImageUri.asStateFlow()

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name.asStateFlow()

    private val _description = MutableStateFlow("")
    val description: StateFlow<String> = _description.asStateFlow()

    private val _playlistCreated = MutableStateFlow<Boolean?>(null)
    val playlistCreated: StateFlow<Boolean?> = _playlistCreated.asStateFlow()

    fun onNameChanged(value: String) {
        _name.value = value
    }

    fun onDescriptionChanged(value: String) {
        _description.value = value
    }

    fun saveImageToStorage(context: Context, uri: Uri) {
        val fileName = "cover_${System.currentTimeMillis()}.png"
        val savedPath = copyImageToInternalStorage(context, uri, fileName)
        _coverImageUri.value = savedPath
    }

    fun createNewPlayList() {
        viewModelScope.launch(Dispatchers.IO) {
            val success = playlistsRepository.addNewPlaylist(
                Playlist(
                    name = _name.value,
                    description = _description.value,
                    coverImageUri = _coverImageUri.value,
                    tracks = emptyList()
                )
            )
            _playlistCreated.value = success
        }
    }

    fun resetCreationState() {
        _playlistCreated.value = null
    }

    private fun copyImageToInternalStorage(
        context: Context,
        uri: Uri,
        fileName: String
    ): String? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            val file = File(context.filesDir, fileName)
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
}