package br.com.maiconhellmann.pomodoro.util.extension

import android.support.design.widget.Snackbar
import android.view.View

fun View.visible(){
    this.visibility = View.VISIBLE
}
fun View.gone(){
    this.visibility = View.GONE
}
fun View.invisible(){
    this.visibility = View.INVISIBLE
}
fun View.snackbar(resId: Int, duration: Int = Snackbar.LENGTH_SHORT) {
    snackbar(this.resources.getString(resId), duration)
}

fun View.snackbar(msg: String, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, msg, duration).show()
}
fun View.longSnackbar(resId: Int) {
    snackbar(resId, Snackbar.LENGTH_LONG)
}

