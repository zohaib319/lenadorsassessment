package com.lenador.assessment.android.adapters

import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.SwitchCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
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
@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Product>?) {
    val adapter = recyclerView.adapter as ProductsAdapter
    adapter.submitList(data)
}