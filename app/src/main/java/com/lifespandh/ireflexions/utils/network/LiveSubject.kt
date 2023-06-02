package com.lifespandh.ireflexions.utils.network

import com.lifespandh.ireflexions.models.SupportContact
import io.reactivex.rxjava3.subjects.PublishSubject

object LiveSubject {
    val FILE_UPLOAD_FILE: PublishSubject<UploadFileStatus> = PublishSubject.create()
    val supportContactAdded: PublishSubject<SupportContact> = PublishSubject.create()
}