package com.lifespandh.ireflexions.utils.network.aws

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.core.app.JobIntentService
import com.amplifyframework.core.Amplify
import com.lifespandh.ireflexions.utils.file.createNewFile
import com.lifespandh.ireflexions.utils.network.LiveSubject
import com.lifespandh.ireflexions.utils.network.UploadFileStatus
import java.io.File

class S3UploadService(
    private val context: Context
) : JobIntentService() {

//    private val secrets = getSecrets()

    override fun onHandleWork(intent: Intent) {
        if (intent.extras?.containsKey(IMAGE_URI) == true) {
            val imageUri = intent.getParcelableExtra<Uri>(IMAGE_URI)
            var image: Bitmap? = null
            if (imageUri != null) {
                image = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
            }

            val file = image?.let { createNewFile(it, context) }
            if (file != null) {
                val extension = file.absolutePath.toString().split(".").last()
//                file?.absolutePath?.let { it2 -> uploadFile(it2) }
                file?.let { uploadFile(it) }
            }
        }
    }

    private fun uploadFile(file: File) {
        val upload = Amplify.Storage.uploadFile("ExampleKey", file, {
            LiveSubject.FILE_UPLOAD_FILE.onNext(UploadFileStatus.Complete(it.key))
        }, {
            LiveSubject.FILE_UPLOAD_FILE.onNext(UploadFileStatus.Error(it))
        }).setOnProgress {
            LiveSubject.FILE_UPLOAD_FILE.onNext(UploadFileStatus.FileStatus(it.fractionCompleted.toInt()))
        }
    //        try {
//            val result = upload.result()
//            Log.i("MyAmplifyApp", "Successfully uploaded: ${result.key}")
//        } catch (error: StorageException) {
//            Log.e("MyAmplifyApp", "Upload failed", error)
//        }
    }

//    private fun s3Upload(path: String, context: Context, extension: String = "jpeg", fileName: String = System.currentTimeMillis().toString()) {
//        val policy: StrictMode.ThreadPolicy = StrictMode.ThreadPolicy.Builder().permitAll().build()
//        StrictMode.setThreadPolicy(policy)
//        val credentials = BasicAWSCredentials(secrets.awsAccessKey, secrets.awsSecretKey)
//        val s3 = AmazonS3Client(credentials)
//        java.security.Security.setProperty("networkaddress.cache.ttl","60")
//        s3.setRegion(Region.getRegion(Regions.US_WEST_2))
//        s3.endpoint = secrets.awsEndpoint
//        val transferUtility = TransferUtility.builder()
//            .defaultBucket(
//                secrets.s3BucketName)
//            .context(context).s3Client(s3).build()
//        val file = File(path)
//        val uploadObserver =
//            transferUtility.upload(
//                secrets.s3BucketName,
//                "$fileName.$extension",
//                file,
//                CannedAccessControlList.PublicRead
//            )
//
//        val transferListener = object : TransferListener {
//            override fun onStateChanged(id: Int, state: TransferState)  {
//                if (state == TransferState.COMPLETED) {
//                    val url = "${secrets.s3BaseUrl}/$fileName.$extension"
//                    LiveSubject.FILE_UPLOAD_FILE.onNext(UploadFileStatus.Complete(url))
//                }
//            }
//            override fun onProgressChanged(id: Int, current: Long, total: Long) {
//                val status = (((current.toDouble() / total) * 100.0).toInt())
//                LiveSubject.FILE_UPLOAD_FILE.onNext(UploadFileStatus.FileStatus(status))
//            }
//            override fun onError(id: Int, ex: Exception) {
//                LiveSubject.FILE_UPLOAD_FILE.onNext(UploadFileStatus.Error(ex))
//            }
//        }
//
//        uploadObserver.setTransferListener(transferListener)
//
//    }

    companion object {
        /**
         * Unique job ID for this service.
         */
        const val JOB_ID = 1000
        const val IMAGE_URI = "image_uri"
        const val IMAGE_URL = "image_url"
        const val DOCUMENT_URI = "document_uri"
        const val DOCUMENT_URL = "document_url"

        /**
         * Convenience method for enqueuing work in to this service.
         */
        fun enqueueWork(context: Context, work: Intent?) {
            enqueueWork(context,
                S3UploadService::class.java, JOB_ID,
                work!!)
        }

        fun newInstance(imageUri: Uri) = Intent(IMAGE_URI, imageUri)
    }
}
