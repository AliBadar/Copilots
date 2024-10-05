package com.android.goally.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.goally.R
import com.android.goally.constants.AppConstant.Companion.MANUAL
import com.android.goally.data.model.api.FilterData
import com.android.goally.databinding.ItemNavigationDrawerBinding
import com.android.goally.interfaces.OnDrawerItemClickListener
import com.android.goally.util.setSafeOnClickListener
import javax.inject.Inject

class NavigationDrawerAdapter  @Inject constructor(

) : RecyclerView.Adapter<NavigationDrawerAdapter.NavigationDrawerViewHolder>() {

    private var routinesList: List<FilterData> = arrayListOf()
    private var type: String = ""

    private lateinit var onItemClickListener: OnDrawerItemClickListener

    class NavigationDrawerViewHolder private constructor(private val binding: ItemNavigationDrawerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            content: FilterData,
            type: String,
            onItemClickListener: OnDrawerItemClickListener
        ) {
            content.let {folder ->
                binding.run {
                    tvTitle.text = folder.name ?: MANUAL
                    tvTotalCount.text = "${folder.count}"
                    if (folder.isSelected) {
                        imgRadioButton.setImageResource(R.drawable.ic_selected)
                    } else {
                        imgRadioButton.setImageResource(R.drawable.ic_unselected)
                    }
                }

                itemView.setSafeOnClickListener {
                    onItemClickListener.onDrawerItemClick(folder.name, type = type)
                }

            }
        }

        companion object {
            fun from(parent: ViewGroup): NavigationDrawerViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemNavigationDrawerBinding.inflate(layoutInflater, parent, false)
                return NavigationDrawerViewHolder(binding)
            }

        }
    }

    fun setDrawerList(
        routinesList: List<FilterData>,
        type: String
    ) {
        this.routinesList = routinesList
        this.type = type
        notifyDataSetChanged()
    }

    fun setDrawerClickListener(
        onItemClickListener: OnDrawerItemClickListener
    ) {
        this.onItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NavigationDrawerViewHolder {
        return NavigationDrawerViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: NavigationDrawerViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        val item = routinesList?.get(position)
        item.let { it?.let { it1 -> holder.bind(it1, type, onItemClickListener) } }
    }

    override fun getItemCount(): Int {
        return routinesList?.size ?: 0
    }
}