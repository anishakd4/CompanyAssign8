package com.developer.anishakd4.unacdemyassignment

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Process
import android.widget.ImageView
import com.developer.anishakd4.unacdemyassignment.AsyncTaskLoadImage.ImageLoadedCallback
import java.io.BufferedInputStream
import java.net.URL
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.ThreadFactory

class ImageLoadingLibrary(context: Context) {

    interface ImageLoadedCallbackLibrary {
        fun imageCallbacking(bitmap: Bitmap?, position: Int?, imageView: ImageView?)
    }

    private val imageViewMap = Collections.synchronizedMap(WeakHashMap<ImageView, String>())

    private val executorService: ExecutorService

    init {
        executorService = Executors.newFixedThreadPool(20, ImageThreadFactory())
    }

    private var imageLoadedCallbackLibrary: ImageLoadedCallbackLibrary? = null

    companion object {

        private var INSTANCE: ImageLoadingLibrary? = null

        @Synchronized
        fun with(context: Context): ImageLoadingLibrary {

            require(context != null) {
                "ImageLoader:with - Context should not be null."
            }

            return INSTANCE ?: ImageLoadingLibrary(context).also {
                INSTANCE = it
            }

        }
    }

    fun load(imageView: ImageView, imageUrl: String, imageLoadedCallbackLibrary: ImageLoadedCallbackLibrary,  position: Int) {

        require(imageView != null) {
            "ImageLoader:load - ImageView should not be null."
        }

        require(imageUrl != null && imageUrl.isNotEmpty()) {
            "ImageLoader:load - Image Url should not be empty"
        }

        this.imageLoadedCallbackLibrary = imageLoadedCallbackLibrary

        //imageView.setImageResource(0)
        imageViewMap[imageView] = imageUrl

        executorService.submit(PhotosLoaderMainRunnable(ImageRequest(imageUrl, imageView, position)))
    }

    internal class ImageThreadFactory : ThreadFactory {
        override fun newThread(runnable: Runnable): Thread {
            return Thread(runnable).apply {
                name = "ImageLoaderLibrary Thread"
                priority = Process.THREAD_PRIORITY_BACKGROUND
            }
        }
    }

    private fun isImageViewReused(imageRequest: ImageRequest): Boolean {
        val tag = imageViewMap[imageRequest.imageView]
        return tag == null || tag != imageRequest.imgUrl
    }

    inner class ImageRequest(var imgUrl: String, var imageView: ImageView, var position: Int)

    inner class PhotosLoaderMainRunnable(private var imageRequest: ImageRequest) : Runnable {

        override fun run() {

            if(isImageViewReused(imageRequest)){
                return
            }

//            if(imageRequest.position == 0){
//                Thread.sleep(4000)
//            }
//
//            if(imageRequest.position == 7){
//                Thread.sleep(1000)
//            }

            val bitmap = downloadBitmapFromURL(imageRequest.imgUrl)

            if(!isImageViewReused(imageRequest) && imageLoadedCallbackLibrary != null){
                //imageLoadedCallbackLibrary!!.imageCallbacking(bitmap, imageRequest.position, imageRequest.imageView)
                imageRequest.imageView.setImageBitmap(bitmap)
            }
        }
    }

    fun downloadBitmapFromURL(imageUrl: String): Bitmap? {
        val url = URL(imageUrl)
        val inputStream = BufferedInputStream(url.openConnection().getInputStream())
        return BitmapFactory.decodeStream(inputStream)
    }
}