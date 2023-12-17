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
    val transactionId: String,
    val status: String,
    val amount: String,
    val items: Int,
    val quantity: Int,
    val createdDate: String
){
    /**
     * Companion objects will store the data which is only meant for this class.
     */
    companion object{
        const val TABLE_NAME = "orders"
        private const val COLUMN_ID = "order_id"
        const val COLUMN_TRANSACTION_ID = "order_transaction_id"
        const val COLUMN_ORDER_STATUS = "order_status"
        const val COLUMN_ORDER_AMOUNT = "order_amount"
        const val COLUMN_ORDER_ITEMS = "order_items"
        const val COLUMN_ORDER_QUANTITY = "order_quantity"
        const val COLUMN_CREATED_DATE = "order_created_date"

        /**
         * To Create a new table which will store our orders
         */
        val CREATE_TABLE = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_TRANSACTION_ID TEXT,
                $COLUMN_ORDER_STATUS TEXT,
                $COLUMN_ORDER_AMOUNT TEXT,
                $COLUMN_ORDER_ITEMS INTEGER,
                $COLUMN_ORDER_QUANTITY INTEGER,
                $COLUMN_CREATED_DATE TEXT
            )
        """.trimIndent()

    }
}