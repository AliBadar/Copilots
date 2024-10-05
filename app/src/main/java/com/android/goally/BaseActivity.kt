package com.android.goally

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.android.goally.ui.viewmodels.GeneralViewModel
import com.android.goally.util.PreferenceUtil
import com.google.gson.Gson
import javax.inject.Inject

open class BaseActivity : AppCompatActivity() {

    protected val generalViewModel: GeneralViewModel by viewModels()

    @Inject
    lateinit var preferenceUtil: PreferenceUtil

    @Inject
    lateinit var gson: Gson

    override fun startActivity(intent: Intent?) {
        super.startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    override fun startActivity(intent: Intent?, options: Bundle?) {
        super.startActivity(intent, options)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

}