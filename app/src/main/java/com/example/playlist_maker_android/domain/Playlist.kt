package com.example.playlist_maker_android.domain

import com.example.playlist_maker_android.data.database.entity.PlaylistEntity

data class Playlist(
    val id: Long = 0,
    val name: String,
    val description: String,
    val coverImageUri: String? = null,
    var tracks: List<Track>
)

fun Playlist.toEntity(): PlaylistEntity {
    return PlaylistEntity(
        id = this.id,
        name = this.name,
        description = this.description,
        coverImageUri = this.coverImageUri
    )
}