package com.android.goally.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.android.goally.R
import com.android.goally.data.model.api.response.copilot.Activities
import com.android.goally.data.model.api.response.copilot.Routines
import com.android.goally.databinding.ItemCopilotBinding
import com.android.goally.databinding.ItemDetailsActivitiesBinding
import com.android.goally.interfaces.OnCopilotItemClickListener
import com.android.goally.interfaces.OnDrawerItemClickListener
import com.android.goally.util.AppUtil
import com.android.goally.util.setSafeOnClickListener
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import javax.inject.Inject

class CopilotActivitiesAdapter  @Inject constructor(

) : RecyclerView.Adapter<CopilotActivitiesAdapter.CopilotViewHolder>() {

    private var routinesList: List<Activities> = arrayListOf()

    class CopilotViewHolder private constructor(private val binding: ItemDetailsActivitiesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            content: Activities,
            position: Int
        ) {
            content.let {routine ->
                binding.run {

                    val layoutParams = imgCountryFlag.layoutParams

                    if (position % 2 == 0) {
                        layoutParams.width = AppUtil.convertDpToPx(180, imgCountryFlag)
                        layoutParams.height = AppUtil.convertDpToPx(100, imgCountryFlag)

                        itemView.setBackgroundColor(itemView.context.getColor(R.color.dim_item))
                    } else {
                        layoutParams.width = AppUtil.convertDpToPx(100, imgCountryFlag)
                        layoutParams.height = AppUtil.convertDpToPx(100, imgCountryFlag)
                        itemView.setBackgroundColor(itemView.context.getColor(R.color.white))
                    }

                    imgCountryFlag.layoutParams = layoutParams

                    loadImageWithRoundedCorners(imgCountryFlag, content.imgURL.toString(), 20)
                    tvTitle.text = routine.name
                }
            }
        }

        companion object {
            fun from(parent: ViewGroup): CopilotViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemDetailsActivitiesBinding.inflate(layoutInflater, parent, false)
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
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView)
        }
    }

    fun setActivitiesList(
        routinesList: List<Activities>
    ) {
        this.routinesList = routinesList
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CopilotViewHolder {
        return CopilotViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CopilotViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        val item = routinesList?.get(position)
        item.let { it?.let { it1 -> holder.bind(it1, position) } }
    }

    override fun getItemCount(): Int {
        return routinesList?.size ?: 0
    }
}