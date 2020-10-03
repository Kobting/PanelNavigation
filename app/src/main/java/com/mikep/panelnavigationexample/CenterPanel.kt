package com.mikep.panelnavigationexample

import com.mikep.panelnavigation.PanelHost

class CenterPanel: PanelHost() {
    override val hostInitialContainerViewId: Int = R.id.center_panel
    override val hostInitialFragmentClass: Class<TestFragment> = TestFragment::class.java
}