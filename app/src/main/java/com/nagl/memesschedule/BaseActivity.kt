package com.nagl.memesschedule

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject


abstract class BaseActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactoryProvider: ViewModelProvider.Factory

    fun showShortSnackBar(message: String, view: View) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }

//    fun hideSoftKeyboard() {
//        val inputMethodManager: InputMethodManager = this.getSystemService(
//            INPUT_METHOD_SERVICE
//        ) as InputMethodManager
//        if (inputMethodManager.isAcceptingText) {
//            inputMethodManager.hideSoftInputFromWindow(
//                this.currentFocus!!.windowToken,
//                0
//            )
//        }
//    }

    fun showLongSnackBar(message: String, view: View) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
    }
}

fun Activity.hideSoftKeyboard() {
    currentFocus?.let {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(it.windowToken, 0)
    }
}