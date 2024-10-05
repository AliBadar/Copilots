package com.android.goally.ui.details

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.animation.AnimationUtils
import com.android.goally.BaseActivity
import com.android.goally.R
import com.android.goally.data.model.api.response.copilot.Routines
import com.android.goally.databinding.ActivityCopilotDetailsBinding
import com.android.goally.databinding.ActivityHomeBinding
import com.android.goally.ui.adapters.CopilotActivitiesAdapter
import com.android.goally.ui.home.HomeActivity
import com.android.goally.util.setSafeOnClickListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CopilotsDetailsActivity: BaseActivity() {

    private lateinit var binding: ActivityCopilotDetailsBinding

    @Inject
    lateinit var activitiesAdapter: CopilotActivitiesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCopilotDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
        setupObservers()
    }

    private fun setupViews() {
        val routines: Routines? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("routines_data", Routines::class.java)
        } else {
            @Suppress("DEPRECATION") // Suppress the deprecation warning for older APIs
            intent.getParcelableExtra<Routines>("routines_data")
        }
        binding.run {
            topBar.tvTitle.text = getString(R.string.do_home_work)

            rvActivities.also {
                val controller = AnimationUtils.loadLayoutAnimation(
                    this@CopilotsDetailsActivity,
                    R.anim.layout_animation_spring
                )
                it.layoutAnimation = controller
                it.adapter = activitiesAdapter
                activitiesAdapter.setActivitiesList(routines?.activities ?: listOf())
            }

            topBar.imgBack.setSafeOnClickListener {
                finish()
            }
        }
    }

    private fun setupObservers() {

    }

    companion object {
        fun getCallingIntent(context: Context, data: Routines): Intent {
            return Intent(context, CopilotsDetailsActivity::class.java).putExtra("routines_data", data)
        }
    }

}