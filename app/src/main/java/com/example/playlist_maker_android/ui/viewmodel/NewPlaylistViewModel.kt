package com.example.playlist_maker_android.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.playlist_maker_android.creator.Creator
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

    // Публичное неизменяемое состояние для UI
    val coverImageUri: StateFlow<String?> = _coverImageUri.asStateFlow()

    // Функция для установки URI обложки
    fun setCoverImageUri(uri: String?) {
        _coverImageUri.value = uri
    }

    fun createNewPlayList(namePlaylist: String, description: String) {
        viewModelScope.launch(Dispatchers.IO) {
            playlistsRepository.addNewPlaylist(Playlist(
                name = namePlaylist,
                description = description,
                coverImageUri = _coverImageUri.value,
                tracks = emptyList()
            ))
        }
    }

    companion object {
        fun getViewModelFactory(): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return NewPlaylistViewModel(
                        Creator.getPlaylistsRepository()
                    ) as T
                }
            }
    }
}