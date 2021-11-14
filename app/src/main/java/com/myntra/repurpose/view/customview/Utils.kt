package com.myntra.repurpose.view.customview

import android.app.Activity
import android.content.ContentResolver
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.core.app.ActivityCompat
import java.io.File
import java.util.*

var toast: Toast? = null

const val sharedPrefFile = "repurposeSharedPreferences"

typealias PrefEditor = SharedPreferences.Editor

internal inline fun SharedPreferences.commit(crossinline exec: PrefEditor.() -> Unit) {
  val editor = this.edit()
  editor.exec()
  editor.apply()
}

const val BASE_URL = "https://api-repurpose.herokuapp.com"

internal fun Activity.toast(message: CharSequence) {
  toast?.cancel()
  toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
      .apply { show() }
}

fun getFileFromUri(contentResolver: ContentResolver, uri: Uri, directory: File): File {
  val suffix: String = if(contentResolver.getType(uri)!!.startsWith("image/")){
    ".jpeg"
  } else{
    ".mp4"
  }
  val file = File.createTempFile("prefix", suffix, directory)
  file.outputStream().use {
    contentResolver.openInputStream(uri)?.copyTo(it)
  }

  return file
}

fun askForPermissions(activity: Activity, permissions: Array<String>, requestCode: Int): Boolean {
  val permissionsToRequest: MutableList<String> = ArrayList()
  for (permission in permissions) {
    if (ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
      permissionsToRequest.add(permission)
    }
  }
  if (permissionsToRequest.isEmpty()) {
    return false
  }
  if (permissionsToRequest.isNotEmpty()) {
    ActivityCompat.requestPermissions(activity, permissionsToRequest.toTypedArray(), requestCode)
  }
  return true
}