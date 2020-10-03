package com.mikep.panelnavigationexample.skillselection

import com.mikep.panelnavigationexample.TestSkillFragment
import com.mikep.panelnavigation.PanelNavigation
import com.mikep.panelnavigation.PanelNavigator

class SkillSelectionNavigation(private val navigator: PanelNavigator): PanelNavigation {

    companion object {
        internal const val ATTACK = "navigation_attack"
        internal const val WOODCUTTING = "navigation_woodcutting"
        internal const val STRENGTH = "navigation_strength"
    }

    override fun onNavigate(navigationKey: String) {
        when(navigationKey) {
            ATTACK -> navigator.navigate(TestSkillFragment.newInstance("Attack"), PanelNavigator.NavigationTarget.CENTER_PANEL, PanelNavigator.NavigationType.REPLACE)
            WOODCUTTING -> navigator.navigate(TestSkillFragment.newInstance("Woodcutting"), PanelNavigator.NavigationTarget.CENTER_PANEL, PanelNavigator.NavigationType.REPLACE)
            STRENGTH -> navigator.navigate(TestSkillFragment.newInstance("Strength"), PanelNavigator.NavigationTarget.CENTER_PANEL, PanelNavigator.NavigationType.REPLACE)
        }
    }
}