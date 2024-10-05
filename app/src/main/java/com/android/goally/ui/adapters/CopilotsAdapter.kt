package com.android.goally.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.android.goally.R
import com.android.goally.data.model.api.response.copilot.Routines
import com.android.goally.databinding.ItemCopilotBinding
import com.android.goally.interfaces.OnCopilotItemClickListener
import com.android.goally.interfaces.OnDrawerItemClickListener
import com.android.goally.util.setSafeOnClickListener
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import javax.inject.Inject

class CopilotsAdapter  @Inject constructor(

) : RecyclerView.Adapter<CopilotsAdapter.CopilotViewHolder>() {

    private var routinesList: List<Routines> = arrayListOf()
    private lateinit var onItemClickListener: OnCopilotItemClickListener

    class CopilotViewHolder private constructor(private val binding: ItemCopilotBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            content: Routines,
            onItemClickListener: OnCopilotItemClickListener
        ) {
            content.let {routine ->
                binding.run {
                    loadImageWithRoundedCorners(imgCountryFlag, content.imgURL.toString(), 20)
                    tvTitle.text = routine.name
                    tvSchedule.text = routine.days
                    tvFolder.text = routine.folder
                }

                itemView.setSafeOnClickListener {
                    onItemClickListener.onItemClick(routine)
                }
            }
        }

        companion object {
            fun from(parent: ViewGroup): CopilotViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemCopilotBinding.inflate(layoutInflater, parent, false)
                return CopilotViewHolder(binding)
            }

        }

        private fun loadImageWithRoundedCorners(imageView: ImageView, url: String, cornerRadius: Int) {
            val requestOptions = RequestOptions().transform(RoundedCorners(cornerRadius))

            Glide.with(imageView.context)
                .load(url)
                .apply(requestOptions)
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_placeholder)
                .transition(DrawableTransitionOptions.withCrossFade()) // Optional: for a fade-in effect
                .into(imageView)
        }
    }

    fun setRoutinesList(
        routinesList: List<Routines>
    ) {
        this.routinesList = routinesList
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(
        onItemClickListener: OnCopilotItemClickListener
    ) {
        this.onItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CopilotViewHolder {
        return CopilotViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CopilotViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        val item = routinesList?.get(position)
        item.let { it?.let { it1 -> holder.bind(it1, onItemClickListener) } }
    }

    override fun getItemCount(): Int {
        return routinesList?.size ?: 0
    }
}