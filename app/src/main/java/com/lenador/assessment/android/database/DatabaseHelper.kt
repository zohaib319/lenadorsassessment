package com.lenador.assessment.android.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.lenador.assessment.android.data.Cart
import com.lenador.assessment.android.data.Order
import com.lenador.assessment.android.data.Product
import com.lenador.assessment.android.data.Product.Companion.COLUMN_NAME
import com.lenador.assessment.android.data.Product.Companion.COLUMN_PRICE
import com.lenador.assessment.android.data.Product.Companion.TABLE_NAME
import com.lenador.assessment.android.others.AppSettings


/**
 * Created By Zohaib on 15/12/2023.
 */

/**
 * Database helper class which contains important CRUD functions for our products, orders, and cart.
 * We will initiate it from a version 1 and a database name
 * Our data class holds all the information about the required tables and their columns.
 */
class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private val sharedPreferences = context.getSharedPreferences(AppSettings.APP_PREFS,Context.MODE_PRIVATE)

    /**
     * Information which is only intended for this class will be added into the companion object, so
     * it is available throughout the project scope without creating an instance of this class.
     */
    companion object{
        private const val DATABASE_NAME = "lenadors_db"
        private const val DATABASE_VERSION = 3
    }
    /**
     * On Create is called when the first time the database is created, even after we increment our
     * versioning. So here we access the tables information from our data class and create new
     * tables
     */
    override fun onCreate(database: SQLiteDatabase?) {
        database?.execSQL(Product.CREATE_TABLE)
        database?.execSQL(Order.CREATE_TABLE)
        database?.execSQL(Cart.CREATE_TABLE)

    }

    /**
     * On Upgrade is called when we upgrade the version of the database. This is required at
     * the later stages of the project development. When we update schemas of the database, we will
     * upgrade our database version and it will update our database with new changes to its tables
     * and columns
     */
    override fun onUpgrade(database: SQLiteDatabase?, p1: Int, p2: Int) {
        database?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        database?.execSQL("DROP TABLE IF EXISTS ${Order.TABLE_NAME}")
        database?.execSQL("DROP TABLE IF EXISTS ${Cart.TABLE_NAME}")
        onCreate(database)
    }

    /**
     * Add new product in sqlite using Content Values.
     * It needs to have a product inside constructor.
     * Returns a long id which is the id inserted lately.
     */
    fun addNewProduct(product: Product): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, product.name)
            put(COLUMN_PRICE, product.price)
        }
        return db.insert(TABLE_NAME, null, values)
    }

    /**
     * Add order to the database.
     */
    fun addOrder(order: Order): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(Order.COLUMN_TRANSACTION_ID, order.transactionId)
            put(Order.COLUMN_ORDER_STATUS, order.status)
            put(Order.COLUMN_ORDER_AMOUNT, order.amount)
            put(Order.COLUMN_ORDER_ITEMS, order.items)
            put(Order.COLUMN_ORDER_QUANTITY, order.quantity)
            put(Order.COLUMN_CREATED_DATE, order.createdDate)
        }
        return db.insert(Order.TABLE_NAME, null, values)
    }

    /**
     * Add a product to Cart
     */
    fun addProductToCart(product: Product): Long {

        val isTaxEnabled = sharedPreferences.getBoolean(AppSettings.TAX_CALCULATIONS_ENABLED, false)

        // Calculating 5% VAT if tax collection is enabled
        val taxPercentage = if (isTaxEnabled) 0.05 else 0.00
        val taxAmount = product.price * taxPercentage

        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(Cart.COLUMN_NAME, product.name)
            put(Cart.COLUMN_PRICE, product.price)
            put(Cart.COLUMN_TAX, taxAmount)
            put(Cart.COLUMN_DISCOUNT, 0.00)
            put(Cart.COLUMN_QUANTITY, 1)
            put(Cart.COLUMN_ITEM_PRICE, product.price)
        }
        return db.insert(Cart.TABLE_NAME, null, values)
    }


    /**
     * Get All Products from the database
     */
    @SuppressLint("Range")
    fun getAllProducts(): List<Product> {
        val productList = mutableListOf<Product>()
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val product = Product(
                    cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                    cursor.getDouble(cursor.getColumnIndex(COLUMN_PRICE))
                )
                productList.add(product)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return productList
    }

    /**
     * Get All Orders
     */
    @SuppressLint("Range")
    fun getAllOrders(): List<Order> {
        val ordersList = mutableListOf<Order>()
        val selectQuery = "SELECT * FROM ${Order.TABLE_NAME}"
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val orderItem = Order(
                    cursor.getString(cursor.getColumnIndex(Order.COLUMN_TRANSACTION_ID)),
                    cursor.getString(cursor.getColumnIndex(Order.COLUMN_ORDER_STATUS)),
                    cursor.getString(cursor.getColumnIndex(Order.COLUMN_ORDER_AMOUNT)),
                    cursor.getInt(cursor.getColumnIndex(Order.COLUMN_ORDER_ITEMS)),
                    cursor.getInt(cursor.getColumnIndex(Order.COLUMN_ORDER_QUANTITY)),
                    cursor.getString(cursor.getColumnIndex(Order.COLUMN_CREATED_DATE)),
                )
                ordersList.add(orderItem)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return ordersList
    }

    /**
     * Get All cart Items
     */
    @SuppressLint("Range")
    fun getAllCartItems(): List<Cart> {
        val cartList = mutableListOf<Cart>()
        val selectQuery = "SELECT * FROM ${Cart.TABLE_NAME}"
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val cartItem = Cart(
                    cursor.getInt(cursor.getColumnIndex(Cart.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(Cart.COLUMN_NAME)),
                    cursor.getDouble(cursor.getColumnIndex(Cart.COLUMN_PRICE)),
                    cursor.getDouble(cursor.getColumnIndex(Cart.COLUMN_TAX)),
                    cursor.getDouble(cursor.getColumnIndex(Cart.COLUMN_DISCOUNT)),
                    cursor.getInt(cursor.getColumnIndex(Cart.COLUMN_QUANTITY)),
                    cursor.getDouble(cursor.getColumnIndex(Cart.COLUMN_ITEM_PRICE))
                )
                cartList.add(cartItem)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return cartList
    }

    /**
     * Delete a cart item from the database. used when the user clicks the delete button
     */
    fun deleteCartItem(cartItemId: Int) {
        val db = this.writableDatabase
        db.delete(Cart.TABLE_NAME, "${Cart.COLUMN_ID} = ?", arrayOf(cartItemId.toString()))
    }







}