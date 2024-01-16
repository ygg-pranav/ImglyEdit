package com.example.photo_video

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.fragment.app.Fragment
import ly.img.android.pesdk.PhotoEditorSettingsList
import ly.img.android.pesdk.VideoEditorSettingsList
import ly.img.android.pesdk.backend.model.constant.ImageExportFormat
import ly.img.android.pesdk.backend.model.state.LoadSettings
import ly.img.android.pesdk.backend.model.state.PhotoEditorSaveSettings
import ly.img.android.pesdk.backend.model.state.TrimSettings
import ly.img.android.pesdk.backend.model.state.VideoEditorSaveSettings
import ly.img.android.pesdk.ui.activity.PhotoEditorBuilder
import ly.img.android.pesdk.ui.activity.VideoEditorBuilder
import java.util.concurrent.TimeUnit

fun Context.showMessage(message: String) {
    Toast.makeText(this.applicationContext, message, Toast.LENGTH_LONG).show()
}

const val EDITOR_REQUEST_CODE = 0x42

/**
 * Show video editor in the context of a [Fragment].
 * Read more about SDK [here](https://img.ly/docs/vesdk/android/getting-started/integration/)
 *
 * @param videoUri The URI of the video.
 * @param maximumVideoLength The maximum permissible video length. The video would be trimmed
 * if original length exceeds this. Default requirement is 30s.
 *
 * @receiver The originated [Fragment]
 */
internal fun Fragment.startVideoEditor(
    videoUri: Uri,
    maximumVideoLength: Long = 30,
) {
    val settingsList = VideoEditorSettingsList(false)
        .configure<LoadSettings> {
            it.source = videoUri
        }
        .configure<TrimSettings> {
            it.setMaximumVideoLength(maximumVideoLength, TimeUnit.SECONDS)
            it.forceTrimMode = TrimSettings.ForceTrim.IF_NEEDED
        }
        .configure<VideoEditorSaveSettings> {
            //Recommended bits/pixel rate for good quality/size balance
            it.bitsPerPixel = 0.04f
        }
    VideoEditorBuilder(this.requireActivity())
        .setSettingsList(settingsList)
        .startActivityForResult(this, EDITOR_REQUEST_CODE)
    settingsList.release()
}

/**
 * Show photo editor in the context of a [Fragment].
 * Read more about SDK [here](https://img.ly/docs/pesdk/android/getting-started/integration/)
 *
 * @param photoUri The URI of the photo.
 * @param exportQuality The export quality in percentage w.r.t to the original size.
 * @receiver The originated [Fragment]
 */
internal fun Fragment.startPhotoEditor(
    photoUri: Uri,
    exportQuality: Int = 80,
) {
    val settingsList = getPhotoEditorSettings(photoUri = photoUri, exportQuality = exportQuality)
    PhotoEditorBuilder(this.requireActivity())
        .setSettingsList(settingsList)
        .startActivityForResult(this, EDITOR_REQUEST_CODE)
    settingsList.release()
}

/**
 * Show video editor in the context of an [Activity].
 * Read more about SDK [here](https://img.ly/docs/pesdk/android/getting-started/integration/)
 *
 * @param photoUri The URI of the photo.
 * @param exportQuality The export quality in percentage w.r.t to the original size.
 *
 * @receiver The originated [Activity]
 */
internal fun Activity.startPhotoEditor(
    photoUri: Uri,
    exportQuality: Int = 80,
) {
    val settingsList = getPhotoEditorSettings(photoUri = photoUri, exportQuality = exportQuality)
    PhotoEditorBuilder(this)
        .setSettingsList(settingsList)
        .startActivityForResult(this, EDITOR_REQUEST_CODE)
    settingsList.release()
}

private fun getPhotoEditorSettings(
    photoUri: Uri,
    exportQuality: Int,
): PhotoEditorSettingsList = PhotoEditorSettingsList(false)
    .configure<LoadSettings> {
        it.source = photoUri
    }
    .configure<PhotoEditorSaveSettings> {
        it.setExportFormat(ImageExportFormat.JPEG)
        it.jpegQuality = exportQuality
    }