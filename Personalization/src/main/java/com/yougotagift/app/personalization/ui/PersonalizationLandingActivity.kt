package com.yougotagift.app.personalization.ui

import android.net.Uri
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.photo_video.MediaAction
import com.example.photo_video.MediaPickerObserver
import com.example.photo_video.PickerState
import com.yougotagift.app.personalization.ui.custommessage.CustomMessageScreen
import com.yougotagift.app.personalization.ui.mediapicker.MediaPickerScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class PersonalizationLandingActivity : ComponentActivity() {

    private val viewModel: PersonalisationLandingViewModel by viewModels()

    private lateinit var navController: NavHostController

    private lateinit var observer: MediaPickerObserver



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        observer = MediaPickerObserver(
            activity = this@PersonalizationLandingActivity,
            onPermissionDenied = {
                viewModel.onCameraPermissionDenied()
            },
            onEditCompleted = { editedMediaUri: Uri, sourceMediaUri: Uri, mediaAction: MediaAction ->
                viewModel.onStateChanged(
                    pickerState = PickerState.Preview(
                        mediaAction = mediaAction,
                        editedMediaUri = editedMediaUri,
                        sourceMediaUri = sourceMediaUri
                    )
                )
            },
            onEditCancelled = {

            },
            onEditFailed = {

            }
        )
        setContent {
            navController = rememberNavController()

            val uiState by viewModel.uiState.collectAsState()

            NavHost(
                navController = navController,
                startDestination = NavigationScreen.MediaPicker.route
            ) {

                composable(route = NavigationScreen.MediaPicker.route) {
                    MediaPickerScreen(
                        pickerState = uiState.pickerState,
                        onMediaTypePicked = { mediaAction ->
                            observer.checkPermissionEnabled(mediaAction = mediaAction)
                        },
                        onAddMediaSkipped = {
                            navController.navigate(route = NavigationScreen.CustomMessage.route)
                        },
                        onEditClickContinue = { _ ->
                            viewModel.onStateChanged(pickerState = PickerState.Selection())
                            navController.navigate(route = NavigationScreen.CustomMessage.route)
                        },
                        onClickNavigateBack = {
                            navController.popBackStack()
                        }
                    )
                }

                composable(route = NavigationScreen.CustomMessage.route) {
                    CustomMessageScreen()
                }

            }
        }
    }
}