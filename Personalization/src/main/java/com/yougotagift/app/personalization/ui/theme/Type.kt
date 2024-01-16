@file:Suppress("unused")

package com.yougotagift.app.personalization.ui.theme

import androidx.compose.material.Text
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp


internal val ModuleTypography = Typography(
    h6 = TextStyle(
        fontFamily = FontFamily.Cursive,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 22.sp,
        color = ModuleTextColorOne
    ),
    button = TextStyle(
        fontFamily = FontFamily.Cursive,
        fontWeight = FontWeight.Bold,
        lineHeight = 27.sp,
        fontSize = 16.sp,
        color = Color.White
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Cursive,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        letterSpacing = 0.12.sp,
        color = Color.Blue
    ),
    overline = TextStyle(
        fontFamily = FontFamily.Cursive,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
        letterSpacing = 0.1.sp,
        color = ModuleSecondary
    ),
    body1 =  TextStyle(
        fontFamily = FontFamily.Cursive,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        color = ModuleTextColorOne
    ),
    body2 =  TextStyle(
        fontFamily = FontFamily.Cursive,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        color = ModuleTextColorTwo
    ),
    subtitle1 = TextStyle(
        fontFamily = FontFamily.Cursive,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        color = ModuleTextColorOne
    )
)


@Composable
internal fun ModuleOverLineText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = ModuleTypography.overline.color
) {
    Text(
        text = text,
        style = ModuleTypography.overline,
        modifier = modifier,
        color = color
    )
}

@Composable
internal fun ModuleBody1Text(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = ModuleTypography.body1.color
) {
    Text(
        text = text,
        style = ModuleTypography.body1,
        modifier = modifier,
        color = color
    )
}

@Composable
internal fun ModuleBody2Text(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = ModuleTypography.body2.color
) {
    Text(
        text = text,
        style = ModuleTypography.body2,
        color = color,
        modifier = modifier
    )
}

@Composable
internal fun ModuleSubtitle1Text(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = ModuleTypography.subtitle1,
        modifier = modifier
    )
}

@Composable
internal fun ModuleH6Text(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = ModuleTypography.h6.color
) {
    Text(
        text = text,
        style = ModuleTypography.h6,
        modifier = modifier,
        color = color,
        textAlign = TextAlign.Center
    )
}