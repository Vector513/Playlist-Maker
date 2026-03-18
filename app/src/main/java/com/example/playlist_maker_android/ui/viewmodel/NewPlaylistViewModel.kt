package com.example.playlist_maker_android.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlist_maker_android.domain.Playlist
import com.example.playlist_maker_android.domain.PlaylistsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NewPlaylistViewModel(
    private val playlistsRepository: PlaylistsRepository
) : ViewModel() {
    private var _coverImageUri = MutableStateFlow<String?>(null)
    val coverImageUri: StateFlow<String?> = _coverImageUri.asStateFlow()

    private val _playlistCreated = MutableStateFlow<Boolean?>(null)
    val playlistCreated: StateFlow<Boolean?> = _playlistCreated.asStateFlow()

    fun setCoverImageUri(uri: String?) {
        _coverImageUri.value = uri
    }

    fun createNewPlayList(namePlaylist: String, description: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val success = playlistsRepository.addNewPlaylist(Playlist(
                name = namePlaylist,
                description = description,
                coverImageUri = _coverImageUri.value,
                tracks = emptyList()
            ))
            _playlistCreated.value = success
        }
    }

    fun resetCreationState() {
        _playlistCreated.value = null
    }

}