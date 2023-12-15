package com.lenador.assessment.android.view.products

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.lenador.assessment.android.adapters.ProductsAdapter
import com.lenador.assessment.android.databinding.FragmentProductsBinding

class ProductsFragment : Fragment() {

    private val viewModel: ProductsViewModel by viewModels()

    companion object {
        fun newInstance() = ProductsFragment()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentProductsBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
//        val viewModel: ProductsViewModel by viewModels {
//            ProductsViewModel.createFactory(requireActivity().application)
//        }
        binding.viewModel = viewModel
        binding.productsGrid.adapter = ProductsAdapter()
        return binding.root
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        val binding = FragmentProductsBinding.inflate(layoutInflater)
//        super.onViewCreated(view, savedInstanceState)
//
////        val viewModel: ProductsViewModel by viewModels {
////            ProductsViewModel.createFactory(requireActivity().application)
////        }
//        viewModel.products.observe(viewLifecycleOwner, Observer { products ->
//            // Update the adapter data when LiveData changes
//            (binding.productsGrid.adapter as? ProductsAdapter)?.submitList(products)
//        })
//        Toast.makeText(context,"test",Toast.LENGTH_SHORT).show()
//
//        binding.viewModel = viewModel
//        binding.productsGrid.adapter = ProductsAdapter()
//
//    }


}