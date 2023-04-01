package com.lifespandh.ireflexions.utils.image

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.format
import id.zelory.compressor.constraint.quality
import id.zelory.compressor.constraint.resolution
import id.zelory.compressor.constraint.size
import kotlinx.coroutines.*
import java.io.File
import kotlin.coroutines.CoroutineContext

class ImageCompressor(
    private val context: Context,
    private val listener: OnImageCompressed
): CoroutineScope {

    fun compressImage(actualImage: File) {
        ImageCompressor(actualImage)
            .compress()
    }

    private inner class ImageCompressor(private val actualImage: File): CoroutineScope {
        private val job: Job = Job()
        override val coroutineContext: CoroutineContext
            get() = Dispatchers.IO + job

        fun compress() = launch {
            val result = compress(actualImage)
            result.let {
                listener.onImageCompressed(it)
            }
        }
    }

    suspend fun compress(actualImage: File): Bitmap = withContext(Dispatchers.IO) {
        actualImage.let { imageFile ->
            val compressedImage = Compressor.compress(context, imageFile) {
                resolution(100, 100)
                quality(80)
                format(Bitmap.CompressFormat.PNG)
                size(30000) // 30 KB
            }

            val bitmapImage = BitmapFactory.decodeFile(compressedImage.path)

            return@withContext bitmapImage
        }
    }

    override val coroutineContext: CoroutineContext
        get() = Job()

    interface OnImageCompressed {
        fun onImageCompressed(compressedBitmap: Bitmap)
    }
}