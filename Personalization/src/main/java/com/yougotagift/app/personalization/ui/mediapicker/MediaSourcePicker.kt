package com.yougotagift.app.personalization.ui.mediapicker

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.photo_video.MediaType

@Composable
fun MediaSourcePicker(
    mediaType: MediaType,
    fromCameraClicked: () -> Unit,
    fromGalleryClicked: () -> Unit,
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .fillMaxWidth()
            .wrapContentHeight()
            .background(Color(0xfff2f5f8))
            .padding(horizontal = 16.dp)
            .padding(top = 30.dp, bottom = 48.dp)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = when (mediaType) {
                MediaType.PHOTO -> "Add Photo"
                MediaType.VIDEO -> "Add Video"
            },
            style = TextStyle(
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                color = Color.Red,
                textAlign = TextAlign.Center
            )
        )
        Spacer(modifier = Modifier.height(27.dp))
        OptionContainer(
            label = when (mediaType) {
                MediaType.PHOTO -> "Take photo"
                MediaType.VIDEO -> "Choose from library"
            },
            iconRes = android.R.drawable.ic_menu_camera,
            onClick = fromCameraClicked
        )
        Spacer(modifier = Modifier.height(10.dp))
        OptionContainer(
            label = when (mediaType) {
                MediaType.PHOTO -> "Take video"
                MediaType.VIDEO -> "Choose from library"
            },
            iconRes = android.R.drawable.ic_menu_view,
            onClick = fromGalleryClicked
        )
    }
}

@Composable
private fun OptionContainer(
    label: String,
    @DrawableRes iconRes: Int,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .background(shape = RoundedCornerShape(16.dp), color = Color.White)
            .fillMaxWidth()
            .clickable {
                onClick()
            }
            .padding(vertical = 30.dp, horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                tint = Color.Green,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = label,
                style = TextStyle(
                    fontFamily = FontFamily.Cursive,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 18.sp,
                    color = Color.Red
                )
            )
        }

    }
}