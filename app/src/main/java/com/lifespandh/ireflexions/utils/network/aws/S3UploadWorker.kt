package com.lifespandh.ireflexions.utils.network.aws

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.amplifyframework.core.Amplify
import com.lifespandh.ireflexions.utils.file.createNewFile
import com.lifespandh.ireflexions.utils.image.deserializeFromJson
import com.lifespandh.ireflexions.utils.logs.logE
import com.lifespandh.ireflexions.utils.network.LiveSubject
import com.lifespandh.ireflexions.utils.network.UploadFileStatus
import java.io.File

class S3UploadWorker(private val context: Context, workerParameters: WorkerParameters): Worker(context, workerParameters) {
    override fun doWork(): Result {
        val data = inputData.getString(IMAGE_BITMAP_STRING)
        val bitmap = deserializeFromJson(data)
//        if (intent.extras?.containsKey(S3UploadService.IMAGE_URI) == true) {
//            val imageUri = intent.getParcelableExtra<Uri>(S3UploadService.IMAGE_URI)
//            var image: Bitmap? = null
//            if (imageUri != null) {
//                image = MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)
//            }
            val image = bitmap

            val file = image?.let { createNewFile(it, context) }
            if (file != null) {
                val extension = file.absolutePath.toString().split(".").last()
//                file?.absolutePath?.let { it2 -> uploadFile(it2) }
                file?.let { uploadFile(it) }
            }
//        }
        return Result.success()
    }

    private fun uploadFile(file: File) {
        val upload = Amplify.Storage.uploadFile("ExampleKey", file, {
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