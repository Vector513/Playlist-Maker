package com.example.playlist_maker_android.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.playlist_maker_android.domain.Playlist
import com.example.playlist_maker_android.creator.Creator
import com.example.playlist_maker_android.domain.PlaylistsRepository
import kotlinx.coroutines.flow.Flow

class PlaylistsViewModel(
    playlistsRepository: PlaylistsRepository
) : ViewModel() {

    val playlists: Flow<List<Playlist>> = playlistsRepository.getAllPlaylists()

    companion object {
        fun getViewModelFactory(): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return PlaylistsViewModel(
                        Creator.getPlaylistsRepository()
                    ) as T
                }
            }
    }
}