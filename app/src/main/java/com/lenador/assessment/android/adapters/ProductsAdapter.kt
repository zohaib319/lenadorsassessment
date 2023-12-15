package com.lenador.assessment.android.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lenador.assessment.android.data.Product
import com.lenador.assessment.android.databinding.ProductGridItemBinding


/**
 * Created By Zohaib on 16/12/2023.
 */


/**
 * Products Adapter which will be used by the recycler view.
 */
class ProductsAdapter : ListAdapter<Product,ProductsAdapter.ProductViewHolder>(DiffCallback) {

    /**
     * ProductViewHolder class which holds the single view at a time.
     */
    class ProductViewHolder(private var binding: ProductGridItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(product: Product){
            binding.product = product
            binding.executePendingBindings()

        }
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of
     * [Product] has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            if (oldItem.price == newItem.price){
                Log.d("printed","same")
            }
            return oldItem.price == newItem.price
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            if (oldItem.price == newItem.price){
                Log.d("printed","same")
            }
            return oldItem.price == newItem.price
        }
    }

    /**
     * On Create View Holder to inflate the product item when needed.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            ProductGridItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    /**
     * Setting data with binding to a single product at a time when needed.
     */
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product)
    }
}