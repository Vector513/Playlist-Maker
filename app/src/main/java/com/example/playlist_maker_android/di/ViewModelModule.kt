package com.example.playlist_maker_android.di

import com.example.playlist_maker_android.ui.viewmodel.FavouritesViewModel
import com.example.playlist_maker_android.ui.viewmodel.NewPlaylistViewModel
import com.example.playlist_maker_android.ui.viewmodel.PlaylistViewModel
import com.example.playlist_maker_android.ui.viewmodel.PlaylistsViewModel
import com.example.playlist_maker_android.ui.viewmodel.SearchViewModel
import com.example.playlist_maker_android.ui.viewmodel.TrackViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SearchViewModel(get(), get()) }
    viewModel { FavouritesViewModel(get()) }
    viewModel { PlaylistsViewModel(get()) }
    viewModel { NewPlaylistViewModel(get()) }
    viewModel { (playlistId: Long) -> PlaylistViewModel(get(), playlistId) }
    viewModel { (trackId: Long) -> TrackViewModel(get(), get(), trackId) }
}
