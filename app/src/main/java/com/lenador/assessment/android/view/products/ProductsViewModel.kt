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
import java.lang.Exception


enum class ProductsFetchStatus  {LOADING,DONE,ERROR}

class ProductsViewModel : ViewModel() {

    private val _status = MutableLiveData<ProductsFetchStatus>()
    val status: LiveData<ProductsFetchStatus> = _status

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

//    private val dbHelper: DatabaseHelper by lazy {
//        DatabaseHelper(application)
//    }

    init {
        Log.d("inside_init","inside_init")
        loadTestData()
    }

//    private fun loadProducts(){
//        viewModelScope.launch {
//            _status.value = ProductsFetchStatus.LOADING
//            try{
//                dbHelper.fetchAvailableProducts()
//                _status.value = ProductsFetchStatus.DONE
//            }catch (e: Exception){
//                _status.value = ProductsFetchStatus.ERROR
//                _products.value = listOf()
//            }
//        }
//    }
    private fun loadTestData() {
        // Sample test data
        val testData = listOf(
            Product(name = "Product 1", price = 10.0),
            Product(name = "Product 2", price = 20.0),
            Product(name = "Product 3", price = 30.0),
            // Add more test products as needed


        )

        // Set the test data to the LiveData
        _products.value = testData

        // Update the status to indicate that the data has been loaded
        _status.value = ProductsFetchStatus.DONE

        Log.d("products_loaded","loaded")


    }
//
//    companion object {
//        fun createFactory(application: Application): ViewModelProvider.Factory {
//            return object : ViewModelProvider.Factory {
//                @Suppress("UNCHECKED_CAST")
//                override fun <T : ViewModel> create(modelClass: Class<T>): T {
//                    if (modelClass.isAssignableFrom(ProductsViewModel::class.java)) {
//                        return ProductsViewModel(application) as T
//                    }
//                    throw IllegalArgumentException("Unknown ViewModel class")
//                }
//            }
//        }
//    }



}