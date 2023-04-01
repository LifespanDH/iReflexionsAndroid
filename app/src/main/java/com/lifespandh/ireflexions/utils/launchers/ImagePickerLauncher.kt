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
    context: Context,
    listener: OnImagePicked
) {

    private val imagePickerResult =
        fragment.registerForActivityResult(ActivityResultContracts.GetContent()) {
            if (it != null) {
                try {
                    val actualImage = pickFileFromUri(context, it)
                    var compressedImage: Bitmap? = null
                    ImageCompressor(context, object : ImageCompressor.OnImageCompressed {
                        override fun onImageCompressed(compressedBitmap: Bitmap) {
                            compressedImage = compressedBitmap
                        }
                    }).compressImage(actualImage)
                    listener.onImagePickResult(actualImage, compressedImage)
                } catch (e: IOException) {
                    listener.onPickError(e)
                    e.printStackTrace()
                }

            }
        }

    fun launch(input: String) {
        imagePickerResult.launch(input)
    }

    interface OnImagePicked {
        fun onImagePickResult(originalImage: File, compressedBitmap: Bitmap?)
        fun onPickError(exception: Exception)
    }

}