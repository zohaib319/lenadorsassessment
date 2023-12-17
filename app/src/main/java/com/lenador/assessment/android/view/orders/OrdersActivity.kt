package com.lenador.assessment.android.view.orders

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.lenador.assessment.android.R
import com.lenador.assessment.android.databinding.ActivityOrdersBinding

/**
 * Activity to show list of orders to the user.
 */
class OrdersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrdersBinding
    private val viewModel: ActivityOrdersViewModel by viewModels {
        ActivityOrdersViewModel.createFactory(application)
    }

    /**
     * Inflate the layout,
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_orders)
        binding.lifecycleOwner = this
        binding.viewModel  = viewModel


        // show fragment of orders
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.orders_fragment_container, OrdersFragment())
                .commit()
        }


    }
}