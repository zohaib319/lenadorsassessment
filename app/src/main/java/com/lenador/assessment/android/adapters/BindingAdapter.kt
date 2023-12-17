package com.lenador.assessment.android.adapters

import android.annotation.SuppressLint
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.SwitchCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lenador.assessment.android.data.Order
import com.lenador.assessment.android.data.Product

/**
 * Created By Zohaib on 15/12/2023.
 */

/**
 * This binding adapter will be used to to create custom setters. For instance,
 * we need to set a double price on a textview. Android wont let us set this value using predefined
 * setter setText(). So for these kind of operations we will use binding adapter.
 * Methods defined here will be used in xml ion conjunction with our viewModel.
 */

/**
 * Setting switch state from the Boolean. Used for settings screen.
 */
@BindingAdapter("app:switchState")
fun setSwitchState(switch: SwitchCompat, switchState: Boolean) {
    if (switch.isChecked != switchState) {
        switch.isChecked = switchState
    }
}

/**
 * Set Double price on textViews.
 */
@BindingAdapter("setPriceFromDouble")
fun setDoublePrice(textView: AppCompatTextView, doublePrice: Double){
    textView.text = "$doublePrice"
}

/**
 * Set Double Price on AppCompatTextView.
 */
@SuppressLint("SetTextI18n")
@BindingAdapter("setProductPrice")
fun setProductPrice(textView: AppCompatTextView, doublePrice: Double){
    textView.text = "$doublePrice AED"
}

/**
 * Setting quantity on textview from an integer.
 */
@BindingAdapter("setQuantity")
fun setQuantity(textView: AppCompatTextView, quantity: Int){
    textView.text = "$quantity"
}

/**
 * List Data setter for available and added products
 */
@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Product>?) {
    val adapter = recyclerView.adapter as ProductsAdapter
    adapter.submitList(data)

}

/**
 * Setter to set orders list on orders recyclerview.
 */
@BindingAdapter("setOrders")
fun bindOrdersRecyclerView(recyclerView: RecyclerView,data: List<Order>){
    val adapter = recyclerView.adapter as OrdersAdapter
    adapter.submitList(data)
}