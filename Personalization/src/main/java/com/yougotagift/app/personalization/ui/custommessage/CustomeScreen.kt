package com.yougotagift.app.personalization.ui.custommessage

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun CustomMessageScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Message screen",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}