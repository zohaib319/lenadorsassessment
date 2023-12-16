package com.lenador.assessment.android.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lenador.assessment.android.data.Cart
import com.lenador.assessment.android.data.Product
import com.lenador.assessment.android.databinding.CartItemBinding


/**
 * Created By Zohaib on 16/12/2023.
 */




class CartItemsAdapter(private val deleteListener: CartItemDeleteListener): ListAdapter<Cart, CartItemsAdapter.CartItemViewHolder>(DiffCallback) {


    interface CartItemDeleteListener {
        fun onDeleteItem(cartItemId: Int)
    }


    inner class CartItemViewHolder(private var binding: CartItemBinding, private val deleteListener: CartItemDeleteListener): RecyclerView.ViewHolder(binding.root){
        fun bind(cartItem: Cart){
            //Todo: change this
            binding.viewModel = cartItem
            binding.executePendingBindings()

            binding.deleteCartItemButton.setOnClickListener {
                deleteListener.onDeleteItem(cartItem.id)
            }

        }
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of
     * [Product] has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<Cart>() {
        override fun areItemsTheSame(oldItem: Cart, newItem: Cart): Boolean {
            if (oldItem.price == newItem.price){
                Log.d("printed","same")
            }
            return oldItem.price == newItem.price
        }

        override fun areContentsTheSame(oldItem: Cart, newItem: Cart): Boolean {
            if (oldItem.price == newItem.price){
                Log.d("printed","same")
            }
            return oldItem.price == newItem.price
        }
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of
     * [Cart] has been updated.
     */


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartItemViewHolder(binding, deleteListener)

    }

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
        val cartItem = getItem(position)
        holder.bind(cartItem)


    }


}