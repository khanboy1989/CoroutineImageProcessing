package com.serhankhan.androidcoroutinesimageprocessing

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.graphics.drawable.toDrawable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.net.URL

class MainActivity : AppCompatActivity() {

    private val IMAGE_URL = "https://raw.githubusercontent.com/DevTides/JetpackDogsApp/master/app/src/main/res/drawable/dog.png"

    //In order to load image to ImageView in Main Thread
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        coroutineScope.launch {

            val originalDeferred = coroutineScope.async(Dispatchers.IO) { getOriginalBitMap()}
            val originalBitmap = originalDeferred.await()
            /**
             * val Default: CoroutineDispatcher (source)
            The default CoroutineDispatcher that is used by all standard builders like launch, async, etc if neither a dispatcher nor any other ContinuationInterceptor
            is specified in their context.
            It is backed by a shared pool of threads on JVM.
            By default, the maximum number of threads used by this dispatcher
            is equal to the number of CPU cores, but is at least two.
             */
            val filteredDeferred = coroutineScope.async(Dispatchers.Default) { applyFilter(originalBitmap) }

            val filteredBitmap = filteredDeferred.await()

            loadImage(filteredBitmap)
        }
    }

    private fun applyFilter(originalBitmap:Bitmap) = Filter.apply(originalBitmap)

    private fun getOriginalBitMap() =
        URL(IMAGE_URL).openStream().use {
            BitmapFactory.decodeStream(it)
        }


    private fun loadImage(bmp:Bitmap) {
        progressBar.visibility = View.GONE
        imageView.setImageBitmap(bmp)
        imageView.visibility = View.VISIBLE
    }
}
