package com.mikep.panelnavigation

fun interface PanelNavigationListener {
    fun onPanelNavigation(target: PanelNavigator.NavigationTarget)
}