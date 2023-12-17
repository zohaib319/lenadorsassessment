package com.lenador.assessment.android.view.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import com.lenador.assessment.android.R
import com.lenador.assessment.android.databinding.ActivitySettingsBinding

/**
 * Settings activity to enable and disable tax calculations
 */
class Settings : AppCompatActivity() {

    private lateinit var binding : ActivitySettingsBinding
    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // access binding from ActivitySettingsBindings
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // setup View Model
        binding.settingsViewModel = viewModel
        // to observe and change the data automatically.
        binding.lifecycleOwner = this


        // when the switch state is changed, show a message.
        binding.taxCollectionSwitch.setOnCheckedChangeListener { _ , isChecked ->
            viewModel.onSwitchStateChanged(isChecked)
            if (isChecked){
                Snackbar.make(binding.root,resources.getString(R.string.tax_collection_turned_on),Snackbar.LENGTH_SHORT).show()
            }else{
                Snackbar.make(binding.root,resources.getString(R.string.tax_collection_turned_off),Snackbar.LENGTH_SHORT).show()
            }
        }

    }
}