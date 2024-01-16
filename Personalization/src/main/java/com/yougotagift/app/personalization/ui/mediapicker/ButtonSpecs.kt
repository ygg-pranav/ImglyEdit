package com.yougotagift.app.personalization.ui.mediapicker

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class ButtonSpecs(
    @StringRes val buttonTitleResId: Int,
    @DrawableRes val buttonIconDrawableResId: Int,
    val onClick: () -> Unit,
)