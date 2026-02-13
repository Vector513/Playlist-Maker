package com.example.playlist_maker_android.data.database

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.playlist_maker_android.data.database.entity.PlaylistEntity
import com.example.playlist_maker_android.data.database.entity.PlaylistTrackCrossRefEntity
import com.example.playlist_maker_android.data.database.entity.TrackEntity
import com.example.playlist_maker_android.data.database.entity.toTrack
import com.example.playlist_maker_android.domain.Playlist

data class PlaylistWithTracksEntity(
    @Embedded val playlist: PlaylistEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = PlaylistTrackCrossRefEntity::class,
            parentColumn = "playlistId",
            entityColumn = "trackId"
        )
    )
    val tracks: List<TrackEntity>
)

fun PlaylistWithTracksEntity.toPlaylist() =
    Playlist(
        id = this.playlist.id,
        name = this.playlist.name,
        description = this.playlist.description,
        tracks = this.tracks.map { it.toTrack() }
    )