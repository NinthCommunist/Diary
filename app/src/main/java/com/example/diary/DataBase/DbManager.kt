package com.example.diary.DataBase

import android.content.ClipDescription
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import android.util.Log
import com.example.diary.CaseDataClass

class DbManager(val context: Context) {
    val dbHelper=DbHelper(context)
    var db : SQLiteDatabase?=null

    fun Open(){
        db=dbHelper.writableDatabase
    }

    fun Close(){
        db?.close()
    }

    fun InsertToDb(case:CaseDataClass){
        val values = ContentValues().apply {
            put(DataBaseConstants.COLUMN_NAME_NAME, case.name)
            put(DataBaseConstants.COLUMN_NAME_DESCRIPTION, case.description)
            put(DataBaseConstants.COLUMN_NAME_DATE_START, case.date_start)
            put(DataBaseConstants.COLUMN_NAME_DATE_FINISH, case.date_finish)
        }
        db?.insert(DataBaseConstants.TABLE_NAME, null, values)
    }
    fun RemoveFromDb(id:String){
        var selector=BaseColumns._ID+"=$id"
        db?.delete(DataBaseConstants.TABLE_NAME, selector, null)

    }
    fun ReadDb():ArrayList<CaseDataClass>{
        var dataArray=ArrayList<CaseDataClass>()
        val cursore=db?.query(DataBaseConstants.TABLE_NAME, null, null, null, null, null, null)
        with(cursore){
            while (this?.moveToNext()==true){
                var id=getInt(getColumnIndex(BaseColumns._ID))
                var name=getString(getColumnIndex(DataBaseConstants.COLUMN_NAME_NAME))
                var description=getString(getColumnIndex(DataBaseConstants.COLUMN_NAME_DESCRIPTION))
                var date_start=getString(getColumnIndex(DataBaseConstants.COLUMN_NAME_DATE_START))
                var date_finish=getString(getColumnIndex(DataBaseConstants.COLUMN_NAME_DATE_FINISH))
                var case=CaseDataClass(id, name, description, date_start, date_finish)
                dataArray.add(case)
            }
        }
        cursore?.close()
        return dataArray
    }


}