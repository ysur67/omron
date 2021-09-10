package com.example.omron.utils.fragment

import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar


fun Fragment.makeSnack(text: String) {
    Snackbar.make(view!!, text, Snackbar.LENGTH_LONG).show()
}
