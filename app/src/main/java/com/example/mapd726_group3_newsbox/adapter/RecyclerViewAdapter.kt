package com.example.mapd726_group3_newsbox.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mapd726_group3_newsbox.Article
import com.example.mapd726_group3_newsbox.R

class RecyclerViewAdapter (private val mList: List<Article>) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_design, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList[position]

        // sets the image to the imageview from our itemHolder class
        //holder.imageView.setImageResource(ItemsViewModel.image)

        // sets the text to the textview from our itemHolder class
        holder.textView.text = ItemsViewModel.title
        // sets the text to the textview from our itemHolder class
        holder.textView2.text = ItemsViewModel.body

    }

    // return the number of the items in the list
    override fun getItemCount(): Int
    {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
       // val imageView: ImageView = itemView.findViewWithTag(R.drawable.default_article)
        val textView: TextView = itemView.findViewById(R.id.articleTitle)
        val textView2: TextView = itemView.findViewById(R.id.articleBody)

    }
}

