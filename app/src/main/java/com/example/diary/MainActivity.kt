package com.example.diary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diary.DataBase.DbManager
import com.example.diary.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    val dbManager=DbManager(this)
    lateinit var ArrayData:ArrayList<CaseDataClass>
    lateinit var adapter: CaseItemAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()


    }

    override fun onResume() {
        super.onResume()
        CoroutineScope(Dispatchers.Main).launch {
            dbManager.Open()
            fillAdapter()
        }

    }

    private fun init(){
        binding.btnAdd.setOnClickListener {
            val intent = Intent(this, CaseActivity::class.java)
            startActivity(intent)
        }
        ArrayData=dbManager.ReadDb()
        adapter= CaseItemAdapter(this, ArrayData)
        binding.rcView.adapter=adapter
        binding.rcView.layoutManager=LinearLayoutManager(this)
        val swap=swap()
        swap.attachToRecyclerView(binding.rcView)



    }

    override fun onDestroy() {
        dbManager.Close()
        super.onDestroy()
    }
    fun fillAdapter(){
        adapter.UpdateAdapter(dbManager.ReadDb())


    }

    private fun swap():ItemTouchHelper{
        return ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
               return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                adapter.RemoveApdaterItme(viewHolder.adapterPosition, dbManager)
            }

        })
    }


}