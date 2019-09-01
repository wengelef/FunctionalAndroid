package com.wengelef.cache

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

object DB {
    fun driver(context: Context): SqlDriver = AndroidSqliteDriver(Database.Schema, context, "test.db")
}