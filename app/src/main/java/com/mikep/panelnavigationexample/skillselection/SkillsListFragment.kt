package com.mikep.panelnavigationexample.skillselection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.mikep.panelnavigationexample.R
import com.mikep.panelnavigationexample.extensions.navigator

class SkillsListFragment : Fragment() {

    lateinit var viewModel: SkillSelectionViewModel
    lateinit var adapter: SkillsListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_skills_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureViewModel()
        adapter = SkillsListAdapter(viewModel.sectionData)
        with(view.findViewById<RecyclerView>(R.id.recycler_view)) {
            this.adapter = this@SkillsListFragment.adapter
            this.addItemDecoration(SkillListDecorator())
        }
    }

    private fun configureViewModel() {
        viewModel = ViewModelProvider(requireActivity()).get(SkillSelectionViewModel::class.java)
        viewModel.skillSelectionNavigation = SkillSelectionNavigation(navigator)
    }

}