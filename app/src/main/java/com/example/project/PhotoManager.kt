package com.example.project

import android.app.Activity
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import java.io.IOException
import android.app.Application
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.graphics.BitmapFactory
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import java.io.File
import java.io.FileInputStream


class PhotoManager {
    companion object {
        fun savePhoto(filename: String, bmp: Bitmap, context: Context): Boolean {
            return try {
                // Need Output stream
                context.openFileOutput("$filename.jpg", MODE_PRIVATE).use { stream ->
                    if (!bmp.compress(Bitmap.CompressFormat.JPEG, 95, stream)) {
                        throw IOException("Couldn't save bitmap.")
                    }
                }
                true
            } catch (e: IOException) {
                false
            }
        }

        fun loadPhoto(filename: String, context: Context): Bitmap {
            val fileDirectory = context.getFilesDir()
            var f = File(fileDirectory, filename)
            var b: Bitmap = BitmapFactory.decodeStream(FileInputStream(f))
            return b
        }

        fun takePhoto (launcher : ActivityResultLauncher<Intent>){
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            launcher.launch(takePictureIntent)
        }
    }

}