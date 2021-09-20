package com.example.diary

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.DatePicker
import com.example.diary.DataBase.DbManager
import com.example.diary.databinding.ActivityCaseBinding
import java.text.SimpleDateFormat
import java.util.*

class CaseActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {
    lateinit var binding: ActivityCaseBinding
    private var year=0
    private var mounth=0
    private var day=0

    private var savedYear=0
    private var savedMounth=0
    private var savedDay=0

    private var dateStart=""
    private var dateFinish=""
    var dbManager=DbManager(this)




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCaseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()

    }

    override fun onResume() {
        super.onResume()
        dbManager.Open()
    }

    private fun init(){
        if(intent.getStringExtra(IntentConstants.I_CODE)=="TRUE"){

            ifClickonItem()
        }

        else {

            getDateCalendar()
            binding.btnCalendar.setOnClickListener {
                DatePickerDialog(this, this, year, mounth, day).show()
            }

            binding.btnSave.setOnClickListener {
                if (!isNullError()) {
                    var case = CaseDataClass(
                        0,
                        binding.edName.text.toString(),
                        binding.edDescription.text.toString(),
                        dateStart,
                        dateFinish
                    )
                    dbManager.InsertToDb(case)
                    finish()
                }

            }
        }
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        savedYear=p1
        savedMounth=p2
        savedDay=p3
        createTimestamp()
        binding.tvDate.text="${getDateFromTimestamp(dateStart)}"
    }

    private fun getDateCalendar(){
        val calendar = Calendar.getInstance()
        year=calendar.get(Calendar.YEAR)
        mounth=calendar.get(Calendar.MONTH)
        day=calendar.get(Calendar.DAY_OF_MONTH)
        savedYear=year
        savedMounth=mounth
        savedDay=day
        createTimestamp()
    }

    private fun createTimestamp(){
        val calendar=Calendar.getInstance()
        calendar.set(savedYear, savedMounth, savedDay, 14, 0)
        dateStart=calendar.time.time.toString()
        calendar.set(savedYear, savedMounth, savedDay, 15, 0)
        dateFinish=calendar.time.time.toString()

    }
    private fun getDateFromTimestamp(timestamp:String):String{
        try {
            val sdf = SimpleDateFormat("dd.MM.yyyy")
            val netDate = Date(timestamp.toLong())
            return sdf.format(netDate)
        } catch (e: Exception) {
            return e.toString()
        }
    }

    private fun isNullError():Boolean{
        var isnull=false
        if (binding.edName.text.isNullOrEmpty()){
            binding.edName.error="Поле пустое"
            isnull=true
        }
        if (binding.edDescription.text.isNullOrEmpty()){
            binding.edDescription.error="Поле пустое"
            isnull=true
        }
        return isnull
    }

    override fun onDestroy() {
        dbManager.Close()
        super.onDestroy()
    }

    private fun ifClickonItem(){
        val intent=intent
        if (intent==null) return

        binding.apply {
            edName.setText(intent.getStringExtra(IntentConstants.I_NAME))
            edDescription.setText(intent.getStringExtra(IntentConstants.I_DESC))
            tvDate.text=intent.getStringExtra(IntentConstants.I_DATA_START)
            btnSave.text="Выйти"
            btnSave.setOnClickListener { finish() }
            btnCalendar.visibility=View.GONE
        }




        }






}