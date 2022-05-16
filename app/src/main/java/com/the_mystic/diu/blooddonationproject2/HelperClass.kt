package com.the_mystic.diu.blooddonationproject2

import android.app.ProgressDialog
import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kenmeidearu.searchablespinnerlibrary.mListString
import com.the_mystic.diu.blooddonationproject2.model.LocationModel
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

class HelperClass {
    companion object {

        fun getDistrict(ctx: Context): ArrayList<mListString> {
            var disList: List<LocationModel> = emptyList()
            val jsonStr = loadJSONFile(ctx, "district.json")
            if (jsonStr != null) {
                disList =
                    Gson().fromJson(jsonStr, object : TypeToken<List<LocationModel>>() {}.type)
            }
            val newList = ArrayList<mListString>()

            for (item in disList) {
                item.name?.let {
                    item.id?.toInt()?.let { it1 ->
                        mListString(
                            it1, item.name
                        )
                    }?.let { it2 ->
                        newList.add(
                            it2
                        )
                    }
                }
            }
            return newList
        }


        private fun loadJSONFile(ctx: Context, fileName: String): String? {
            var json: String? = ""
            json = try {
                val inputStream: InputStream = ctx.assets.open(fileName)
                val size: Int = inputStream.available()
                val byteArray = ByteArray(size)
                inputStream.read(byteArray)
                inputStream.close()
                String(byteArray, Charset.forName("UTF-8"))
            } catch (e: IOException) {
                e.printStackTrace()
                return null
            }
            return json
        }

        fun createProgressDialog(context: Context, msg: String): ProgressDialog {
            val progress: ProgressDialog = ProgressDialog(
                context
            )
            progress.setMessage(msg)
            progress.setCancelable(false)
            return progress
        }

    }
}