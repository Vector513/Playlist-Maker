package com.example.playlist_maker_android.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.playlist_maker_android.creator.Creator
import com.example.playlist_maker_android.domain.Track
import com.example.playlist_maker_android.domain.TracksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class FavouritesViewModel(
    private val tracksRepository: TracksRepository
) : ViewModel() {

    val favoriteList: Flow<List<Track>> = tracksRepository.getFavoriteTracks()

    fun toggleFavourite(isFavourite: Boolean, track: Track) {
        viewModelScope.launch(Dispatchers.IO) {
            tracksRepository.updateTrackFavoriteStatus(track, isFavourite)
        }
    }

    companion object {
        fun getViewModelFactory(): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return FavouritesViewModel(
                        tracksRepository = Creator.getTracksRepository()
                    ) as T
                }
            }
    }
}

