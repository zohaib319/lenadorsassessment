package com.lenador.assessment.android.adapters

import android.annotation.SuppressLint
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.SwitchCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lenador.assessment.android.data.Cart
import com.lenador.assessment.android.data.Product

/**
 * Created By Zohaib on 15/12/2023.
 */
@BindingAdapter("app:switchState")
fun setSwitchState(switch: SwitchCompat, switchState: Boolean) {
    if (switch.isChecked != switchState) {
        switch.isChecked = switchState
    }
}

@BindingAdapter("setPriceFromDouble")
fun setDoublePrice(textView: AppCompatTextView, doublePrice: Double){
    textView.text = "$doublePrice"
}
@SuppressLint("SetTextI18n")
@BindingAdapter("setProductPrice")
fun setProductPrice(textView: AppCompatTextView, doublePrice: Double){
    textView.text = "$doublePrice AED"
}
@BindingAdapter("setQuantity")
fun setQuantity(textView: AppCompatTextView, quantity: Int){
    textView.text = "$quantity"
}




@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Product>?) {
    val adapter = recyclerView.adapter as ProductsAdapter
    adapter.submitList(data)
}

@BindingAdapter("setCartProducts")
fun bindCartRecyclerView(recyclerView: RecyclerView,data: List<Cart>?){
    val adapter = recyclerView.adapter as CartItemsAdapter
    adapter.submitList(data)
}