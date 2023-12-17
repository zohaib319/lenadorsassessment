package com.lenador.assessment.android.view.newProductDialogue

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.lenador.assessment.android.R
import com.lenador.assessment.android.data.Product
import com.lenador.assessment.android.database.DatabaseHelper
import com.lenador.assessment.android.databinding.NewProductDialogueBinding


/**
 * Created By Zohaib on 16/12/2023.
 */


/**
 * Interface to communicate back to activity that the product is created successfully.
 */
interface ProductAddListener {
    fun onProductAdded()
}

/**
 * Dialogue class which will show to the user a nice dialogue,
 * It has a name and price field.
 * User needs to input to create a product.
 * The Product is then saved to sqlite database.
 */
class NewProductDialogue(context: Context,private val databaseHelper: DatabaseHelper, private val productAddListener: ProductAddListener) : Dialog(context) {


//    /**
//     * Create ViewModel for the dialogue.
//     */
//    private val viewModel: NewProductViewModel by lazy {
//        ViewModelProvider.AndroidViewModelFactory.getInstance(context.applicationContext as Application)
//            .create(NewProductViewModel::class.java)
//    }

    // binding for the dialogue to access different views.
    private lateinit var binding: NewProductDialogueBinding

    /**
     * OnCreate, called when the dialogue is created
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.new_product_dialogue,
            null,
            false
        )
        setContentView(binding.root)

        /** setting dialogue parameters, width and height.
         * Please note that these are fixed values, because app right now is handling a single screen
         * size for tablet.
         */

        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(window?.attributes)
        layoutParams.width = 800
        window?.attributes = layoutParams
        // Set up data binding with the view model
//        binding.viewModel = viewModel

        /**
         * Save Button clicked on dialogue.
         * After confirming that the user did entered some data, we will save it to database.
         */
        binding.saveProductButtonNewProductDialogue.setOnClickListener {
            if (binding.titleNewProductDialogue.text.contentEquals("")){
                Toast.makeText(context,"Please input product name to proceed.",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (binding.priceEtAddNewProductDialogue.text.contentEquals("")){
                Toast.makeText(context,"Please input product price to proceed.",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val product = Product(name = binding.nameEtAddNewProductDialogue.text.toString(),
                price = binding.priceEtAddNewProductDialogue.getNumericValue())
            databaseHelper.addNewProduct(product)
            productAddListener.onProductAdded()
            dismiss()

        }


    }

}