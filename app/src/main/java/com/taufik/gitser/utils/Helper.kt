package com.taufik.gitser.utils

import android.view.View
import android.view.Window
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat

fun applyEdgeToEdgeInsets(
    window: Window,
    root: View,
    consumeInsets: Boolean = true,
    onInsetsApplied: (View, WindowInsetsCompat, Insets) -> Unit = { view, _, insets ->
        view.setPadding(
            insets.left,
            insets.top,
            insets.right,
            insets.bottom
        )
    }
) {
    ViewCompat.setOnApplyWindowInsetsListener(root) { view, windowInsets ->
        val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
        onInsetsApplied(view, windowInsets, insets)

        val controller = WindowCompat.getInsetsController(window, view)
        controller.isAppearanceLightStatusBars = true
        controller.isAppearanceLightNavigationBars = true

        if (consumeInsets) {
            WindowInsetsCompat.CONSUMED
        } else {
            windowInsets
        }
    }
}