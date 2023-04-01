package com.lifespandh.ireflexions.utils.launchers

import android.content.Context
import android.graphics.Bitmap
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.lifespandh.ireflexions.utils.file.pickFileFromUri
import com.lifespandh.ireflexions.utils.image.ImageCompressor
import com.lifespandh.ireflexions.utils.image.getImageUri
import java.io.File
import java.io.IOException

class ImageCaptureLauncher(
    fragment: Fragment,
    context: Context,
    listener: OnImageCaptured
) {

    private val imageCaptureResult =
        fragment.registerForActivityResult(ActivityResultContracts.TakePicturePreview()) {
            if (it != null) {
                try {
                    val actualImage = getImageUri(context, it)?.let { it1 ->
                        pickFileFromUri(
                            context,
                            it1
                        )
                    }
                    var compressedImage: Bitmap? = null
                    if (actualImage != null) {
                        ImageCompressor(context, object : ImageCompressor.OnImageCompressed {
                            override fun onImageCompressed(compressedBitmap: Bitmap) {
                                compressedImage = compressedBitmap
                            }
                        }).compressImage(actualImage)
                    }
                    listener.onImageCaptured(actualImage, compressedImage)
                } catch (e: IOException) {
                    listener.onImageNotCaptured(e)
                    e.printStackTrace()
                }
            } else {
                listener.onImageNotCaptured()
            }
        }

    fun launch() {
        imageCaptureResult.launch(null)
    }

    interface OnImageCaptured {
        fun onImageCaptured(actualImage: File?, compressedBitmap: Bitmap?)
        fun onImageNotCaptured(exception: java.lang.Exception? = null)
    }
}