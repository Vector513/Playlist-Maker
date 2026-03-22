package com.example.playlist_maker_android.data.cache

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.net.URL

class PreviewCacheManager(context: Context) {

    private val cacheDir = File(context.cacheDir, CACHE_DIR_NAME).apply { mkdirs() }

    fun getCachedFile(trackId: Long): File? {
        val file = File(cacheDir, "$trackId$FILE_EXTENSION")
        return if (file.exists()) file else null
    }

    suspend fun cachePreview(trackId: Long, previewUrl: String): File? {
        return withContext(Dispatchers.IO) {
            try {
                val file = File(cacheDir, "$trackId$FILE_EXTENSION")
                if (file.exists()) return@withContext file
                URL(previewUrl).openStream().use { input ->
                    file.outputStream().use { output ->
                        input.copyTo(output)
                    }
                }
                file
            } catch (_: Exception) {
                null
            }
        }
    }

    fun deleteCache(trackId: Long) {
        File(cacheDir, "$trackId$FILE_EXTENSION").delete()
    }

    companion object {
        private const val CACHE_DIR_NAME = "previews"
        private const val FILE_EXTENSION = ".m4a"
    }
}
