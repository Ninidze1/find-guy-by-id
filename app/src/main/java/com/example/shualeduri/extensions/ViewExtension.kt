package com.example.shualeduri.extensions

import android.app.Dialog
import android.view.View
import androidx.navigation.NavDirections
import androidx.navigation.findNavController

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.INVISIBLE
}

fun View.fade(action: NavDirections?=null) {
    alpha = 0f
    animate().setDuration(2250).alpha(1f).withEndAction {
        if (action != null) {
            findNavController().navigate(action)
        }
    }
}