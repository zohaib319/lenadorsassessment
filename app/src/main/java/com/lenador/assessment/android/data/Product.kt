package com.lenador.assessment.android.data

/**
 * Created By Zohaib on 15/12/2023.
 */

/**
 * This data class defines a Product which contains a name and a price.
 * We will use this data class to add, read, update and delete our products inside Local DB.
 */
data class Product(
    val name: String,
    val price: Double
){
    /**
     * Companion object to store properties and variables which are meant for this class only.
     */
    companion object {
        const val TABLE_NAME = "products"
        private const val COLUMN_ID = "product_id"
        const val COLUMN_NAME = "product_name"
        const val COLUMN_PRICE = "product_price"

        /**
         * Create new table to store our products
         */
        val CREATE_TABLE = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT,
                $COLUMN_PRICE REAL
            )
        """.trimIndent()
    }
}