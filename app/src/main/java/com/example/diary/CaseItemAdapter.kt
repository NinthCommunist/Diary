package com.example.diary

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.diary.DataBase.DbManager
import com.example.diary.databinding.CaseItemsBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CaseItemAdapter(val context: Context, val ArrayOfCase:ArrayList<CaseDataClass>):RecyclerView.Adapter<CaseItemAdapter.ViewHolder>() {

    class ViewHolder(view: View, val context: Context):RecyclerView.ViewHolder(view) {
        val binding=CaseItemsBinding.bind(view)
        private fun getDateFromTimestamp(timestamp:String):String{
            try {
                val sdf = SimpleDateFormat("dd.MM.yyyy :HH:mm ")
                val netDate = Date(timestamp.toLong())
                return sdf.format(netDate)
            } catch (e: Exception) {
                return e.toString()
            }
        }

        fun bind(case:CaseDataClass, context: Context){
            binding.tvName.text=case.name
            binding.tvDateItem.text="${getDateFromTimestamp(case.date_start)} - ${getDateFromTimestamp(case.date_finish).substringAfter(':')}"
            itemView.setOnClickListener {
                val intent = Intent(context, CaseActivity::class.java)
                intent.putExtra(IntentConstants.I_NAME, case.name)
                intent.putExtra(IntentConstants.I_DESC, case.description)
                intent.putExtra(IntentConstants.I_DATA_START, "${getDateFromTimestamp(case.date_start).substringBefore(' ')}")
                intent.putExtra(IntentConstants.I_CODE, "TRUE")
                context.startActivity(intent)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.case_items, parent, false)
        return ViewHolder(view, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(ArrayOfCase[position], context)
    }

    override fun getItemCount(): Int {
        return ArrayOfCase.size
    }

    fun UpdateAdapter(newArrayOfCase: ArrayList<CaseDataClass>){
        ArrayOfCase.clear()
        ArrayOfCase.addAll(newArrayOfCase)
        notifyDataSetChanged()
    }
    fun RemoveApdaterItme(position: Int, db:DbManager){
        db.RemoveFromDb(ArrayOfCase[position].id.toString())
        ArrayOfCase.removeAt(position)
        notifyItemRangeChanged(0, ArrayOfCase.size)
        notifyItemRemoved(position)
    }


}