package com.mikep.panelnavigationexample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.mikep.panelnavigation.PanelNavigator
import com.mikep.panelnavigationexample.extensions.navigator

class TestFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_test, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.text_view_one).setOnClickListener {
            navigator.navigate(TestFragmentTwo::class.java, PanelNavigator.NavigationTarget.CENTER_PANEL)
        }
    }

}