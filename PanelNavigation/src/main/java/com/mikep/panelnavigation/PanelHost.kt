package com.mikep.panelnavigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment

abstract class PanelHost: Fragment() {
    abstract val hostInitialContainerViewId: Int
    abstract val hostInitialFragmentClass: Class<out Fragment>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FrameLayout(requireContext()).apply {
            id = hostInitialContainerViewId
        }
    }
}