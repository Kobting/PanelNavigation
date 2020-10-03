package com.mikep.panelnavigationexample.skillselection

import com.mikep.panelnavigationexample.R

data class Section(
    val sectionId: String,
    val headerText: Int,
    val headerEndDrawable: Int?,
    val onHeaderClicked: (section: Section) -> Unit,
    val sectionItems: List<SectionItem>
)

sealed class SectionItem {
    abstract val sectionText: Int
    abstract val sectionStartDrawable: Int
    abstract val onSectionClicked: (sectionItem: SectionItem) -> Unit
}

sealed class CombatSectionItem : SectionItem() {
}

data class AttackSectionItem(
    override val onSectionClicked: (sectionItem: SectionItem) -> Unit
) : CombatSectionItem() {
    override val sectionText: Int = R.string.attack
    override val sectionStartDrawable: Int = R.drawable.ic_attack
}

data class StrengthSectionItem(
    override val onSectionClicked: (sectionItem: SectionItem) -> Unit
) : CombatSectionItem() {
    override val sectionText: Int = R.string.strength
    override val sectionStartDrawable: Int = R.drawable.ic_attack
}


sealed class SkillSectionItem: SectionItem()

data class WoodcuttingSectionItem(
    override val onSectionClicked: (sectionItem: SectionItem) -> Unit
) : SkillSectionItem() {
    override val sectionText: Int = R.string.woodcutting
    override val sectionStartDrawable: Int = R.drawable.ic_woodcutting
}
