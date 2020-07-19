package com.example.android.devbyteviewer.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.android.devbyteviewer.database.getDatabase
import com.example.android.devbyteviewer.repository.VideosRepository
import retrofit2.HttpException
import timber.log.Timber

/** Worker to update repository with new data from internet **/
class RefreshDataWorker(appContext: Context, params: WorkerParameters): CoroutineWorker(appContext, params) {
    companion object {
        // Every scheduled work request must have a unique name
        const val WORK_NAME = "com.example.android.devbyteviewer.work.RefreshDataWorker"
    }
    override suspend fun doWork(): Result {
        /** Repository makes API calls and stores data in cache. It acts as data source **/
        val videosRepository = VideosRepository(getDatabase(applicationContext))
        try {
            videosRepository.refreshVideos()
        } catch (e: HttpException) {
            Timber.d("video refresh failed, retrying")
            return Result.retry()
        }
        Timber.d("videos refreshed")
        return Result.success()
    }
}
