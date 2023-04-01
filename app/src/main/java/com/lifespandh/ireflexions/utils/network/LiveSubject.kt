package com.lifespandh.ireflexions.utils.network

import io.reactivex.rxjava3.subjects.PublishSubject

object LiveSubject {
    val FILE_UPLOAD_FILE: PublishSubject<UploadFileStatus> = PublishSubject.create()
}