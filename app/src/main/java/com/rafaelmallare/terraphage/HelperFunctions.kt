package com.rafaelmallare.terraphage

import android.view.View

/**
 * Created by Rj on 5/25/2017.
 */
fun setOnClickListeners(views: List<View>, listenerSetFunction: (View) -> Unit) {
    for (view in views) {
        view.setOnClickListener { listenerSetFunction(view) }
    }
}