package com.lenador.assessment.android.view.products

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.lenador.assessment.android.data.Product
import com.lenador.assessment.android.database.DatabaseHelper
import kotlinx.coroutines.launch


/**
 * Enum to hold status of the data which is being loaded. We can use this enum to show important
 * loading mechanisms while the user waits for the load to complete.
 * SQLite load does not take much time to load, but still we need to be transparent and show some
 * information while the user waiting on the blank screen.
 */
enum class ProductsFetchStatus  {LOADING,DONE,ERROR}

class ProductsViewModel(application: Application) : ViewModel() {

    private val _status = MutableLiveData<ProductsFetchStatus>()
    val status: LiveData<ProductsFetchStatus> = _status

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    /**
     * Initializing dbHelper by lazy so it is only initialized when accessed for the first time.
     */
    private val dbHelper: DatabaseHelper by lazy {
        DatabaseHelper(application)
    }

    init {
        loadProducts()
    }

    fun saveProductToCart(product:Product){
        dbHelper.addProductToCart(product)

    }



    private fun loadProducts(){
        viewModelScope.launch {
            _status.value = ProductsFetchStatus.LOADING
            try{
                _products.value = dbHelper.getAllProducts()
                _status.value = ProductsFetchStatus.DONE
                Log.d("size is ","${products.value?.size}")
            }catch (e: Exception){
                _status.value = ProductsFetchStatus.ERROR
                _products.value = listOf()
            }
        }
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
                    if (modelClass.isAssignableFrom(ProductsViewModel::class.java)) {
                        return ProductsViewModel(application) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
        }
    }



}