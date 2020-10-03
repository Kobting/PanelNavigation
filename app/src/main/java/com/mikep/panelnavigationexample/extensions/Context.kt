package com.mikep.panelnavigationexample.extensions

import android.content.Context
import android.view.View

fun Context.dip(value: Int): Int = (value * resources.displayMetrics.density).toInt()
fun Context.dip(value: Float): Int = (value * resources.displayMetrics.density).toInt()

fun View.dip(value: Int): Int = (value * resources.displayMetrics.density).toInt()
fun View.dip(value: Float): Int = (value * resources.displayMetrics.density).toInt()