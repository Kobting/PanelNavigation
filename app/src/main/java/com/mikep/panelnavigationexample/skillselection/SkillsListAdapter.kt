package com.mikep.panelnavigationexample.skillselection

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mikep.panelnavigationexample.R
import com.mikep.panelnavigationexample.databinding.ViewSectionHeaderBinding
import com.mikep.panelnavigationexample.databinding.ViewSectionItemBinding

class SkillsListAdapter(private val initialItems: List<Section>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val currentItems: MutableList<Section> = initialItems.toMutableList()

    companion object {
        private const val SECTION_VIEW_TYPE = 1
        private const val SECTION_ITEM_VIEW_TYPE = 2
    }

    fun updateItems(newItems: List<Section>) {
        currentItems.clear()
        currentItems.addAll(newItems)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            SECTION_VIEW_TYPE -> {
                SectionViewHolder(DataBindingUtil.inflate(inflater,
                    R.layout.view_section_header, parent, false))
            }
            SECTION_ITEM_VIEW_TYPE -> {
                SectionItemViewHolder(DataBindingUtil.inflate(inflater,
                    R.layout.view_section_item, parent, false))
            }
            else -> throw IllegalArgumentException("Unknown viewType: $viewType")
        }
    }

    override fun getItemCount(): Int {
        var itemCount = currentItems.size
        currentItems.forEach {
            itemCount += it.sectionItems.size
        }
        return itemCount
    }

    override fun getItemViewType(position: Int): Int {

        val flattenedList = currentItemsFlattened()

        return when (flattenedList[position]) {
            is Section -> SECTION_VIEW_TYPE
            is SectionItem -> SECTION_ITEM_VIEW_TYPE
            else -> -1
        }
    }

    private fun currentItemsFlattened(): List<Any> {
        val flattenedList: MutableList<Any> = mutableListOf()

        currentItems.forEach {
            flattenedList.add(it)
            flattenedList.addAll(it.sectionItems)
        }

        return flattenedList
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val flattenedList = currentItemsFlattened()

        when (holder) {
            is SectionViewHolder -> holder.bind(flattenedList[position] as Section)
            is SectionItemViewHolder -> holder.bind(flattenedList[position] as SectionItem)
        }
    }

    inner class SectionViewHolder(private val binding: ViewSectionHeaderBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(section: Section) {
            binding.sectionHeader = section
            binding.executePendingBindings()
        }
    }

    inner class SectionItemViewHolder(private val binding: ViewSectionItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(sectionItem: SectionItem) {
            binding.sectionItem = sectionItem
            binding.executePendingBindings()
        }

    }
}