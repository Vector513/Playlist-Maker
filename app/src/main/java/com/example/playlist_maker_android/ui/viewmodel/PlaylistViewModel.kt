package com.example.playlist_maker_android.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.playlist_maker_android.domain.PlaylistsRepository


class PlaylistViewModel(
    playlistsRepository: PlaylistsRepository,
    playlistId: Long
) : ViewModel() {
    
    val playlist = playlistsRepository.getPlaylist(playlistId)
    
}
