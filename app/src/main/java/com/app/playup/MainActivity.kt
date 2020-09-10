package com.app.playup

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {
    val REQUEST_READ_STORAGE_PERMISSION = 201
    val REQUEST_OPEN_CAMERA_PERMISSION = 202
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        firstStoragePermission()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty()) {
            if (requestCode == REQUEST_READ_STORAGE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                secondCameraPermission()
            } else if (requestCode == REQUEST_READ_STORAGE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(this, "Mohon berikan perizinan", Toast.LENGTH_SHORT).show()
                firstStoragePermission()
            }
        }
    }

    fun firstStoragePermission() {
        if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                arrayOf(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ), REQUEST_READ_STORAGE_PERMISSION
            )
        }
    }

    fun secondCameraPermission() {
        if (checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                arrayOf(
                    android.Manifest.permission.CAMERA
                ), REQUEST_OPEN_CAMERA_PERMISSION
            )
        }
    }
}