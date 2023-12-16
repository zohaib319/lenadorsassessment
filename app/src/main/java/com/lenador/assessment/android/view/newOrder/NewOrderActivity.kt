package com.lenador.assessment.android.view.newOrder

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.lenador.assessment.android.R
import com.lenador.assessment.android.adapters.CartItemsAdapter
import com.lenador.assessment.android.database.DatabaseHelper
import com.lenador.assessment.android.databinding.ActivityNewOrderBinding
import com.lenador.assessment.android.view.newProductDialogue.NewProductDialogue
import com.lenador.assessment.android.view.newProductDialogue.ProductAddListener
import com.lenador.assessment.android.view.products.ProductsFragment

class NewOrderActivity : AppCompatActivity(), ProductsFragment.InteractionListener, ProductAddListener, CartItemsAdapter.CartItemDeleteListener {

    private lateinit var binding: ActivityNewOrderBinding
    private val viewModel: NewOrderViewModel by viewModels {
        NewOrderViewModel.createFactory(application)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_order)
        binding.lifecycleOwner = this
        binding.newOrderViewModel = viewModel
        // setup recyclerView
        val cartItemsRecyclerView = binding.cartItemsRv
        cartItemsRecyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        cartItemsRecyclerView.adapter = CartItemsAdapter(this)
        viewModel.cartItems.observe(this) { cartItems ->
            // Update the adapter data when LiveData changes
            updateButtonVisibility(cartItems.isNotEmpty())
            (cartItemsRecyclerView.adapter as? CartItemsAdapter)?.submitList(cartItems)
        }
        viewModel.totalItems.observe(this){ totalItems ->
            binding.productsCountTv.text = "$totalItems"
            binding.quantityTv.text = "$totalItems"
        }
        viewModel.subTotalAmount.observe(this){subTotalAmount ->
            binding.subtotalTv.text = "$subTotalAmount AED"
        }
        viewModel.totalTax.observe(this){totalTax ->
            binding.totalTaxTv.text = "$totalTax AED"
        }
        viewModel.totalAmount.observe(this){totalAmount ->
            binding.totalAmountTv.text = "$totalAmount AED"
        }

        binding.cancelButtonNewOrder.setOnClickListener {
            finish()
        }
        binding.clearButtonNewOrder.setOnClickListener {
            Snackbar.make(binding.root,"Clear Order",Snackbar.LENGTH_SHORT).show()
        }
        binding.paymentButtonNewOrder.setOnClickListener {
            Snackbar.make(binding.root,"Payment",Snackbar.LENGTH_SHORT).show()
        }
        binding.discountButtonNewOrder.setOnClickListener {
            Snackbar.make(binding.root,"Discount",Snackbar.LENGTH_SHORT).show()
        }


        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, ProductsFragment())
                .commit()
        }
    }

    private fun updateButtonVisibility(isCartNotEmpty: Boolean){
        if (isCartNotEmpty){
            binding.paymentButtonNewOrder.isEnabled = true
            binding.clearButtonNewOrder.isEnabled = true
            binding.printQuotationButtonNewOrder.isEnabled = true
            binding.suspendButtonNewOrder.isEnabled = true
            binding.discountButtonNewOrder.isEnabled = true
        }
    }
    override fun onAddButtonClicked() {
        /**
         * Create a custom dialogue using our custom class and pass in the database helper.
         */
        val databaseHelper = DatabaseHelper(this)
        val customDialogue = NewProductDialogue(this,databaseHelper,this)
        customDialogue.show()

    }

    /**
     *
     */
    override fun onProductAddedToCart() {
        viewModel.loadCartItems()
    }
    override fun onDeleteItem(cartItemId: Int) {
        viewModel.deleteCartItem(cartItemId)
    }

    override fun onProductAdded() {
        /**
         * To refresh the data inside fragment.
         */
        supportFragmentManager.beginTransaction().remove(ProductsFragment()).commit()
        val newFragment = ProductsFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, newFragment)
            .commit()
    }


}