package com.fuchuanboy.todaylunch

import android.content.res.Resources
import kotlin.math.roundToInt

/** Density-independent pixel helper shared by the lunch app UI. */
fun dp(value: Int): Int =
    (value * Resources.getSystem().displayMetrics.density).roundToInt()
