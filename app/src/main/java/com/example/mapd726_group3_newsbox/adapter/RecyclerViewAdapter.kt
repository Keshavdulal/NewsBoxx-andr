package com.example.mapd726_group3_newsbox.adapter

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mapd726_group3_newsbox.Article
import com.example.mapd726_group3_newsbox.R
import com.example.mapd726_group3_newsbox.databinding.CardViewDesignBinding

class RecyclerViewAdapter(private val mList: List<Article>, private val onItemClick: (String) -> Unit ,private val onBookmark: (Article) -> Unit,mContext:Context) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    var app_context = mContext


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
        // Share the link (Copy to clipboard)
        holder.button_share.setOnClickListener {
            val textTocopy = article.source
            val clipboard = app_context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("text",textTocopy)
            clipboard.setPrimaryClip(clipData)
            // Toast message
            Toast.makeText(app_context,"Link copied to clipboard. Share it! ",Toast.LENGTH_LONG).show()

        }

        holder.onBind(article, onItemClick,onBookmark)

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {

        private val binding: CardViewDesignBinding = CardViewDesignBinding.bind(itemView)
        val button_share: Button = itemView.findViewById(R.id.button_share)


        fun onBind(article: Article, onItemClick: (String) -> Unit, onBookmark: (Article) -> Unit) {
            binding.articleTitle.text = article.title
            binding.articleBody.text = article.body
            binding.articleSource.text = article.source
            val imageResource = if (article.isBookmarked) R.drawable.ic_bookmark else R.drawable.ic_bookmark_border
            val share_resource = R.drawable.share
            binding.shareImage.setImageResource(share_resource)
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
            binding.shareImage.setOnClickListener{
               //share_image(article.source)
            }

        }
    }
    private fun share_image(source: String?) {
        // Share the link (Copy to clipboard)
        val textTocopy = source

        val clipboard = app_context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("text",textTocopy)
        clipboard.setPrimaryClip(clipData)
        // Toast message
        Toast.makeText(app_context,"Link copied to clipboard. Share it! ", Toast.LENGTH_LONG).show()

    }
}

