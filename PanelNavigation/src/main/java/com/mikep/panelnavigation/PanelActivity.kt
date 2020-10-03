package com.mikep.panelnavigation

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnLayout
import com.discord.panels.OverlappingPanelsLayout
import com.discord.panels.PanelState

abstract class PanelActivity: AppCompatActivity() {

    abstract val startPanelHostClass: Class<out PanelHost>
    abstract val centerPanelHostClass: Class<out PanelHost>
    abstract val endPanelHostClass: Class<out PanelHost>
    abstract val contentViewLayoutId: Int
    abstract val panelsViewId: Int

    open class Configuration(
        val startPanelTarget: OverlappingPanelsLayout.Panel = OverlappingPanelsLayout.Panel.START,
        val finalPanelTarget: OverlappingPanelsLayout.Panel = OverlappingPanelsLayout.Panel.START
    )

    open val configuration: Configuration = Configuration()
    lateinit var navigator: PanelNavigator

    lateinit var panels: OverlappingPanelsLayout

    private val startPanelStateListener = object : OverlappingPanelsLayout.PanelStateListener {
        override fun onPanelStateChange(panelState: PanelState) = onStartPanelStateChanged(panelState)
    }

    private val endPanelStateListener = object : OverlappingPanelsLayout.PanelStateListener {
        override fun onPanelStateChange(panelState: PanelState) = onEndPanelStateChanged(panelState)
    }

    private var panelStates: PanelStates? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentViewLayoutId)
        navigator = PanelNavigator(this, startPanelHostClass, centerPanelHostClass, endPanelHostClass)
        try {
            panels = findViewById(panelsViewId)
        } catch (ex: Exception) {
            throw NullPointerException("Cannot find OverlappingPanelsLayout with id: $panelsViewId in contentView")
        }
        panelStates = savedInstanceState?.getParcelable(KEY_PANEL_STATE)

        panels.doOnLayout {
            configurePanels()
        }
    }

    private fun configurePanels() {
        if(panelStates == null) {
            panelStates = PanelStates()
            when(configuration.startPanelTarget) {
                OverlappingPanelsLayout.Panel.START -> {
                    panels.openStartPanel()
                }
                OverlappingPanelsLayout.Panel.CENTER -> {}
                OverlappingPanelsLayout.Panel.END -> {
                    panels.openEndPanel()
                }
            }
        } else {
            panels.handleStartPanelState(panelStates!!.startPanelState.toPanelState())
            panels.handleEndPanelState(panelStates!!.endPanelState.toPanelState())
        }
        panels.registerStartPanelStateListeners(startPanelStateListener)
        panels.registerEndPanelStateListeners(endPanelStateListener)
    }

    open fun onStartPanelStateChanged(panelState: PanelState) {
        panelStates = panelStates?.copy(startPanelState = panelState.toParcelablePanelState())
    }

    open fun onEndPanelStateChanged(panelState: PanelState) {
        panelStates = panelStates?.copy(endPanelState = panelState.toParcelablePanelState())
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putParcelable(KEY_PANEL_STATE, panelStates)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        panelStates = savedInstanceState.getParcelable<PanelStates?>(KEY_PANEL_STATE)
    }

    override fun onBackPressed() {
        val currentSelectedPanel = panels.getSelectedPanel()
        val onBackHandled = when (currentSelectedPanel) {
            OverlappingPanelsLayout.Panel.START -> navigator.onBack(PanelNavigator.NavigationTarget.START_PANEL)
            OverlappingPanelsLayout.Panel.CENTER -> navigator.onBack(PanelNavigator.NavigationTarget.CENTER_PANEL)
            OverlappingPanelsLayout.Panel.END -> navigator.onBack(PanelNavigator.NavigationTarget.END_PANEL)
        }

        if(!onBackHandled && currentSelectedPanel != configuration.finalPanelTarget) {
            when(configuration.finalPanelTarget) {
                OverlappingPanelsLayout.Panel.START -> {
                    if(currentSelectedPanel == OverlappingPanelsLayout.Panel.END) {
                        panels.closePanels()
                    } else {
                        panels.openStartPanel()
                    }
                }
                OverlappingPanelsLayout.Panel.CENTER -> panels.closePanels()
                OverlappingPanelsLayout.Panel.END -> {
                    if(currentSelectedPanel == OverlappingPanelsLayout.Panel.START) {
                        panels.closePanels()
                    } else {
                        panels.openEndPanel()
                    }
                }
            }
        } else if(!onBackHandled) {
            super.onBackPressed()
        }

    }

    companion object {
        private const val KEY_PANEL_STATE = "key_panel_state"
    }
}