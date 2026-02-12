package com.example.playlist_maker_android.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.playlist_maker_android.creator.Creator
import com.example.playlist_maker_android.domain.Track
import com.example.playlist_maker_android.domain.TracksRepository
import kotlinx.coroutines.flow.Flow

class FavouritesViewModel(
    private val tracksRepository: TracksRepository
) : ViewModel() {

    val favoriteList: Flow<List<Track>> = tracksRepository.getFavoriteTracks()

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

