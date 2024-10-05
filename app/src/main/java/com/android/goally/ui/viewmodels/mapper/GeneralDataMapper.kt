package com.android.goally.ui.viewmodels.mapper

import com.android.goally.constants.AppConstant.Companion.MANUAL
import com.android.goally.data.model.api.FilterData
import com.android.goally.data.model.api.response.copilot.Routines

object GeneralDataMapper {

    fun setDaysForRoutines(routinesList: List<Routines>) {
        routinesList.forEach { routine ->
            routine.days = when (val schedule = routine.scheduleV2) {
                null -> MANUAL
                else -> {
                    val dailyRepeat = schedule.dailyRepeatValues
                    when {
                        dailyRepeat?.Mon?.isNotEmpty() == true &&
                                dailyRepeat.Tue.isNotEmpty() &&
                                dailyRepeat.Wed.isNotEmpty() &&
                                dailyRepeat.Thu.isNotEmpty() &&
                                dailyRepeat.Fri.isNotEmpty() &&
                                dailyRepeat.Sat.isEmpty() &&
                                dailyRepeat.Sun.isEmpty() -> "Weekdays"

                        dailyRepeat?.Mon?.isNotEmpty() == true &&
                                dailyRepeat.Tue.isNotEmpty() &&
                                dailyRepeat.Wed.isNotEmpty() &&
                                dailyRepeat.Thu.isNotEmpty() &&
                                dailyRepeat.Fri.isNotEmpty() &&
                                dailyRepeat.Sat.isNotEmpty() &&
                                dailyRepeat.Sun.isNotEmpty() -> "Everyday"

                        dailyRepeat?.Sat?.isNotEmpty() == true &&
                                dailyRepeat.Sun.isNotEmpty() &&
                                dailyRepeat.Mon.isEmpty() &&
                                dailyRepeat.Tue.isEmpty() &&
                                dailyRepeat.Wed.isEmpty() &&
                                dailyRepeat.Thu.isEmpty() &&
                                dailyRepeat.Fri.isEmpty() -> "Weekends"

                        schedule.yearlyRepeatDateValue != null -> schedule.yearlyRepeatDateValue

                        else -> MANUAL
                    }
                }
            }
        }
    }

    inline fun mapRoutinesData(
        routines: List<Routines>,
        onSuccess: (List<Routines>, List<FilterData>, List<FilterData>) -> Unit
    ) {
        setDaysForRoutines(routines)
        routines.map { it.folder }.distinct().sortedBy { it }

        val folderCounts = routines.groupBy { it.folder }
            .map { (folder, items) -> FilterData(folder ?: MANUAL, items.size) }

        val scheduleCounts = routines.groupBy { it.scheduleV2?.type }
            .map { (schedule, items) -> FilterData(schedule ?: MANUAL, items.size) }

        onSuccess(routines, folderCounts, scheduleCounts)
    }
}