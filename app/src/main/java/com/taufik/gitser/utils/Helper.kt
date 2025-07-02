package com.taufik.gitser.utils

import android.view.View
import android.view.Window
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat

fun enableWindowInsets(
    window: Window,
    bindingRoot: View,
    left: Int? = null,
    right: Int? = null,
    bottom: Int? = null,
    top: Int? = null,
) {
    ViewCompat.setOnApplyWindowInsetsListener(bindingRoot) { view, windowInsets ->
        val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
        view.setPadding(
            left ?: insets.left,
            top ?: insets.top,
            right ?: insets.right,
            bottom ?: insets.bottom
        )
        val windowInsetsController = WindowCompat.getInsetsController(window, view)
        windowInsetsController.isAppearanceLightStatusBars = false
        windowInsets
    }
}