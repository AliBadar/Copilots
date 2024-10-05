package com.android.goally.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.android.goally.BaseActivity
import com.android.goally.R
import com.android.goally.constants.AppConstant.Companion.FOLDER
import com.android.goally.constants.AppConstant.Companion.MANUAL
import com.android.goally.constants.AppConstant.Companion.SCHEDULE
import com.android.goally.data.model.api.FilterData
import com.android.goally.data.model.api.response.copilot.Routines
import com.android.goally.databinding.ActivityHomeBinding
import com.android.goally.interfaces.OnCopilotItemClickListener
import com.android.goally.interfaces.OnDrawerItemClickListener
import com.android.goally.ui.adapters.CopilotsAdapter
import com.android.goally.ui.adapters.NavigationDrawerAdapter
import com.android.goally.ui.details.CopilotsDetailsActivity
import com.android.goally.util.setSafeOnClickListener
import com.android.goally.util.showSnack
import com.android.goally.util.toast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : BaseActivity(), OnDrawerItemClickListener, OnCopilotItemClickListener {
    private lateinit var binding: ActivityHomeBinding

    private val foldersList = mutableListOf<FilterData>()
    private val scheduleList = mutableListOf<FilterData>()
    private val routinesList = mutableListOf<Routines>()

    private var shouldAnimate = false

    @Inject
    lateinit var copilotsAdapter: CopilotsAdapter

    @Inject
    lateinit var navigationDrawerAdapter: NavigationDrawerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
        setupObservers()
    }

    private fun setupViews() {
        binding.run {

            rvCopilots.also {
                it.adapter = copilotsAdapter
                copilotsAdapter.setOnItemClickListener(this@HomeActivity)
            }

            rvNavigationDrawer.also {
                it.adapter = navigationDrawerAdapter
                navigationDrawerAdapter.setDrawerClickListener(this@HomeActivity)
            }

            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, GravityCompat.END)

            btnFilterRight.setSafeOnClickListener {
                tvAll.text = getString(R.string.all_folders)
                navigationDrawerAdapter.setDrawerList(foldersList, FOLDER).also {
                    drawerLayout.openDrawer(GravityCompat.END)
                }
            }

            btnFilterLeft.setSafeOnClickListener {
                tvAll.text = getString(R.string.schedule)
                navigationDrawerAdapter.setDrawerList(scheduleList, SCHEDULE).also {
                    drawerLayout.openDrawer(GravityCompat.END)
                }
            }

            topBar.imgBack.setSafeOnClickListener {
                finish()
            }
        }
    }

    private fun setupObservers() {
        //observer goes here
        generalViewModel.getAuthenticationLive().observe(this) {
            it?.let {
                //binding.tvEmail.text = getString(R.string.you_are_logged_in_as, it.name)
            }
        }

        generalViewModel.getCopilots(onLoading = {
            if (it) {
                binding.shimmerLayout.visibility = View.VISIBLE
                binding.rvCopilots.visibility = View.INVISIBLE
            } else {
                binding.shimmerLayout.visibility = View.GONE
                binding.rvCopilots.visibility = View.VISIBLE
            }
        }, onError = { message ->
            binding.shimmerLayout.visibility = View.GONE
            binding.rvCopilots.visibility = View.GONE
            showNoCopilots(listOf())
//            binding.drawerLayout.showSnack(message)
        }, onSuccess = { routines, folderRoutines, scheduleRoutines ->


            routinesList.clear()
            foldersList.clear()
            scheduleList.clear()

            binding.shimmerLayout.visibility = View.GONE
            binding.rvCopilots.visibility = View.VISIBLE

            routinesList.addAll(routines)


            if (shouldAnimate) {
                binding.rvCopilots.layoutAnimation = null
                shouldAnimate = false
            } else {
                shouldAnimate = true
            }

            showNoCopilots(routinesList)
            copilotsAdapter.setRoutinesList(routinesList)





            with(FilterData(getString(R.string.all_folders), routines.size, true)) {
                foldersList.add(this)
            }.also {
                foldersList.addAll(folderRoutines)
            }

            with(FilterData(getString(R.string.all), routines.size, true)) {
                scheduleList.add(this)
            }.also {
                scheduleList.addAll(scheduleRoutines)
            }
        })
    }


    companion object {
        fun getCallingIntent(context: Context): Intent {
            return Intent(context, HomeActivity::class.java)
        }
    }

    override fun onDrawerItemClick(selectedName: String, type: String) {
        val filterRoutinesList: List<Routines>

        if (type == FOLDER) {
            updateFolderSelection(selectedName)
        } else {
            updateScheduleSelection(selectedName)
        }


        val selectedSchedule = scheduleList.find { it.isSelected }?.name
        val selectedFolder = foldersList.find { it.isSelected }?.name

        filterRoutinesList = when {
            selectedFolder == getString(R.string.all_folders) && selectedSchedule == getString(R.string.all) -> {
                routinesList
            }

            selectedFolder == getString(R.string.all_folders) -> {
                routinesList.filter {
                    (it.scheduleV2?.type ?: MANUAL) == selectedSchedule
                }
            }

            selectedSchedule == getString(R.string.all) -> {
                routinesList.filter { it.folder == selectedFolder }
            }

            else -> {
                routinesList.filter {
                    it.folder == selectedFolder && (it.scheduleV2?.type
                        ?: MANUAL) == selectedSchedule
                }
            }
        }

        copilotsAdapter.setRoutinesList(filterRoutinesList).also {
            binding.drawerLayout.closeDrawers()
            shouldAnimate = true
            showNoCopilots(filterRoutinesList)
        }

    }

    private fun updateFolderSelection(selectedName: String) {
        foldersList.forEach { folder ->
            folder.isSelected = folder.name == selectedName
        }
    }

    private fun updateScheduleSelection(selectedName: String) {
        scheduleList.forEach { schedule ->
            schedule.isSelected = schedule.name == selectedName
        }
    }

    override fun onItemClick(routines: Routines) {
        startActivity(CopilotsDetailsActivity.getCallingIntent(this@HomeActivity, routines))
    }

    private fun showNoCopilots(routineList: List<Routines>) {
        binding.run {
            if (routineList.isEmpty()) {
                imgNoCopilots.visibility = View.VISIBLE
                rvCopilots.visibility = View.GONE
            } else {
                imgNoCopilots.visibility = View.GONE
                rvCopilots.visibility = View.VISIBLE
                if (shouldAnimate) {
                    val controller = AnimationUtils.loadLayoutAnimation(
                        this@HomeActivity,
                        R.anim.layout_animation_spring
                    )
                    rvCopilots.layoutAnimation = controller
                }
            }

        }
    }
}