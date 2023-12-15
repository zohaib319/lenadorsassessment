package com.lenador.assessment.android.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.lenador.assessment.android.data.Order
import com.lenador.assessment.android.data.Product


/**
 * Created By Zohaib on 15/12/2023.
 */

/**
 * Database helper class which contains important CRUD functions for our products, orders, and cart.
 * We will initiate it from a version 1 and a database name
 * Our data class holds all the information about the required tables and their columns.
 */
class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    /**
     * Information which is only intended for this class will be added into the companion object, so
     * it is available throughout the project scope without creating an instance of this class.
     */
    companion object{
        private const val DATABASE_NAME = "lenadors_db"
        private const val DATABASE_VERSION = 1
    }
    /**
     * On Create is called when the first time the database is created, even after we increment our
     * versioning. So here we access the tables information from our data class and create new
     * tables
     */
    override fun onCreate(database: SQLiteDatabase?) {
        database?.execSQL(Product.CREATE_TABLE)
        database?.execSQL(Order.CREATE_TABLE)

    }

    /**
     * On Upgrade is called when we upgrade the version of the database. This is required at
     * the later stages of the project development. When we update schemas of the database, we will
     * upgrade our database version and it will update our database with new changes to its tables
     * and columns
     */
    override fun onUpgrade(database: SQLiteDatabase?, p1: Int, p2: Int) {
        database?.execSQL("DROP TABLE IF EXISTS ${Product.TABLE_NAME}")
        database?.execSQL("DROP TABLE IF EXISTS ${Order.TABLE_NAME}")
        onCreate(database)
    }


    //Todo: Add CRUD for products, cart, orders etc.

    suspend fun fetchAvailableProducts(){

    }



}