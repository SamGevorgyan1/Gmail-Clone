package com.gmailclone.utils.extensions

import android.content.Context
import android.view.View
import android.view.ViewGroup

fun hideViews(vararg views: View) {
    views.forEach { it.visibility = View.GONE }
}

fun showViews(vararg views: View) {
    views.forEach { it.visibility = View.VISIBLE }
}


fun updateViewMargins(
    view: View,
    topDp: Int = 0,
    startDp: Int = 0,
    endDp: Int = 0,
    bottomDp: Int = 0,
    context: Context
) {
    val layoutParams = view.layoutParams as ViewGroup.MarginLayoutParams

    val density = context.resources.displayMetrics.density

    val topPx = dpToPx(topDp, density)
    val startPx = dpToPx(startDp, density)
    val endPx = dpToPx(endDp, density)
    val bottomPx = dpToPx(bottomDp, density)

    layoutParams.topMargin = topPx
    layoutParams.marginStart = startPx
    layoutParams.marginEnd = endPx
    layoutParams.bottomMargin = bottomPx

    view.layoutParams = layoutParams
}

private fun dpToPx(dp: Int, density: Float): Int {
    return (dp * density).toInt()
}
