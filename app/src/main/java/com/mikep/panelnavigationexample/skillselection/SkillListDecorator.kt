package com.mikep.panelnavigationexample.skillselection

import android.graphics.Rect
import android.view.View
import androidx.core.view.setPadding
import androidx.recyclerview.widget.RecyclerView
import com.mikep.panelnavigationexample.extensions.dip

class SkillListDecorator: RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        if(parent.indexOfChild(view) != 0) {
            view.setPadding(view.dip(16), 0, view.dip(16), view.dip(16))
        } else {
            view.setPadding(view.dip(16))
        }
    }

}