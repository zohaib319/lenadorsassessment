package com.lenador.assessment.android.adapters

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lenador.assessment.android.data.Order
import com.lenador.assessment.android.databinding.OrderItemBinding
import com.lenador.assessment.android.databinding.OrdersHeaderBinding


/**
 * Created By Zohaib on 17/12/2023.
 */

/**
 * Orders Adapter to show list of orders previously made. These orders are being saved to
 * offline database.
 */
class OrdersAdapter : ListAdapter<Order, RecyclerView.ViewHolder>(DiffCallback) {

    // for header and actual order item.
    private val viewTypeHeader = 0
    private val viewTypeItem = 1

    /**
     * To inflate an order item in the recyclerview.
     */
    class OrderItemViewHolder(private var binding: OrderItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(orderItem: Order){
            binding.viewModel = orderItem
            binding.executePendingBindings()
        }
    }

    /**
     * To inflate a header at the top.
     */
    class OrderHeaderViewHolder(binding: OrdersHeaderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            // Bind headerData to the views in your header layout
        }
    }


    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of
     * [Order] has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<Order>() {
        override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
            if (oldItem.transactionId== newItem.transactionId){
                Log.d("printed","same")
            }
            return oldItem.transactionId == newItem.transactionId
        }

        override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
            if (oldItem.transactionId == newItem.transactionId){
                Log.d("printed","same")
            }
            return oldItem.transactionId == newItem.transactionId
        }
    }

    /**
     * Get item count to add our header instance.
     */
    override fun getItemCount(): Int {
        return super.getItemCount() + 1 // Add 1 for the header
    }

    /**
     * Return view type based on position in the recyclerview.
     * 0 -> Header
     * 1 -> Order Item
     */
    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            viewTypeHeader
        } else {
            viewTypeItem
        }
    }

    /**
     * When the view holder is created, we need to inflate the item for both header and item
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {


        return when (viewType){
            viewTypeHeader -> {
                val binding = OrderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                OrderItemViewHolder(binding)
            }
            viewTypeItem -> {
                val binding = OrdersHeaderBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                OrderHeaderViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid viewType: $viewType")
        }
    }


    /**
     * Bind the views for both header and item.
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is OrderHeaderViewHolder -> {
                holder.bind()
            }
            is OrderItemViewHolder -> {
                val orderItem = getItem(position - 1) // Adjust position for the header
                holder.bind(orderItem)
                if (position % 2 == 0) {
                    // odd item.
                    holder.itemView.setBackgroundColor(Color.parseColor("#d3d3d3"))
                } else {
                    holder.itemView.setBackgroundColor(Color.parseColor("#66961F88"))
                }
            }
        }


    }


}