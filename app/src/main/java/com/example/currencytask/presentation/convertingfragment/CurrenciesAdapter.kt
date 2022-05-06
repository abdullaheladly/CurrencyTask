package com.example.currencytask.presentation.convertingfragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.currencytask.R
import com.example.currencytask.domain.model.CustomRateClass
import com.example.currencytask.util.DiffUtilCallBack

class CurrenciesAdapter(): RecyclerView.Adapter<CurrenciesAdapter.MyViewHolder>(){
    private var currenciesList= emptyList<CustomRateClass>()

    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.currency_item_row,parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.Title).text=currenciesList[position].key
        holder.itemView.findViewById<TextView>(R.id.Value).text=currenciesList[position].value

    }

    override fun getItemCount(): Int {
        return currenciesList.size
    }

    fun saveData(newActionsList:List<CustomRateClass>){
        val leadsListDiffUtil= DiffUtilCallBack(currenciesList,newActionsList)
        val diffUtilResult= DiffUtil.calculateDiff(leadsListDiffUtil)
        currenciesList=newActionsList
        diffUtilResult.dispatchUpdatesTo(this)
    }

}