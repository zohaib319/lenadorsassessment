package com.lenador.assessment.android.view.newOrder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lenador.assessment.android.R
import com.lenador.assessment.android.view.products.ProductsFragment

class NewOrderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_order)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, ProductsFragment())
                .commit()
        }
    }
}