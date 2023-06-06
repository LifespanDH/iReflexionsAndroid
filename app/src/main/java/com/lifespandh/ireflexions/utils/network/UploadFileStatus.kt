package com.lifespandh.ireflexions.utils.network

import java.net.URL

sealed class UploadFileStatus{
    data class FileStatus(val status: Double): UploadFileStatus()
    data class Error(val exception: Throwable): UploadFileStatus()
    data class Complete(val s3Url: String): UploadFileStatus()
    data class Start(val start: Boolean): UploadFileStatus()
}