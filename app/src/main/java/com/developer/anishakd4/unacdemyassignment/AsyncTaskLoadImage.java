package com.developer.anishakd4.unacdemyassignment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class AsyncTaskLoadImage extends AsyncTask<String, String, Bitmap> {

    interface ImageLoadedCallback{
        void imageCallback(Bitmap bitmap);
    }
    private final static String TAG = "AsyncTaskLoadImage";
    private ImageView imageView;
    Integer position;

    ImageLoadedCallback imageLoadedCallback = null;

    public AsyncTaskLoadImage(ImageView imageView, Integer position, ImageLoadedCallback imageLoadedCallback) {
        this.imageView = imageView;
        this.position = position;
        this.imageLoadedCallback = imageLoadedCallback;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        Bitmap bitmap = null;
        try {
            URL url = new URL(params[0]);
            bitmap = BitmapFactory.decodeStream((InputStream) url.getContent());
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        //imageView.setImageBitmap(bitmap);

        if (imageLoadedCallback != null){
            imageLoadedCallback.imageCallback(bitmap);
        }
    }
}
