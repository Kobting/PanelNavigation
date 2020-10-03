package com.mikep.panelnavigationexample.skillselection

import android.util.Log
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModel
import com.mikep.panelnavigationexample.R

class SkillSelectionViewModel : ViewModel() {

    lateinit var skillSelectionNavigation: SkillSelectionNavigation

    companion object {
        private const val COMBAT_SECTION_ID = "combat"
        private const val SKILLS_SECTION_ID = "skills"
    }

    val sectionData = listOf(
        Section(
            COMBAT_SECTION_ID,
            R.string.combat,
            null,
            ::onHeaderClicked,
            listOf(
                AttackSectionItem(::onSectionItemClicked),
                StrengthSectionItem(::onSectionItemClicked)
            )
        ),
        Section(
            SKILLS_SECTION_ID,
            R.string.skills,
            null,
            ::onHeaderClicked,
            listOf(
                WoodcuttingSectionItem(::onSectionItemClicked)
            )
        )
    )

    fun onSectionItemClicked(sectionItem: SectionItem) {
        when (sectionItem) {
            is CombatSectionItem -> onCombatItemClicked(sectionItem)
            is SkillSectionItem -> onSkillItemClicked(sectionItem)
        }
    }

    private fun onCombatItemClicked(sectionItem: CombatSectionItem) {
        when (sectionItem) {
            is AttackSectionItem -> skillSelectionNavigation.onNavigate(SkillSelectionNavigation.ATTACK)
            is StrengthSectionItem -> skillSelectionNavigation.onNavigate(SkillSelectionNavigation.STRENGTH)
        }
    }


    private fun onSkillItemClicked(sectionItem: SkillSectionItem) {
        when (sectionItem) {
            is WoodcuttingSectionItem -> skillSelectionNavigation.onNavigate(SkillSelectionNavigation.WOODCUTTING)
        }
    }

    fun onHeaderClicked(section: Section) {

    }

}

@BindingAdapter("app:drawableEndCompat")
fun setEndDrawable(textView: TextView, drawableRes: Int?) {
    Log.d("setEndDrawable", "DrawableRes: $drawableRes")
    val currentDrawables = textView.compoundDrawables
    textView.setCompoundDrawablesWithIntrinsicBounds(currentDrawables[0], currentDrawables[1], ContextCompat.getDrawable(textView.context, drawableRes ?: return), currentDrawables[3])
}

@BindingAdapter("app:drawableStartCompat")
fun setStartDrawable(textView: TextView, drawableRes: Int?) {
    Log.d("setEndDrawable", "DrawableRes: $drawableRes")
    val currentDrawables = textView.compoundDrawables
    textView.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(textView.context, drawableRes ?: return), currentDrawables[1], currentDrawables[2], currentDrawables[3])
}

