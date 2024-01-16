package com.yougotagift.app.personalization.ui.mediapicker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yougotagift.app.personalization.R

@Composable
fun CircularDivider(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = Modifier
            .size(60.dp)
            .background(
                color = Color(0xfff2f5f8),
                shape = CircleShape,
            )
            .then(modifier),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.divider_button_label),
            style = TextStyle(
                fontFamily = FontFamily.Cursive,
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
                lineHeight = 18.sp,
                textAlign = TextAlign.Center
            )
        )
    }
}
