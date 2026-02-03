package com.example.playlist_maker_android.data

import com.example.playlist_maker_android.data.network.Track
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class DatabaseMock(val scope: CoroutineScope) {

    private val historyList = mutableListOf<Word>(
        Word("Beatles"),
        Word("Группа"),
        Word("Yesterday"),
        Word("Let it Be"))
    private val _historyUpdates = MutableSharedFlow<Unit>()
    private val _playlistsUpdates = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    private val playlists = mutableListOf<Playlist>()
    private val tracks = mutableListOf(
        Track(
            id = 1,
            trackName = "Владивосток 2000",
            artistName = "Мумий Троль",
            trackTime = "2:38",
            image = "",
            favorite = false,
            playlistId = 0
        ),
        Track(
            id = 2,
            trackName = "Группа крови",
            artistName = "Кино",
            trackTime = "4:43",
            image = "",
            favorite = false,
            playlistId = 0
        ),
        Track(
            id = 3,
            trackName = "Не смотри назад",
            artistName = "Ария",
            trackTime = "5:12",
            image = "",
            favorite = false,
            playlistId = 0
        ),
        Track(
            id = 4,
            trackName = "Звезда по имени Солнце",
            artistName = "Кино",
            trackTime = "3:45",
            image = "",
            favorite = false,
            playlistId = 0
        ),
        Track(
            id = 5,
            trackName = "Лондон",
            artistName = "Аквариум",
            trackTime = "4:32",
            image = "",
            favorite = false,
            playlistId = 0
        ),
        Track(
            id = 6,
            trackName = "На заре",
            artistName = "Альянс",
            trackTime = "3:50",
            image = "",
            favorite = false,
            playlistId = 0
        ),
        Track(
            id = 7,
            trackName = "Перемен",
            artistName = "Кино",
            trackTime = "4:56",
            image = "",
            favorite = false,
            playlistId = 0
        ),
        Track(
            id = 8,
            trackName = "Розовый фламинго",
            artistName = "Сплин",
            trackTime = "3:15",
            image = "",
            favorite = false,
            playlistId = 0
        ),
        Track(
            id = 9,
            trackName = "Танцевать",
            artistName = "Мельница",
            trackTime = "3:42",
            image = "",
            favorite = false,
            playlistId = 0
        ),
        Track(
            id = 10,
            trackName = "Чёрный бумер",
            artistName = "Серега",
            trackTime = "4:01",
            image = "",
            favorite = false,
            playlistId = 0
        )
    )

    fun getHistoryRequests(): List<Word> = historyList.reversed()

    fun notifyHistoryChanged() {
        scope.launch(Dispatchers.IO) {
            _historyUpdates.emit(Unit)
        }
    }

    fun addToHistory(word: Word) {
        historyList.add(word)
        notifyHistoryChanged()
    }

    fun getAllPlaylists(): Flow<List<Playlist>> = flow {
        fun buildPlaylists(): List<Playlist> {
            val filteredPlaylists = mutableListOf<Playlist>()
            playlists.forEach { playlist ->
                val playlistTracks = tracks.filter { track ->
                    track.playlistId == playlist.id
                }
                filteredPlaylists.add(playlist.copy(tracks = playlistTracks))
            }
            return filteredPlaylists.toList()
        }

        delay(500) // Имитируем задержку загрузки из базы данных
        emit(buildPlaylists())
        _playlistsUpdates.collect {
            emit(buildPlaylists())
        }
    }

    fun getPlaylist(id: Long): Flow<Playlist?> = flow {
        emit(playlists.find { it.id == id })
    }

    fun addNewPlaylist(name: String, description: String) {
        playlists.add(
            Playlist(
                id = playlists.size.toLong() + 1,
                name = name,
                description = description,
                tracks = emptyList()
            )
        )
        _playlistsUpdates.tryEmit(Unit)
    }

    fun deletePlaylistById(playlistId: Long) {
        playlists.removeIf { it.id == playlistId }
        _playlistsUpdates.tryEmit(Unit)
    }

    fun deleteTrackFromPlaylist(trackId: Long) {
        tracks.removeIf { it.id == trackId }
        _playlistsUpdates.tryEmit(Unit)
    }

    fun getTrackByNameAndArtist(track: Track): Flow<Track?> = flow {
        emit(tracks.find { it.trackName == track.trackName && it.artistName == track.artistName })
    }

    fun insertTrack(track: Track) {
        tracks.removeIf { it.id == track.id }
        tracks.add(track)
        _playlistsUpdates.tryEmit(Unit)
    }

    fun getFavoriteTracks(): Flow<List<Track>> = flow {
        delay(300) // Имитируем задержку
        val favorites = tracks.filter { it.favorite }
        emit(favorites)
    }

    fun deleteTracksByPlaylistId(playlistId: Long) {
        tracks.removeIf { it.playlistId == playlistId }
        _playlistsUpdates.tryEmit(Unit)
    }

    fun searchTracks(expression: String): List<Track> {
        return tracks.filter { it.trackName.contains(expression, true) }
    }
}