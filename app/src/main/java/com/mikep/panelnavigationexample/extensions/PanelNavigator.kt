package com.mikep.panelnavigationexample.extensions

import androidx.fragment.app.Fragment
import com.mikep.panelnavigation.PanelActivity
import com.mikep.panelnavigation.PanelNavigator

val Fragment.navigator: PanelNavigator
    get() = (requireActivity() as? PanelActivity)?.navigator ?: throw IllegalStateException("Activity of ${this::class.java.name} is not a PanelActivity")