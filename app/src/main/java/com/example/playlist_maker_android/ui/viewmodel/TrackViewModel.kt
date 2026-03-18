package com.example.playlist_maker_android.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlist_maker_android.domain.Playlist
import com.example.playlist_maker_android.domain.PlaylistsRepository
import com.example.playlist_maker_android.domain.Track
import com.example.playlist_maker_android.domain.TracksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TrackViewModel(
    private val tracksRepository: TracksRepository,
    private val playlistsRepository: PlaylistsRepository,
    private val trackId: Long
) : ViewModel() {

    val playlists: Flow<List<Playlist>> = playlistsRepository.getAllPlaylists()

    private val _currentTrack = MutableStateFlow<Track?>(null)
    val currentTrack = _currentTrack.asStateFlow()

    init {
        loadTrack()
    }

    private fun loadTrack() {
        viewModelScope.launch {
            _currentTrack.value = tracksRepository.getTrackById(trackId)
        }
    }

    fun toggleFavorite(isFavorite: Boolean) {
        val track = _currentTrack.value ?: return
        viewModelScope.launch(Dispatchers.IO) {
            tracksRepository.updateTrackFavoriteStatus(track, isFavorite)
            _currentTrack.value = track.copy(favorite = isFavorite)
        }
    }

    fun insertTrackToPlaylist(playlistId: Long) {
        val track = _currentTrack.value ?: return
        viewModelScope.launch(Dispatchers.IO) {
            playlistsRepository.addTrackToPlaylist(track, playlistId)
        }
    }

}

