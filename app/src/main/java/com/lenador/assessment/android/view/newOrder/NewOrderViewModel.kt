package com.lenador.assessment.android.view.newOrder

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lenador.assessment.android.data.Cart
import com.lenador.assessment.android.data.Order
import com.lenador.assessment.android.database.DatabaseHelper
import java.text.SimpleDateFormat
import java.util.Date
import java.util.UUID


/**
 * Created By Zohaib on 15/12/2023.
 */


enum class CartItemsStatus {LOADING,DONE,ERROR}

enum class OrderPlaceStatus {PLACED}


class NewOrderViewModel(application: Application) : ViewModel()  {

    private val _status = MutableLiveData<String>()
//    val status : LiveData<String> = _status

    private val _orderStatus = MutableLiveData<String>()
    val orderStatus : LiveData<String> = _orderStatus

    private val _cartItems = MutableLiveData<List<Cart>>()
    val cartItems : LiveData<List<Cart>> = _cartItems

    // for billing calculation
    private val _totalItems = MutableLiveData<Int>()
    val totalItems: LiveData<Int> = _totalItems

    private val _subTotalAmount = MutableLiveData<Double>()
    val subTotalAmount : LiveData<Double> = _subTotalAmount

    private val _totalTax = MutableLiveData<Double>()
    val totalTax : LiveData<Double> = _totalTax

    private val _totalAmount = MutableLiveData<Double>()
    val totalAmount : LiveData<Double> = _totalAmount

    /**
     * Initializing dbHelper by lazy so it is only initialized when accessed for the first time.
     */
    private val dbHelper: DatabaseHelper by lazy {
        DatabaseHelper(application)
    }

    init {
        loadCartItems()
    }

    fun deleteCartItem(cartItem:Int){
         _cartItems.value = _cartItems.value?.filterNot { it.id == cartItem }
        dbHelper.deleteCartItem(cartItem)
        calculateBilling()
    }

    fun addOrder() {
        val order = Order(
            transactionId = UUID.randomUUID().toString(),
            status = "Paid",
            amount = String.format("%.2f", _totalAmount.value ?: 0.0),
            items = _totalItems.value ?: 0,
            quantity = _totalItems.value ?: 0,
            createdDate = getCurrentTimestamp()
        )

        // Save the order to the database
        val id = dbHelper.addOrder(order)


        Log.d("inserted at","$id")

        // Delete all cart items
        _cartItems.value?.forEach { cartItem ->
            dbHelper.deleteCartItem(cartItem.id)
        }
        _cartItems.value = emptyList()

        // Recalculate billing after deleting cart items
        calculateBilling()

        if (id != -1L){
            _orderStatus.value = OrderPlaceStatus.PLACED.toString()
        }


    }
    @SuppressLint("SimpleDateFormat")
    private fun getCurrentTimestamp(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return sdf.format(Date())
    }

    // load cart items and calculate billing based on the products.
    fun loadCartItems(){
        _status.value = CartItemsStatus.LOADING.toString()
        try{
            _cartItems.value = dbHelper.getAllCartItems()
            _status.value = CartItemsStatus.DONE.toString()
            calculateBilling()
        }catch (e: Exception){
            _status.value = CartItemsStatus.ERROR.toString()
            _cartItems.value = listOf()
            calculateBilling()
        }
    }

    // calculate billing everytime there is an update in the cart.
    private fun calculateBilling() {
        val total = _cartItems.value?.sumOf { it.quantity } ?: 0
        _totalItems.value = total
        _totalItems.value = _cartItems.value?.size ?: 0

        val subTotalAmount = _cartItems.value?.sumOf { it.price } ?: 0.0
        _subTotalAmount.value = subTotalAmount

        val totalTax = _cartItems.value?.sumOf { it.tax } ?: 0.0
        _totalTax.value = totalTax

        val totalAmount = subTotalAmount + totalTax
        _totalAmount.value = totalAmount

    }
    

    // factory method to get application context and access database helper with application
    // context.
    companion object {
        fun createFactory(application: Application): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(NewOrderViewModel::class.java)) {
                        return NewOrderViewModel(application) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
        }
    }
}