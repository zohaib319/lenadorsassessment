package com.lenador.assessment.android.view.dashboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lenador.assessment.android.databinding.DashboardBinding
import com.lenador.assessment.android.view.newOrder.NewOrderActivity


/**
 * Dashboard where the user can have access to different operations, for example settings,
 * view orders, reports and add new orders
 */
class Dashboard : AppCompatActivity() {

    /**
     * Local variables
     */

    // for View Binding
    private lateinit var binding: DashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // access binding for Dashboard Binding
        binding = DashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**
         * Navigate to settings when the settings button is clicked.
         */
        binding.settingsButtonDashboard.setOnClickListener {
            val settingsIntent = Intent(this,com.lenador.assessment.android.view.settings.Settings:: class.java)
            startActivity(settingsIntent)
        }
        binding.newOrderLayout.setOnClickListener {
            val newOrderIntent = Intent(this,NewOrderActivity::class.java)
            startActivity(newOrderIntent)
        }


    }
}