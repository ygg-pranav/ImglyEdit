package com.example.photo_video

import android.net.Uri

typealias EditCompletionCallback = (editedMediaUri: Uri, sourceMediaUri: Uri) -> Unit

typealias EditCompletionCallbackWithMedia = (editedMediaUri: Uri, sourceMediaUri: Uri, MediaAction) -> Unit