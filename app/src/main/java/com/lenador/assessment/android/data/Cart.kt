package com.lenador.assessment.android.data

/**
 * Created By Zohaib on 15/12/2023.
 */

/**
 * This data class Cart can contain number of products. We will use this data class
 * to add, read, update, and delete products inside a cart.
 */
data class Cart(
    val id: Int,
    val name:String,
    val price: Double,
    val tax: Double,
    val discount: Double,
    val quantity: Int,
    val itemPrice: Double

){
    companion object {
        const val TABLE_NAME = "cart_items"
        const val COLUMN_ID = "cart_item_id"
        const val COLUMN_NAME = "name"
        const val COLUMN_PRICE = "price"
        const val COLUMN_TAX = "tax"
        const val COLUMN_DISCOUNT = "discount"
        const val COLUMN_QUANTITY = "quantity"
        const val COLUMN_ITEM_PRICE = "item_price"

        /**
         * To Create a new table which will store our orders
         */
        val CREATE_TABLE = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT,
                $COLUMN_PRICE REAL,
                $COLUMN_TAX REAL,
                $COLUMN_DISCOUNT REAL,
                $COLUMN_QUANTITY INTEGER,
                $COLUMN_ITEM_PRICE REAL
            )
        """.trimIndent()


    }
}