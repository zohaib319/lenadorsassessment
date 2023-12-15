package com.lenador.assessment.android.data

/**
 * Created By Zohaib on 15/12/2023.
 */

/**
 * This data class represents an order.
 * An Order contains orderName, orderPrice, orderTax, orderDiscount, productQuantity,
 * productQuantity, and orderTotalAmount.
 * We will use this class to create, read, update, delete orders in the local db.
 */
data class Order(
    val orderName: String,
    val orderPrice: Double,
    val orderTax: Double,
    val orderDiscount: Double,
    val productQuantity: Int,
    val orderTotalAmount: Double

){
    /**
     * Companion objects will store the data which is only meant for this class.
     */
    companion object{
        const val TABLE_NAME = "orders"
        private const val COLUMN_ID = "order_id"
        private const val COLUMN_ORDER_NAME = "order_name"
        private const val COLUMN_ORDER_PRICE = "order_price"
        private const val COLUMN_ORDER_TAX = "order_tax"
        private const val COLUMN_ORDER_DISCOUNT = "order_discount"
        private const val COLUMN_PRODUCT_QUANTITY = "product_quantity"
        private const val COLUMN_ORDER_TOTAL_AMOUNT = "order_total_amount"

        /**
         * To Create a new table which will store our orders
         */
        val CREATE_TABLE = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_ORDER_NAME TEXT,
                $COLUMN_ORDER_PRICE REAL,
                $COLUMN_ORDER_TAX REAL,
                $COLUMN_ORDER_DISCOUNT REAL,
                $COLUMN_PRODUCT_QUANTITY INTEGER,
                $COLUMN_ORDER_TOTAL_AMOUNT REAL
            )
        """.trimIndent()

    }
}