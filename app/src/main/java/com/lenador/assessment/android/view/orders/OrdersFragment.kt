package com.lenador.assessment.android.view.orders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.lenador.assessment.android.adapters.OrdersAdapter
import com.lenador.assessment.android.databinding.FragmentOrdersBinding

class OrdersFragment : Fragment() {


    val viewModel: OrdersViewModel by viewModels {
        OrdersViewModel.createFactory(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentOrdersBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        /* recycler view setup here to show the orders. add observer to observe the view model for
        *  changes
        */
        val recyclerView = binding.ordersRv
        recyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        recyclerView.adapter = OrdersAdapter()
        viewModel.orders.observe(viewLifecycleOwner) { orders ->
            // Update the adapter data when LiveData changes
            (recyclerView.adapter as? OrdersAdapter)?.submitList(orders)
        }

        return binding.root
    }

}