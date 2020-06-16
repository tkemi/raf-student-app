package com.example.project2.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.io.IOException;

import androidx.exifinterface.media.ExifInterface;

public class ImageUtil {

    public static Bitmap getRotatedBitmap(String path){
        int orientation = calculateRotation(path);
        Matrix matrix = new Matrix();
        matrix.postRotate(orientation);
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(),
                matrix, true);
    }

    private static int calculateRotation(String path) {
        int rotation = 0;
        ExifInterface exif;

        try {
            exif = new ExifInterface(path);
        } catch (IOException e) {
            e.printStackTrace();
            return rotation;
        }

        int orientation = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                rotation = 90;
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                rotation = 180;
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                rotation = 270;
                break;
            case ExifInterface.ORIENTATION_NORMAL:
            default:
                rotation = 0;
        }
        return rotation;
    }
}
