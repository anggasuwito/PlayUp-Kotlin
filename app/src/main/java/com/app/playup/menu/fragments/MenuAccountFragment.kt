package com.app.playup.menu.fragments

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.database.Cursor
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.navigation.findNavController
import com.app.playup.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_menu_account.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class MenuAccountFragment : Fragment(), View.OnClickListener {
    var sharedPreferences: SharedPreferences? = null
    val SELECT_FILE_FROM_STORAGE = 66
    val OPEN_CAMERA_REQUEST_CODE = 13
    lateinit var photoFile: File
    lateinit var currentPhotoPath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = activity?.getSharedPreferences(
            getString(R.string.shared_preference_name),
            Context.MODE_PRIVATE
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val username = sharedPreferences?.getString(
            getString(R.string.username_key),
            getString(R.string.default_value)
        )
        val loginMethod = sharedPreferences?.getString(
            getString(R.string.login_method_key),
            getString(R.string.default_value)
        )
        menuAccountText.text = "$username\nMatch : 100\nRank : 70\nLogin : $loginMethod"
        menuAccountLogout.setOnClickListener(this)
        menuAccountSettingProfile.setOnClickListener(this)
        menuAccountImage.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            menuAccountLogout -> {
                with(sharedPreferences?.edit()) {
                    this?.remove(getString(R.string.username_key))
                    this?.remove(getString(R.string.login_method_key))
                    this?.commit()
                }
                activity?.finish()
            }
            menuAccountSettingProfile -> {
                v?.findNavController()?.navigate(R.id.action_global_userChangeProfileActivity)
            }
            menuAccountImage -> {
                val changeImageDialog = AlertDialog.Builder(this.context)
                changeImageDialog.setTitle(R.string.change_photo_prompt).setItems(
                    R.array.change_photo_arrays,
                    DialogInterface.OnClickListener { dialog, selectedOption ->
                        //selectedOption menunjukan index item
                        if (selectedOption == 0) {
                            openCamera()
                        } else if (selectedOption == 1) {
                            browseImageFile()
                        }
                    }).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == OPEN_CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = BitmapFactory.decodeFile(photoFile.absolutePath)
            menuAccountImage.setImageBitmap(imageBitmap)
        }
        if (requestCode == SELECT_FILE_FROM_STORAGE && resultCode == Activity.RESULT_OK) {
            val originalPath = getOriginalPathFromUri(data?.data!!)
            val imageFile: File = File(originalPath)
            val imageBitmap = BitmapFactory.decodeFile(imageFile.absolutePath)
            menuAccountImage.setImageBitmap(imageBitmap)
        }
    }

    fun getOriginalPathFromUri(contentUri: Uri): String? {
        var originalPath: String? = null
        val projection =
            arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor? =
            activity?.contentResolver?.query(contentUri, projection, null, null, null)
        if (cursor?.moveToFirst()!!) {
            val columnIndex: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            originalPath = cursor.getString(columnIndex)
        }
        return originalPath
    }

    private fun browseImageFile() {
        val selectFileIntent =
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(selectFileIntent, SELECT_FILE_FROM_STORAGE)
    }

    fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.resolveActivity(this.requireActivity().packageManager)
        photoFile = createImageFile()
        val photoURI =
            FileProvider.getUriForFile(
                this.requireContext(),
               "com.app.playup.fileprovider",
                photoFile
            )
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
        startActivityForResult(cameraIntent, OPEN_CAMERA_REQUEST_CODE)
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!

        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            currentPhotoPath = absolutePath
            println("COBA = " + currentPhotoPath)
        }
    }
}

