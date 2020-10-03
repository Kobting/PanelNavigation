package com.mikep.panelnavigationexample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.mikep.panelnavigationexample.databinding.FragmentTestSkillBinding

class TestSkillFragment: Fragment() {

    lateinit var binding: FragmentTestSkillBinding

    companion object {

        private const val SKILL_NAME_ARG = "skill_name_arg"

        fun newInstance(skillName: String): TestSkillFragment {
            return TestSkillFragment().apply {
                arguments = Bundle().apply {
                    putString(SKILL_NAME_ARG, skillName)
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_test_skill, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.skillName = requireArguments().getString(SKILL_NAME_ARG)
    }

}