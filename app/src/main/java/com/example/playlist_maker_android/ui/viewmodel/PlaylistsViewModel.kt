package com.example.playlist_maker_android.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.playlist_maker_android.domain.Playlist
import com.example.playlist_maker_android.domain.PlaylistsRepository
import kotlinx.coroutines.flow.Flow

class PlaylistsViewModel(
    playlistsRepository: PlaylistsRepository
) : ViewModel() {

    val playlists: Flow<List<Playlist>> = playlistsRepository.getAllPlaylists()

}