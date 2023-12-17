package com.lenador.assessment.android.view.orders

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lenador.assessment.android.data.Order
import com.lenador.assessment.android.database.DatabaseHelper


enum class OrdersFetchStatus {DONE}
class OrdersViewModel(application: Application) : ViewModel() {

    private val _status = MutableLiveData<String>()
    val status : LiveData<String> = _status

    private val _orders = MutableLiveData<List<Order>>()
    val orders : LiveData<List<Order>> = _orders

    private val dbHelper: DatabaseHelper by lazy {
        DatabaseHelper(application)
    }

    init {
        loadOrders()
    }

    private fun loadOrders(){
        _status.value = OrdersFetchStatus.DONE.toString()
        _orders.value = dbHelper.getAllOrders()
    }



    /**
     * Creating ViewModel factory so we can access application context for our database helper
     * initialization.
     */
    companion object {
        fun createFactory(application: Application): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(OrdersViewModel::class.java)) {
                        return OrdersViewModel(application) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
        }
    }


}