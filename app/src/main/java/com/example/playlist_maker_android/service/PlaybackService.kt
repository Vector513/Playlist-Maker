package com.example.playlist_maker_android.service

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.IBinder
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.core.app.NotificationCompat
import androidx.media.app.NotificationCompat.MediaStyle
import coil.ImageLoader
import coil.request.ImageRequest
import com.example.playlist_maker_android.App
import com.example.playlist_maker_android.R
import com.example.playlist_maker_android.domain.PlayerRepository
import com.example.playlist_maker_android.domain.PlayerState
import com.example.playlist_maker_android.ui.main.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class PlaybackService : Service() {

    private val playerRepository: PlayerRepository by inject()
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    private lateinit var mediaSession: MediaSessionCompat
    private var cachedAlbumArt: Bitmap? = null
    private var cachedTrackId: Long? = null

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        mediaSession = MediaSessionCompat(this, "PlaylistMakerSession").apply {
            setCallback(mediaSessionCallback)
            isActive = true
        }

        scope.launch {
            playerRepository.playerState.collect { state ->
                if (state.currentTrack != null) {
                    updateMediaSession(state)
                    loadAlbumArtAndNotify(state)
                } else {
                    mediaSession.isActive = false
                    stopForeground(STOP_FOREGROUND_REMOVE)
                    stopSelf()
                }
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_PLAY_PAUSE -> {
                val state = playerRepository.playerState.value
                if (state.currentTrack != null) {
                    if (state.isPlaying) playerRepository.pause() else playerRepository.resume()
                }
            }
            ACTION_NEXT -> playerRepository.next()
            ACTION_PREVIOUS -> playerRepository.previous()
            ACTION_STOP -> {
                playerRepository.stop()
                stopForeground(STOP_FOREGROUND_REMOVE)
                stopSelf()
            }
        }
        return START_NOT_STICKY
    }

    private val mediaSessionCallback = object : MediaSessionCompat.Callback() {
        override fun onPlay() {
            playerRepository.resume()
        }

        override fun onPause() {
            playerRepository.pause()
        }

        override fun onStop() {
            playerRepository.stop()
        }

        override fun onSkipToNext() {
            playerRepository.next()
        }

        override fun onSkipToPrevious() {
            playerRepository.previous()
        }

        override fun onSeekTo(pos: Long) {
            playerRepository.seekTo(pos.toInt())
        }
    }

    private fun updateMediaSession(state: PlayerState) {
        val track = state.currentTrack ?: return

        val metadata = MediaMetadataCompat.Builder()
            .putString(MediaMetadataCompat.METADATA_KEY_TITLE, track.trackName)
            .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, track.artistName)
            .putLong(MediaMetadataCompat.METADATA_KEY_DURATION, state.durationMs.toLong())
            .apply {
                cachedAlbumArt?.let {
                    putBitmap(MediaMetadataCompat.METADATA_KEY_ALBUM_ART, it)
                }
            }
            .build()
        mediaSession.setMetadata(metadata)

        var actions = PlaybackStateCompat.ACTION_PLAY or
                PlaybackStateCompat.ACTION_PAUSE or
                PlaybackStateCompat.ACTION_PLAY_PAUSE or
                PlaybackStateCompat.ACTION_STOP or
                PlaybackStateCompat.ACTION_SEEK_TO

        if (state.hasNext) actions = actions or PlaybackStateCompat.ACTION_SKIP_TO_NEXT
        if (state.hasPrevious) actions = actions or PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS

        val playbackState = PlaybackStateCompat.Builder()
            .setActions(actions)
            .setState(
                if (state.isPlaying) PlaybackStateCompat.STATE_PLAYING
                else PlaybackStateCompat.STATE_PAUSED,
                state.currentPositionMs.toLong(),
                if (state.isPlaying) 1f else 0f
            )
            .build()
        mediaSession.setPlaybackState(playbackState)
    }

    private fun loadAlbumArtAndNotify(state: PlayerState) {
        val track = state.currentTrack ?: return

        if (cachedTrackId == track.id) {
            showNotification(state)
            return
        }

        cachedTrackId = track.id
        cachedAlbumArt = null
        showNotification(state)

        scope.launch {
            try {
                val loader = ImageLoader(this@PlaybackService)
                val request = ImageRequest.Builder(this@PlaybackService)
                    .data(track.image)
                    .allowHardware(false)
                    .build()
                val result = loader.execute(request)
                cachedAlbumArt = (result.drawable as? BitmapDrawable)?.bitmap
                updateMediaSession(state)
                showNotification(playerRepository.playerState.value)
            } catch (_: Exception) { }
        }
    }

    private fun showNotification(state: PlayerState) {
        val track = state.currentTrack ?: return

        val contentIntent = PendingIntent.getActivity(
            this, 0,
            Intent(this, MainActivity::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val previousIntent = PendingIntent.getService(
            this, 1,
            Intent(this, PlaybackService::class.java).apply { action = ACTION_PREVIOUS },
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val playPauseIntent = PendingIntent.getService(
            this, 2,
            Intent(this, PlaybackService::class.java).apply { action = ACTION_PLAY_PAUSE },
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val nextIntent = PendingIntent.getService(
            this, 3,
            Intent(this, PlaybackService::class.java).apply { action = ACTION_NEXT },
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val stopIntent = PendingIntent.getService(
            this, 4,
            Intent(this, PlaybackService::class.java).apply { action = ACTION_STOP },
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val playPauseIcon = if (state.isPlaying) R.drawable.ic_pause else R.drawable.ic_play
        val playPauseTitle = if (state.isPlaying) getString(R.string.pause_description)
            else getString(R.string.play_description)

        val builder = NotificationCompat.Builder(this, App.PLAYBACK_CHANNEL_ID)
            .setContentTitle(track.trackName)
            .setContentText(track.artistName)
            .setSmallIcon(R.drawable.ic_play)
            .setLargeIcon(cachedAlbumArt)
            .setContentIntent(contentIntent)

        // Action 0: Previous
        builder.addAction(
            R.drawable.ic_skip_previous,
            getString(R.string.previous_description),
            previousIntent
        )
        // Action 1: Play/Pause
        builder.addAction(playPauseIcon, playPauseTitle, playPauseIntent)
        // Action 2: Next
        builder.addAction(
            R.drawable.ic_skip_next,
            getString(R.string.next_description),
            nextIntent
        )
        // Action 3: Stop
        builder.addAction(R.drawable.ic_stop, getString(R.string.stop_description), stopIntent)

        builder.setStyle(
            MediaStyle()
                .setMediaSession(mediaSession.sessionToken)
                .setShowActionsInCompactView(0, 1, 2)
        )
            .setOngoing(state.isPlaying)
            .setSilent(true)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)

        startForeground(
            NOTIFICATION_ID,
            builder.build(),
            ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK
        )
    }

    override fun onDestroy() {
        mediaSession.release()
        scope.cancel()
        super.onDestroy()
    }

    companion object {
        const val ACTION_PLAY_PAUSE = "action_play_pause"
        const val ACTION_NEXT = "action_next"
        const val ACTION_PREVIOUS = "action_previous"
        const val ACTION_STOP = "action_stop"
        const val NOTIFICATION_ID = 1
    }
}
