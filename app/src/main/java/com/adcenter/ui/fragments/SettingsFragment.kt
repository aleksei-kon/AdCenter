package com.adcenter.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.adcenter.R
import com.adcenter.app.App
import com.adcenter.app.config.AppConfig
import com.adcenter.entities.view.AppConfigInfo
import com.adcenter.extensions.gone
import com.adcenter.extensions.visible
import com.adcenter.ui.IPageConfiguration
import com.adcenter.ui.IPageConfiguration.ToolbarScrollBehaviour
import com.adcenter.ui.activities.DevSettingsActivity
import com.adcenter.ui.activities.LoginActivity
import com.adcenter.ui.theme.IThemeManager
import kotlinx.android.synthetic.main.fragment_settings.*
import javax.inject.Inject

class SettingsFragment : BaseFragment(), IPageConfiguration {

    override val toolbarTitle: String by lazy { getString(R.string.settings_title) }

    override val layout: Int = R.layout.fragment_settings

    override val toolbarScrollBehaviour: ToolbarScrollBehaviour = ToolbarScrollBehaviour.POSITIONED

    @Inject
    lateinit var themeManager: IThemeManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        App.appComponent.inject(this)

        if (AppConfig.isLoggedIn) {
            loginButton.gone()
            logoutButton.visible()
        } else {
            loginButton.visible()
            logoutButton.gone()
        }

        loginButton.setOnClickListener {
            requireContext().startActivity(
                Intent(
                    requireContext(),
                    LoginActivity::class.java
                )
            )
        }

        logoutButton.setOnClickListener {
            AppConfig.updateConfig(AppConfigInfo())
            requireActivity().recreate()
        }

        switchThemeButton.setOnClickListener {
            themeManager.switchTheme()
            requireActivity().recreate()
        }

        devSettingsButton.setOnClickListener {
            requireContext().startActivity(
                Intent(
                    requireContext(),
                    DevSettingsActivity::class.java
                )
            )
        }
    }
}


/*

private fun pickFromGallery() {
        val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(takePicture, 0)
    }

    private fun pickFromCamera() {
        val pickPhoto = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        startActivityForResult(pickPhoto, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {
            val selectedImageUri = data?.data
            imageView.setImageURI(selectedImageUri)

            val file = File(getRealPathFromURI(requireContext(), selectedImageUri!!))

            Thread(Runnable {
                val client = OkHttpClient()

                val requestBody = MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart(
                        "uploadFile", file.name,
                        RequestBody.create(MediaType.parse("image/jpg"), file)
                    )
                    .build()

                val request = Request.Builder()
                    .url("http://192.168.100.38:8080/image/upload")
                    .post(requestBody)
                    .build()

                val response = client.newCall(request).execute()
                val b = response.body()
            }).start()
        }
    }

    fun getRealPathFromURI(context: Context, contentUri: Uri): String {
        var cursor: Cursor? = null
        try {
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            cursor = context.contentResolver.query(contentUri, proj, null, null, null)
            val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            return cursor.getString(column_index)
        } finally {
            cursor?.close()
        }
    }
 */