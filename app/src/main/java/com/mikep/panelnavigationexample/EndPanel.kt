package com.mikep.panelnavigationexample

import com.mikep.panelnavigation.PanelHost

class EndPanel: PanelHost() {
    override val hostInitialContainerViewId: Int = R.id.end_panel
    override val hostInitialFragmentClass: Class<TestFragment> = TestFragment::class.java
}