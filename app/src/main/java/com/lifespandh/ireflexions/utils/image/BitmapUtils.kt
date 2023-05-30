package com.lifespandh.ireflexions.utils.image

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.ContactsContract
import android.provider.MediaStore
import com.google.gson.Gson
import java.io.BufferedInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream


fun getBitmapFromUriPath(image: String, context: Context): Bitmap? {
    val photoUri = Uri.withAppendedPath(
        ContactsContract.Contacts.CONTENT_URI,
        image
    )
    return try {
        val photoStream: InputStream =
            ContactsContract.Contacts.openContactPhotoInputStream(
                context.contentResolver,
                photoUri
            )
        val buf = BufferedInputStream(photoStream)
        val bitmap: Bitmap = BitmapFactory.decodeStream(buf)
        bitmap
    } catch (e: Exception) {
        e.printStackTrace();
        null
    }
}

fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
    val bytes = ByteArrayOutputStream()
    inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
    val path = MediaStore.Images.Media.insertImage(
        inContext.getContentResolver(),
        inImage,
        "Title",
        null
    )
    return Uri.parse(path)
}

fun serializeToJson(bmp: Bitmap?): String? {
    val gson = Gson()
    return gson.toJson(bmp)
}

fun deserializeFromJson(jsonString: String?): Bitmap? {
    val gson = Gson()
    return gson.fromJson(jsonString, Bitmap::class.java)
}