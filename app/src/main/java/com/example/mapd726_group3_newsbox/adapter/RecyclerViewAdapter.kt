package com.example.mapd726_group3_newsbox.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mapd726_group3_newsbox.Article
import com.example.mapd726_group3_newsbox.R
import com.example.mapd726_group3_newsbox.databinding.CardViewDesignBinding

class RecyclerViewAdapter(private val mList: List<Article>, private val onItemClick: (String) -> Unit ,private val onBookmark: (Article) -> Unit) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

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

        val article = mList[position]

        holder.onBind(article, onItemClick,onBookmark)

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {

        private val binding: CardViewDesignBinding = CardViewDesignBinding.bind(itemView)


        fun onBind(article: Article, onItemClick: (String) -> Unit, onBookmark: (Article) -> Unit) {
            binding.articleTitle.text = article.title
            binding.articleBody.text = article.body
            binding.articleSource.text = article.source
            val imageResource = if (article.isBookmarked) R.drawable.ic_bookmark else R.drawable.ic_bookmark_border
            binding.bookmarkImage.setImageResource(imageResource)
            binding.cardView.setOnClickListener {
                onItemClick(article.url ?: "")
            }
            binding.bookmarkImage.setOnClickListener {
                if (!article.isBookmarked) {
                    binding.bookmarkImage.setImageResource(R.drawable.ic_bookmark)
                } else {
                    binding.bookmarkImage.setImageResource(R.drawable.ic_bookmark_border)
                }
                article.isBookmarked = !article.isBookmarked
                onBookmark(article)

            }
        }


    }
}

