package com.the_mystic.diu.blooddonationproject2

import android.content.Context
import android.graphics.Color
import android.util.Base64
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import java.io.File
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log

fun Int.Companion.randomColor(): Int {
    return Color.argb(
        255,
        Random.nextInt(256),
        Random.nextInt(256),
        Random.nextInt(256)
    )
}

fun String.toast(context: Context, show: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, this, show).show()
}

fun Uri.getOriginalFileName(context: Context): String? {
    return context.contentResolver.query(this, null, null, null, null)?.use {
        val nameColumnIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        it.moveToFirst()
        it.getString(nameColumnIndex)
    }
}

fun String.getMimeType(): String {
    Log.d("TAG", "getMimeType: $this")
    var l = this.substring(this.lastIndexOf("."))

    return l
}

fun String.isImage(): Boolean {
    var l = this.substring(this.lastIndexOf("."))
    if (l.isNotEmpty()) {
        l = l.lowercase()
        if (l.contains("jpeg")
            || l.contains("jpg")
            || l.contains("png")
            || l.contains("svg")
        ) {
            return true
        }


    }
    return false
}

fun String.nullableToast(context: Context?) {
    context?.let {
        Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
    }
}

fun Int.nullableToast(context: Context?) {
    context?.let {
        context.resources.getString(this)?.toast(context)
        //Toast.makeText(context,this,Toast.LENGTH_SHORT).show()
    }
}

fun String.decode(): String {
    return Base64.decode(this, Base64.DEFAULT).toString(charset("UTF-8"))
}

fun String.encode(): String {
    return Base64.encodeToString(this.toByteArray(charset("UTF-8")), Base64.DEFAULT)
}

fun View.setAllParentsClip(enabled: Boolean) {
    var parent = parent
    while (parent is ViewGroup) {
        parent.clipChildren = enabled
        parent.clipToPadding = enabled
        parent = parent.parent
    }
}

fun EditText.isNotEmpty(): Boolean {
    return !this.text.toString().trim().isEmpty()
}

fun String.convertToEnglishDigits(): String {
    return this.replace("১", "1").replace("২", "2").replace("৩", "3").replace("৪", "4")
        .replace("৫", "5")
        .replace("৬", "6").replace("৭", "7").replace("৮", "8").replace("৯", "9").replace("০", "0")
        .replace(",", "")
}

fun String.convertToEnglishDigitsWithoutComma(): String {
    return this.replace("১", "1").replace("২", "2").replace("৩", "3").replace("৪", "4")
        .replace("৫", "5")
        .replace("৬", "6").replace("৭", "7").replace("৮", "8").replace("৯", "9").replace("০", "0")
}

//FIle Size Extention
val File?.size get() = this?.let { if (!exists()) 0.0 else length().toDouble() } ?: 0.0
val File?.sizeInKb get() = size / 1024
val File?.sizeInMb get() = sizeInKb / 1024
val File?.sizeInGb get() = sizeInMb / 1024
val File?.sizeInTb get() = sizeInGb / 1024

fun Double?.decimalString(): String? {
    return DecimalFormat("##,##,###.#").format(this).convertToEnglishDigitsWithoutComma()
}

fun Int?.decimalString(): String? {
    return DecimalFormat("##,##,###.#").format(this).convertToEnglishDigitsWithoutComma()
}

fun Float?.decimalString(): String? {
    return DecimalFormat("##,##,###.#").format(this).convertToEnglishDigitsWithoutComma()
}

fun String?.decimalString(): String? {
    return try {

        var doubleValue = this?.convertToEnglishDigits()?.toDoubleOrNull() ?: 0.0
        DecimalFormat("##,##,###.#").format(doubleValue)

    } catch (e: Exception) {
        return this
    }

}

fun getDate(dateString: String): Date? {
    val format = SimpleDateFormat("dd-MM-yyyy")
    return try {
        val date: Date? = format.parse(dateString)
        System.out.println(date)
        date
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun getCurrentDate(): String {
    val sdf = SimpleDateFormat("dd-MM-yyyy")
    return sdf.format(Date())
}
