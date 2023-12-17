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

/**
 * Cart Items Adapter, to be used to display cart items in a list.
 * It implements a delete listener which will help to delete the added products in the cart.
 * It has a diff call back to differentiate between a number of items, so we can avoid
 * adding same products over and over again.
 */
class CartItemsAdapter(private val deleteListener: CartItemDeleteListener): ListAdapter<Cart,
        CartItemsAdapter.CartItemViewHolder>(DiffCallback) {

    /**
     * Interface to handle click event on delete button
     */
    interface CartItemDeleteListener {
        fun onDeleteItem(cartItemId: Int)
    }

    /**
     * Inner class which holds our item in a list. In our instance a cart item.
     *
     */
    inner class CartItemViewHolder(private var binding: CartItemBinding,
                                   private val deleteListener: CartItemDeleteListener):
        RecyclerView.ViewHolder(binding.root){
        fun bind(cartItem: Cart){
            binding.viewModel = cartItem
            binding.executePendingBindings()

            /**
             * Calling interface method when the delete button is pressed. We communicate
             * back to our activity for the changes and update UI.
             */
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

    /**
     * Binding a single item at a time based on the position.
     */
    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
        val cartItem = getItem(position)
        holder.bind(cartItem)


    }


}