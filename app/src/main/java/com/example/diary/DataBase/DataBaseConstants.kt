package com.example.diary.DataBase

import android.provider.BaseColumns

object DataBaseConstants {
    const val TABLE_NAME = "mytable"
    const val COLUMN_NAME_NAME = "name"
    const val COLUMN_NAME_DESCRIPTION = "description"
    const val COLUMN_NAME_DATE_START = "date_start"
    const val COLUMN_NAME_DATE_FINISH = "date_finish"

    const val DATABASE_VERSION = 1
    const val DATABASE_NAME = "DateBase.db"

    const val CREATE_TABLE="CREATE TABLE IF NOT EXISTS $TABLE_NAME ("+
            "${BaseColumns._ID} INTEGER PRIMARY KEY, $COLUMN_NAME_NAME TEXT, $COLUMN_NAME_DESCRIPTION TEXT, "+
            "$COLUMN_NAME_DATE_START TEXT, $COLUMN_NAME_DATE_FINISH TEXT)"
    const val DELETE_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
}