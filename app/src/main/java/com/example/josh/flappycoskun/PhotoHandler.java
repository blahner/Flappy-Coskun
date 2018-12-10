package com.example.josh.flappycoskun;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

public class PhotoHandler {

    public static Bitmap decodeBitmap(Resources res, int resID, int reqWidth, int reqHeight) {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resID, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        Bitmap bmp = BitmapFactory.decodeResource(res, resID, options);
        return bmp;
    }

    //some info on bitmaps & canvases: http://www.informit.com/articles/article.aspx?p=2143148&seqNum=2
    public static Bitmap resizeBitmap(Bitmap bmp, int newWidth, int newHeight) {
        //returns a NEW bitmap similar to bmp (the input argument), but resized.
        int oldWidth = bmp.getWidth();
        int oldHeight = bmp.getHeight();

        //resizing
        Matrix matrix = new Matrix();
        matrix.postScale(((float) newWidth) / oldWidth, ((float) newHeight) / oldHeight);

        Bitmap resizedBMP = Bitmap.createBitmap(bmp, 0, 0, oldWidth, oldHeight, matrix, false);
        bmp.recycle(); //free memory (https://developer.android.com/reference/android/graphics/Bitmap#recycle())
        return resizedBMP;
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        //https://developer.android.com/topic/performance/graphics/load-bitmap
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float) height / (float) reqHeight);
            } else {
                inSampleSize = Math.round((float) width / (float) reqWidth);
            }
        }
        return inSampleSize;
    }

}
