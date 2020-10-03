package com.mikep.panelnavigationexample

import com.mikep.panelnavigation.PanelHost
import com.mikep.panelnavigationexample.skillselection.SkillsListFragment

class StartPanel: PanelHost() {
    override val hostInitialContainerViewId: Int = R.id.start_panel
    override val hostInitialFragmentClass: Class<SkillsListFragment> = SkillsListFragment::class.java
}