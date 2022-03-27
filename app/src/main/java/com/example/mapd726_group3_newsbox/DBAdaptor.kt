package com.example.mapd726_group3_newsbox
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//
//class DBAdaptor(private val articleList: ArrayList<Article>): RecyclerView.Adapter<DBAdaptor.MyViewHolder>() {
//
//
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.individual_article,
//        parent,  false)
//        return MyViewHolder(itemView)
//    }
//
//    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        val currentItem = articleList[position]
//        holder.title1.text = currentItem.title
//        holder.summary.text = currentItem.summary
//    }
//
//    override fun getItemCount(): Int {
//        return articleList.size
//    }
//
//    class MyViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){
//        val title1: TextView = itemView.findViewById(R.id.title)
//
//        val summary: TextView = itemView.findViewById(R.id.title2)
//    }
//}