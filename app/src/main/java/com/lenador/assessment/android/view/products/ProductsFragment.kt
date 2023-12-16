package com.lenador.assessment.android.view.products

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.lenador.assessment.android.adapters.ProductsAdapter
import com.lenador.assessment.android.data.Product
import com.lenador.assessment.android.databinding.FragmentProductsBinding
import com.lenador.assessment.android.others.GridSpacingItemDecoration


/**
 * This fragment will display list of available products. we will use ProductsViewModel to fetch
 * and update list of products. LifeCycle observer will automatically detect changes in the
 * LiveData of the Products View Model
 */
class ProductsFragment : Fragment() {

    private var listener: InteractionListener? = null
    val viewModel: ProductsViewModel by viewModels {
        ProductsViewModel.createFactory(requireActivity().application)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentProductsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        // Set up RecyclerView
        val recyclerView = binding.productsGrid
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        recyclerView.addItemDecoration(GridSpacingItemDecoration(10))

        // Use a hardcoded value for spacing between items

        // Apply item decoration to add spacing between items
        recyclerView.adapter = ProductsAdapter { product ->
            onProductClicked(product)
        }

        viewModel.products.observe(viewLifecycleOwner) { products ->
            // Update the adapter data when LiveData changes
            (recyclerView.adapter as? ProductsAdapter)?.submitList(products)
        }
        binding.addProductButtonProductsFragment.setOnClickListener {
            listener?.onAddButtonClicked()
        }

        return binding.root
    }


    /**
     * Handle the product click event. Save the product to cart using ViewModel
     */
    private fun onProductClicked(product: Product) {
        viewModel.saveProductToCart(product)
        listener?.onProductAddedToCart()
    }


    /**
     * Interface to handle plus button click inside activity to add product
     */
    interface InteractionListener{
        fun onAddButtonClicked()
        fun onProductAddedToCart()
    }


    /**
     * Initialize listener when the fragment is attached to the activity
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is InteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement Interaction Listener")
        }
    }





}