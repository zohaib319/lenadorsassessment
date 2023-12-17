package com.lenador.assessment.android.view.orders

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lenador.assessment.android.database.DatabaseHelper


/**
 * Created By Zohaib on 17/12/2023.
 */

class ActivityOrdersViewModel(application: Application): ViewModel() {


    private val dbHelper: DatabaseHelper by lazy {
        DatabaseHelper(application)
    }
    init {

    }

    /**
     * Creating ViewModel factory so we can access application context for our database helper
     * initialization.
     */
    companion object {
        fun createFactory(application: Application): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(ActivityOrdersViewModel::class.java)) {
                        return ActivityOrdersViewModel(application) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
        }
    }
}