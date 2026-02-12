package com.example.playlist_maker_android.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.playlist_maker_android.creator.Creator
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
            tracksRepository.getTrackById(trackId)
                .collect { track ->
                    _currentTrack.value = track
                }
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
            tracksRepository.insertTrackToPlaylist(track, playlistId)
        }
    }

    companion object {
        fun getViewModelFactory(trackId: Long): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return TrackViewModel(
                        tracksRepository = Creator.getTracksRepository(),
                        playlistsRepository = Creator.getPlaylistsRepository(),
                        trackId = trackId
                    ) as T
                }
            }
    }
}

