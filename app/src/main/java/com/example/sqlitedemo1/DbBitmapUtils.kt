package com.example.sqlitedemo1

import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream


class DbBitmapUtils {
    companion object {
        // convert from bitmap to byte array
        fun getBytes(bitmap: Bitmap): ByteArray {
            val stream = ByteArrayOutputStream()
            bitmap.compress(CompressFormat.JPEG, 0, stream)
            return stream.toByteArray()
        }

        // convert from byte array to bitmap
        fun getImage(image: ByteArray): Bitmap {
            return BitmapFactory.decodeByteArray(image, 0, image.size)
        }
    }

}
