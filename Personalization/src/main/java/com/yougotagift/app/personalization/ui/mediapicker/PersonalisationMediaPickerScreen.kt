package com.yougotagift.app.personalization.ui.mediapicker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yougotagift.app.personalization.R

@Composable
fun PersonalisationMediaPickerScreen(
    modifier: Modifier = Modifier,
    onClickAddPhoto: () -> Unit,
    onClickAddVideo: () -> Unit,
) {
    Column(
        modifier = Modifier
            .background(Color(0xfff2f5f8))
            .systemBarsPadding()
            .fillMaxSize()
            .then(modifier),
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Column {
                    listOf(
                        ButtonSpecs(
                            buttonTitleResId = R.string.add_photo_button_label,
                            buttonIconDrawableResId = android.R.drawable.ic_menu_camera,
                            onClick = onClickAddPhoto
                        ),
                        ButtonSpecs(
                            buttonTitleResId = R.string.add_video_button_label,
                            buttonIconDrawableResId = android.R.drawable.ic_menu_camera,
                            onClick = onClickAddVideo
                        )
                    ).forEach {
                        AddMediaButton(
                            buttonSpecs = it
                        )
                    }
                }
                CircularDivider(
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
            Spacer(
                modifier = Modifier.padding(
                    top = 30.dp,
                    bottom = 30.dp
                )
            )
        }
    }
}