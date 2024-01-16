package com.yougotagift.app.personalization.ui.mediapicker

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import com.example.photo_video.MediaAction
import com.example.photo_video.MediaType
import com.example.photo_video.PickerState

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MediaPickerScreen(
    pickerState: PickerState,
    onMediaTypePicked: (MediaAction) -> Unit,
    onAddMediaSkipped: () -> Unit,
    onEditClickContinue: (Uri) -> Unit,
    onClickNavigateBack: () -> Unit,
) {

    var mediaType by remember { mutableStateOf(MediaType.PHOTO) }
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true,
    )
    val coroutineScope = rememberCoroutineScope()

    ModalBottomSheetLayout(
        sheetBackgroundColor = Color.Transparent,
        sheetContent = {
            MediaSourcePicker(
                mediaType = mediaType,
                fromCameraClicked = {
                    coroutineScope.launch {
                        sheetState.hide()
                    }
                    when (mediaType) {
                        MediaType.PHOTO -> onMediaTypePicked(MediaAction.OPEN_PHOTO_CAMERA)
                        MediaType.VIDEO -> onMediaTypePicked(MediaAction.OPEN_VIDEO_CAMERA)
                    }
                },
                fromGalleryClicked = {
                    coroutineScope.launch {
                        sheetState.hide()
                    }
                    when (mediaType) {
                        MediaType.PHOTO -> onMediaTypePicked(MediaAction.OPEN_PHOTO_GALLERY)
                        MediaType.VIDEO -> onMediaTypePicked(MediaAction.OPEN_VIDEO_GALLERY)
                    }
                }
            )
        },
        sheetState = sheetState
    ) {
        when (pickerState) {
            is PickerState.Selection -> {
                PersonalisationMediaPickerScreen(
                    onClickAddPhoto = {
                        mediaType = MediaType.PHOTO
                        coroutineScope.launch {
                            sheetState.show()
                        }
                    },
                    onClickAddVideo = {
                        mediaType = MediaType.VIDEO
                        coroutineScope.launch {
                            sheetState.show()
                        }
                    }
                )
            }

            is PickerState.Preview -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = "Click for preview",
                        modifier = Modifier
                            .padding(
                                vertical = 32.dp,
                                horizontal = 16.dp
                            )
                            .clickable {
                                onEditClickContinue(pickerState.editedMediaUri)
                            }
                            .align(Alignment.Center)
                    )
                }
            }

            PickerState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {

                }
            }
        }
    }
}
