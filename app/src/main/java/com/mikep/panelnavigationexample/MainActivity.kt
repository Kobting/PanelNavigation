package com.mikep.panelnavigationexample

import android.os.Bundle
import com.discord.panels.OverlappingPanelsLayout
import com.mikep.panelnavigation.PanelActivity
import com.mikep.panelnavigation.PanelHost
import com.mikep.panelnavigation.PanelNavigationListener
import com.mikep.panelnavigation.PanelNavigator

class MainActivity : PanelActivity(), PanelNavigationListener {

    override val startPanelHostClass: Class<out PanelHost> = StartPanel::class.java
    override val centerPanelHostClass: Class<out PanelHost> = CenterPanel::class.java
    override val endPanelHostClass: Class<out PanelHost> = EndPanel::class.java
    override val contentViewLayoutId: Int = R.layout.activity_panels
    override val panelsViewId: Int = R.id.layout_panels

    override val configuration: Configuration = Configuration(
        startPanelTarget = OverlappingPanelsLayout.Panel.START,
        finalPanelTarget = OverlappingPanelsLayout.Panel.START
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigator.panelNavigationListener = this
    }

    override fun onPanelNavigation(target: PanelNavigator.NavigationTarget) {
        if(target == PanelNavigator.NavigationTarget.CENTER_PANEL) {
            panels.closePanels()
        }
    }

}