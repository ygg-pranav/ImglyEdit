package com.yougotagift.app.personalization.ui

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.photo_video.PickerState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class PersonalisationLandingViewModel @Inject constructor() : ViewModel() {



    /**
     * The UI state.
     *
     * @param photoUri - holds the uri of selected photo
     * @param videoUri - holds the uri of picked video
     * @param shouldPreserveTheState - If set to true photo picker state will pick last updated state
     * @param mediaUri - Holds the uri of selected media
     */
    data class UIState(
        val pickerState: PickerState,
        val photoUri: String? = null,
        val videoUri: String? = null,
        val shouldPreserveTheState: Boolean = false,
        val mediaUri: Uri? = null,
    )


    private val _uiState: MutableStateFlow<UIState> = MutableStateFlow(
        UIState(
            pickerState = PickerState.Selection()
        )
    )
    val uiState = _uiState.asStateFlow()



    fun onStateChanged(pickerState: PickerState) {
        _uiState.update {
            it.copy(
                pickerState = pickerState
            )
        }
    }

    fun onCameraPermissionDenied() {
        require(uiState.value.pickerState is PickerState.Selection) {
            "Expected PickerState.Selection"
        }
        onStateChanged(
            pickerState = (uiState.value.pickerState as PickerState.Selection).copy(
                shouldShowCameraPermissionDialog = true,
            )
        )
    }

}
