package com.lenador.assessment.android.view.newOrder

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lenador.assessment.android.data.Cart
import com.lenador.assessment.android.database.DatabaseHelper



/**
 * Created By Zohaib on 15/12/2023.
 */


enum class CartItemsStatus {LOADING,DONE,ERROR}


class NewOrderViewModel(application: Application) : ViewModel()  {

    private val _status = MutableLiveData<String>()
//    val status : LiveData<String> = _status

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