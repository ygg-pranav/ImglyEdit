package com.example.photo_video

import android.Manifest
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import ly.img.android.pesdk.PhotoEditorSettingsList
import ly.img.android.pesdk.VideoEditorSettingsList
import ly.img.android.pesdk.backend.model.EditorSDKResult
import ly.img.android.pesdk.backend.model.constant.ImageExportFormat
import ly.img.android.pesdk.backend.model.state.LoadSettings
import ly.img.android.pesdk.backend.model.state.PhotoEditorSaveSettings
import ly.img.android.pesdk.backend.model.state.TrimSettings
import ly.img.android.pesdk.backend.model.state.VideoEditorSaveSettings
import ly.img.android.pesdk.backend.model.state.manager.SettingsList
import ly.img.android.pesdk.backend.model.state.manager.SettingsList.DEBUG_SETTINGS_LIST_CREATOR
import ly.img.android.pesdk.ui.activity.PhotoEditorActivityResultContract
import ly.img.android.pesdk.ui.activity.VideoEditorActivityResultContract
import java.io.File
import java.util.concurrent.TimeUnit

class MediaPickerObserver(
    private val onPermissionDenied: () -> Unit,
    private val activity: ComponentActivity,
    private val onEditCompleted: EditCompletionCallbackWithMedia,
    private val onEditCancelled: (MediaAction) -> Unit,
    private val onEditFailed: (MediaAction) -> Unit,
) {

    private val getContent = MediaPickerLifeCycleObserver(
        activity.activityResultRegistry,
        onPermissionDenied = {
            onPermissionDenied()
        },
        onPermissionEnabled = { mediaAction ->
            when (mediaAction) {
                MediaAction.OPEN_PHOTO_GALLERY -> {
                    getPhotoFromMedia()
                }

                MediaAction.OPEN_VIDEO_GALLERY -> {
                    getVideoFromMedia()
                }

                MediaAction.OPEN_PHOTO_CAMERA -> {
                    takePhotoFromCamera()
                }

                MediaAction.OPEN_VIDEO_CAMERA -> {
                    takeVideoFromCamera()
                }
            }
        },
        onUriReceived = { uri: Uri, mediaAction: MediaAction ->
            when (mediaAction) {
                MediaAction.OPEN_PHOTO_GALLERY,
                MediaAction.OPEN_PHOTO_CAMERA,
                -> {
                    startEditorForImage(sourceMediaUri = uri)
                }

                else -> {
                    startEditorForVideo(sourceMediaUri = uri)
                }
            }
        },
        onEditCompleted = { sourceMediaUri: Uri, editedMediaUri: Uri, mediaAction: MediaAction ->
            onEditCompleted(editedMediaUri, sourceMediaUri, mediaAction)
        },
        onEditFailed = { mediaAction: MediaAction ->
            onEditFailed(mediaAction)
        },
        onEditCancelled = { mediaAction: MediaAction ->
            onEditCancelled(mediaAction)
        }
    ).also {
        activity.lifecycle.addObserver(it)
    }

    fun checkPermissionEnabled(mediaAction: MediaAction) {
        getContent.checkPermissionEnabled(mediaAction = mediaAction)
    }

    private fun getPhotoFromMedia() {
        getContent.selectImage()
    }

    private fun getVideoFromMedia() {
        getContent.selectVideo()
    }

    private fun takePhotoFromCamera() {
        getContent.takePhotoFromCamera()
    }

    private fun takeVideoFromCamera() {
        getContent.takeVideoFromCamera()
    }

    private fun startEditorForImage(sourceMediaUri: Uri) {
        getContent.getEditorLauncherForImage(imageUri = sourceMediaUri)
    }

    private fun startEditorForVideo(sourceMediaUri: Uri) {
        getContent.getEditorLauncherForVideo(videoUri = sourceMediaUri)
    }

    fun startEditor(mediaAction: MediaAction, sourceMediaUri: Uri) {
        getContent.setMediaAction(mediaAction = mediaAction)
        when (mediaAction) {
            MediaAction.OPEN_VIDEO_GALLERY,
            MediaAction.OPEN_VIDEO_CAMERA,
            -> {
                startEditorForVideo(sourceMediaUri = sourceMediaUri)
            }

            else -> {
                startEditorForImage(sourceMediaUri = sourceMediaUri)
            }
        }
    }

    inner class MediaPickerLifeCycleObserver(
        private val registry: ActivityResultRegistry,
        private val onPermissionDenied: () -> Unit,
        private val onPermissionEnabled: (MediaAction) -> Unit,
        private val onUriReceived: (Uri, MediaAction) -> Unit,
        private val onEditCompleted: (sourceMediaUri: Uri, editedMediaUri: Uri, mediaAction: MediaAction) -> Unit,
        private val onEditCancelled: (MediaAction) -> Unit,
        private val onEditFailed: (MediaAction) -> Unit,
    ) : DefaultLifecycleObserver {

        private lateinit var mediaAction: MediaAction

        private lateinit var file: File

        private lateinit var getPermissions: ActivityResultLauncher<String>

        private lateinit var getVideo: ActivityResultLauncher<PickVisualMediaRequest>

        private lateinit var getPhoto: ActivityResultLauncher<PickVisualMediaRequest>

        private lateinit var getPhotoFromCamera: ActivityResultLauncher<Uri>

        private lateinit var getVideoFromCamera: ActivityResultLauncher<Uri>

        private lateinit var getPhotoEditor: ActivityResultLauncher<SettingsList>

        private lateinit var getVideoEditor: ActivityResultLauncher<SettingsList>

        override fun onCreate(owner: LifecycleOwner) {

            getPermissions = registry.register(
                "get_permission",
                owner,
                ActivityResultContracts.RequestPermission()
            ) {
                if (it) {
                    onPermissionEnabled.invoke(mediaAction)
                } else {
                    onPermissionDenied()
                }
            }

            getPhoto = registry.register(
                "pick_camera",
                owner,
                ActivityResultContracts.PickVisualMedia()
            ) {
                it?.let {
                    onUriReceived(it, mediaAction)
                }
            }

            getVideo = registry.register(
                "pick_video",
                owner,
                ActivityResultContracts.PickVisualMedia()
            ) {
                it?.let {
                    onUriReceived(it, mediaAction)
                }
            }

            getPhotoFromCamera = registry.register(
                "take_photo",
                owner,
                ActivityResultContracts.TakePicture()
            ) {
                if (it) {
                    onUriReceived(Uri.fromFile(file), mediaAction)
                }
            }

            getVideoFromCamera = registry.register(
                "take_video",
                owner,
                ActivityResultContracts.CaptureVideo()
            ) {
                if (it) {
                    onUriReceived(Uri.fromFile(file), mediaAction)
                }
            }

            getPhotoEditor = registry.register(
                "editor_img",
                owner,
                PhotoEditorActivityResultContract()
            ) { result ->
                when (result.resultStatus) {
                    EditorSDKResult.Status.CANCELED -> {
                        onEditCancelled(mediaAction)
                    }

                    EditorSDKResult.Status.EXPORT_DONE -> {
                        if (result.sourceUri != null && (result.resultUri != null)) {
                            onEditCompleted(
                                result.sourceUri!!,
                                result.resultUri!!,
                                mediaAction
                            )
                        } else {
                            onEditFailed(mediaAction)
                        }
                    }

                    else -> {
                        onEditFailed(mediaAction)
                    }
                }
            }

            getVideoEditor = registry.register(
                "editor_video",
                owner,
                VideoEditorActivityResultContract()
            ) { result ->
                when (result.resultStatus) {
                    EditorSDKResult.Status.CANCELED -> {
                        onEditCancelled(mediaAction)
                    }

                    EditorSDKResult.Status.EXPORT_DONE -> {
                        if (result.sourceUri != null && (result.resultUri != null)) {
                            onEditCompleted(
                                result.sourceUri!!,
                                result.resultUri!!,
                                mediaAction
                            )
                        } else {
                            onEditFailed(mediaAction)
                        }
                    }

                    else -> {
                        onEditFailed(mediaAction)
                    }
                }
            }
        }

        fun checkPermissionEnabled(mediaAction: MediaAction) {
            this.mediaAction = mediaAction
            getPermissions.launch(Manifest.permission.CAMERA)
        }

        fun selectVideo() {
            getVideo.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.VideoOnly))
        }

        fun selectImage() {
            getPhoto.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        fun takePhotoFromCamera() {
            file = File(activity.cacheDir, "imgly_photo.jpg")
            getPhotoFromCamera.launch(
                FileProvider.getUriForFile(
                    activity,
                    activity.getString(R.string.media_content_provider_authority),
                    file
                )
            )
        }

        fun takeVideoFromCamera() {
            file = File(activity.cacheDir, "imgly_video.mp4")
            getVideoFromCamera.launch(
                FileProvider.getUriForFile(
                    activity,
                    activity.getString(R.string.media_content_provider_authority),
                    file
                )
            )
        }

        fun getEditorLauncherForImage(
            imageUri: Uri,
            exportQuality: Int = 80,
        ) {
            val settingsList = getPhotoEditorSettings(
                photoUri = imageUri,
                exportQuality = exportQuality
            )
            getPhotoEditor.launch(settingsList)
            settingsList.release()
        }

        fun getEditorLauncherForVideo(
            videoUri: Uri,
            exportQuality: Long = 30,
        ) {
            val settingsList = getVideoEditorSettings(
                videoUri = videoUri,
                maximumVideoLength = exportQuality
            )
            getVideoEditor.launch(settingsList)
            settingsList.release()
        }

        fun setMediaAction(mediaAction: MediaAction) {
            this.mediaAction = mediaAction
        }

    }
}

enum class MediaAction {
    OPEN_PHOTO_GALLERY, OPEN_PHOTO_CAMERA, OPEN_VIDEO_GALLERY, OPEN_VIDEO_CAMERA;

    companion object {
        fun isPhotoAction(mediaAction: MediaAction) = when (mediaAction) {
            OPEN_PHOTO_GALLERY,
            OPEN_PHOTO_CAMERA,
            -> {
                true
            }

            else -> false
        }
    }
}

enum class MediaType {
    PHOTO,
    VIDEO,
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
    }.apply {
        DEBUG_SETTINGS_LIST_CREATOR = true
    }


private fun getVideoEditorSettings(
    videoUri: Uri,
    maximumVideoLength: Long = 30,
) = VideoEditorSettingsList(false)
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
    }.apply {
        DEBUG_SETTINGS_LIST_CREATOR = true
    }

sealed class PickerState {

    /**
     * The picker is loading.
     */
    object Loading : PickerState()

    /**
     * State where the select photo or video prompt is shown.
     *
     * @param shouldShowCameraPermissionDialog Determine whether dialog demanding camera permission should be shown.
     */
    data class Selection(
        val shouldShowCameraPermissionDialog: Boolean = false,
    ) : PickerState()

    /**
     * State where the selected photo or video is previewed with the edits shown.
     *
     * @param mediaAction The [MediaAction] that triggered this preview.
     * @param editedMediaUri The [Uri] of the Edited media.
     * @param sourceMediaUri The [Uri] of the original media.
     */
    data class Preview(
        val mediaAction: MediaAction,
        val editedMediaUri: Uri,
        val sourceMediaUri: Uri,
    ) : PickerState()
}

