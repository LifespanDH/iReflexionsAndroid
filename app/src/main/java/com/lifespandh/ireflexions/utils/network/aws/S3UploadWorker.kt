package com.lifespandh.ireflexions.utils.network.aws

import android.content.Context
import androidx.core.net.toUri
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.amplifyframework.core.Amplify
import com.lifespandh.ireflexions.utils.file.createNewFile
import com.lifespandh.ireflexions.utils.file.getFileName
import com.lifespandh.ireflexions.utils.image.deserializeFromJson
import com.lifespandh.ireflexions.utils.network.LiveSubject
import com.lifespandh.ireflexions.utils.network.UploadFileStatus
import java.io.File


class S3UploadWorker(private val context: Context, workerParameters: WorkerParameters): CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {
        val data = inputData.getString(IMAGE_BITMAP_STRING)
        val bitmap = deserializeFromJson(data)

        val file = bitmap?.let { createNewFile(it, context) }
        if (file != null) {
            val extension = file.absolutePath.toString().split(".").last()
            file?.let { uploadFile(extension, it) }
        }
        return Result.success()
    }

    private fun uploadFile(extension: String, file: File) {
        val filename = "${System.currentTimeMillis()}-${getFileName(context, file.toUri())}.$extension"
        val upload = Amplify.Storage.uploadFile(filename, file, {
            LiveSubject.FILE_UPLOAD_FILE.onNext(UploadFileStatus.Complete(it.key))
        }, {
            LiveSubject.FILE_UPLOAD_FILE.onNext(UploadFileStatus.Error(it))
        }).setOnProgress {
            LiveSubject.FILE_UPLOAD_FILE.onNext(UploadFileStatus.FileStatus(it.fractionCompleted))
        }
    }

    companion object {
        const val IMAGE_BITMAP_STRING = "image_bitmap_string"
    }

}