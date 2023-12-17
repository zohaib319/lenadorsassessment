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


/**
 * New Order activity which holds list of available products, billing buttons, list of cart items.
 */
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
        cartItemsRecyclerView.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)
        cartItemsRecyclerView.adapter = CartItemsAdapter(this)
        viewModel.cartItems.observe(this) { cartItems ->
            // Update the adapter data when LiveData changes
            updateButtonVisibility(cartItems.isNotEmpty())
            (cartItemsRecyclerView.adapter as? CartItemsAdapter)?.submitList(cartItems)
        }

        // observe when the order is placed inside viewModel and show a nice message.
        viewModel.orderStatus.observe(this){status ->
            if (status == "PLACED"){
                Snackbar.make(binding.root,"Your Order Is Saved.", Snackbar.LENGTH_SHORT).show()
            }
        }



        // observer to observe live changes to total items being added to cart in the view model
        viewModel.totalItems.observe(this){ totalItems ->
            binding.productsCountTv.text = "$totalItems"
            binding.quantityTv.text = "$totalItems"
        }
        // observer to observe subTotalAmount changes in the view model
        viewModel.subTotalAmount.observe(this){subTotalAmount ->
            binding.subtotalTv.text = "$subTotalAmount AED"
        }

        // observer to observe the tax changes on the viewModel.
        viewModel.totalTax.observe(this){totalTax ->
            binding.totalTaxTv.text = "$totalTax AED"
        }
        // observer to observe the changes being made to totalAmount in viewModel.
        viewModel.totalAmount.observe(this){totalAmount ->
            binding.totalAmountTv.text = "$totalAmount AED"
        }

        // buttons click handlers.
        binding.cancelButtonNewOrder.setOnClickListener {
            finish()
        }
        binding.clearButtonNewOrder.setOnClickListener {
            Snackbar.make(binding.root,"Clear Order",Snackbar.LENGTH_SHORT).show()
        }
        binding.paymentButtonNewOrder.setOnClickListener {
            viewModel.addOrder()
        }
        binding.discountButtonNewOrder.setOnClickListener {
            Snackbar.make(binding.root,"Discount",Snackbar.LENGTH_SHORT).show()
        }

        // attaching products fragment to show products at the side of activity.
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, ProductsFragment())
                .commit()
        }
    }

    /*  when there are no products added, we dont need the buttons enabled.
    *   so we will enable them once needed.
     */

    private fun updateButtonVisibility(isCartNotEmpty: Boolean){
        if (isCartNotEmpty){
            binding.paymentButtonNewOrder.isEnabled = true
            binding.clearButtonNewOrder.isEnabled = true
            binding.printQuotationButtonNewOrder.isEnabled = true
            binding.suspendButtonNewOrder.isEnabled = true
            binding.discountButtonNewOrder.isEnabled = true
        }
    }

    /**
     * Button listener for the plus button inside fragment.We will show a new dialogue inside
     * activity to add new product to database.
     */
    override fun onAddButtonClicked() {
        /**
         * Create a custom dialogue using our custom class and pass in the database helper.
         */
        val databaseHelper = DatabaseHelper(this)
        val customDialogue = NewProductDialogue(this,databaseHelper,this)
        customDialogue.show()

    }

    /**
     * When the user successfully enters the data and added product item.
     * We need to load cart items again when the user clicks the product from the available products
     */
    override fun onProductAddedToCart() {
        viewModel.loadCartItems()
    }

    /**
     * We need to delete a cart item from the cart, which is available in viewModel
     */
    override fun onDeleteItem(cartItemId: Int) {
        viewModel.deleteCartItem(cartItemId)
    }


    /**
     * Refresh the data inside fragment to show recently added product
     */
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