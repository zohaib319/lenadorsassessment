package com.lenador.assessment.android.view.settings

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.lenador.assessment.android.others.AppSettings


/**
 * Created By Zohaib on 15/12/2023.
 */

/**
 * Settings View Model will be used to set the initial state of the settings screen.
 * We will fetch data from shared preferences and set the switch initial state.
 *
 */
class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    val taxCollectionSwitchState =  MutableLiveData<Boolean>()
    private val appSettings: SharedPreferences = application.getSharedPreferences(AppSettings.APP_PREFS
        ,Context.MODE_PRIVATE)


    // when init check the persisted status of the toggle button and restore it.
    init {
        taxCollectionSwitchState.value = appSettings.getBoolean(AppSettings.TAX_CALCULATIONS_ENABLED
            ,false)
    }


    // When the switch state is changed, we will call this method to update the data in the
    // viewModel.
    fun onSwitchStateChanged(newState: Boolean) {
        taxCollectionSwitchState.value = newState
        appSettings.edit().putBoolean(AppSettings.TAX_CALCULATIONS_ENABLED, newState).apply()
    }
}