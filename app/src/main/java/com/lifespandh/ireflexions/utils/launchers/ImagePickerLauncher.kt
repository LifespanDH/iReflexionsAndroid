package com.lifespandh.ireflexions.utils.launchers

import android.content.Context
import android.graphics.Bitmap
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.lifespandh.ireflexions.utils.file.pickFileFromUri
import com.lifespandh.ireflexions.utils.image.ImageCompressor
import java.io.File
import java.io.IOException

class ImagePickerLauncher(
    fragment: Fragment,
    listener: OnImagePicked
) {

    private lateinit var context: Context

    private val imagePickerResult =
        fragment.registerForActivityResult(ActivityResultContracts.GetContent()) {
            if (it != null) {
                try {
                    val actualImage = pickFileFromUri(context, it)
                    var compressedImage: Bitmap? = null
                    ImageCompressor(context, object : ImageCompressor.OnImageCompressed {
                        override fun onImageCompressed(compressedBitmap: Bitmap) {
                            compressedImage = compressedBitmap
                            listener.onImagePickResult(actualImage, compressedImage)
                        }
                    }).compressImage(actualImage)
                } catch (e: IOException) {
                    listener.onPickError(e)
                    e.printStackTrace()
                }

            }
        }

    fun launch(input: String, context: Context) {
        this.context = context
        imagePickerResult.launch(input)
    }

    interface OnImagePicked {
        fun onImagePickResult(originalImage: File, compressedBitmap: Bitmap?)
        fun onPickError(exception: Exception)
    }

}