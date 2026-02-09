package com.example.playlist_maker_android.data.network

import com.example.playlist_maker_android.data.ITunesApiService
import com.example.playlist_maker_android.data.dto.TracksSearchRequest
import com.example.playlist_maker_android.domain.BaseResponse
import com.example.playlist_maker_android.domain.NetworkClient
import okio.IOException
import retrofit2.HttpException

class RetrofitNetworkClient(private val api: ITunesApiService) : NetworkClient {

    override suspend fun search(dto: Any): BaseResponse {
        return try {
            when (dto) {
                is TracksSearchRequest -> {
                    val response = api.searchTracks(
                        query = dto.expression,
                        media = "music",
                        entity = "song",
                        limit = 10
                    )
                    response.resultCode = 200
                    response
                }

                else -> BaseResponse().apply {
                    resultCode = 400
                    errorMessage = "Invalid request type: expected TracksSearchRequest or String"
                }
            }
        } catch (e: HttpException) {
            // HTTP ошибки (4xx, 5xx) - это ошибки сервера
            BaseResponse().apply {
                resultCode = e.code()
                errorMessage = "Server error: HTTP ${e.code()} - ${e.message()}"
            }
        } catch (e: IOException) {
            BaseResponse().apply {
                resultCode = -1
                errorMessage = "Network error: ${e.message ?: "Unknown IO error"}"
            }
        } catch (e: Exception) {
            BaseResponse().apply {
                resultCode = -2
                errorMessage = "Unexpected error: ${e.message ?: "Unknown error"}"
            }
        }
    }

    override suspend fun getTrackById(id: Long): BaseResponse {
        return try {
            val response = api.getTrackById(id)
            // Устанавливаем resultCode = 200 для успешного ответа
            response.resultCode = 200
            response
        } catch (e: HttpException) {
            // HTTP ошибки (4xx, 5xx) - это ошибки сервера
            BaseResponse().apply {
                resultCode = e.code()
                errorMessage = "Server error: HTTP ${e.code()} - ${e.message()}"
            }
        } catch (e: IOException) {
            BaseResponse().apply {
                resultCode = -1
                errorMessage = "Network error: ${e.message ?: "Unknown IO error"}"
            }
        } catch (e: Exception) {
            BaseResponse().apply {
                resultCode = -2
                errorMessage = "Unexpected error: ${e.message ?: "Unknown error"}"
            }
        }
    }
}