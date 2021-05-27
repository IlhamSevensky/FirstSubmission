package com.app.core.extensions

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.net.Uri
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

fun Bitmap.saveToInternalStorage(context: Context): Uri {
    val wrapper = ContextWrapper(context)

    // Initializing a new file
    // The bellow line return a directory in internal storage
    var file = wrapper.getDir("images", Context.MODE_PRIVATE)

    file = File(file, "${UUID.randomUUID()}.png")

    try {

        FileOutputStream(file).use { outputStream ->
            this.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.flush()
        }

    } catch (e: IOException){
        e.printStackTrace()
    }

    return Uri.parse(file.absolutePath)
}