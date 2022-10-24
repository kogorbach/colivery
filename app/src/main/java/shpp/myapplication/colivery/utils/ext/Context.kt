package shpp.myapplication.colivery.utils.ext

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.toast(@StringRes message: Int) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.toast(@StringRes message: Int, vararg params: Any) {
    Toast.makeText(this, getString(message, *params), Toast.LENGTH_SHORT).show()
}