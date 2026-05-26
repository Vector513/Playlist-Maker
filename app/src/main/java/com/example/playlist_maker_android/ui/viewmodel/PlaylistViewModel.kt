package com.example.playlist_maker_android.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlist_maker_android.domain.PlaylistsRepository
import kotlinx.coroutines.launch


class PlaylistViewModel(
    private val playlistsRepository: PlaylistsRepository,
    private val playlistId: Long
) : ViewModel() {

    val playlist = playlistsRepository.getPlaylist(playlistId)

    fun removeTrack(trackId: Long) {
        viewModelScope.launch {
            playlistsRepository.removeTrackFromPlaylist(trackId, playlistId)
        }
    }
}
