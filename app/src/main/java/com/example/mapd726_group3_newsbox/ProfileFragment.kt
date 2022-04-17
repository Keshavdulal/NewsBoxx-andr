package com.example.mapd726_group3_newsbox

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mapd726_group3_newsbox.adapter.RecyclerViewAdapter
import com.example.mapd726_group3_newsbox.databinding.FrgamentProfileBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment() {

    private lateinit var articleArrayList: ArrayList<Article>
    private var _binding: FrgamentProfileBinding? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        articleArrayList = ArrayList()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FrgamentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val arrayAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.news_sources,
            android.R.layout.simple_spinner_item
        )
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = arrayAdapter

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val selectedNewsSource = p0?.getItemAtPosition(p2) as String
                getBookmarksNews(selectedNewsSource)

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

        binding.spinner.setSelection(0)

        val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.rNewsList.layoutManager = layoutManager

    }


    private fun getNewsData(newsSource: String,bookmarkedList: List<Article>) {

        val database = FirebaseFirestore.getInstance()
        articleArrayList.clear()
        database.collection("ScrapedFeed").document(newsSource)
            .get().addOnSuccessListener {
                if (it.exists()) {
                    val data = it.data
                    data?.let {
                        for ((key, value) in data) {
                            val map = value as Map<*, *>
                            val article = map.toArticle(newsSource)
                            bookmarkedList.forEach {
                                if (it.title == article.title) {
                                    article.isBookmarked=true
                                    return@forEach
                                }
                            }
                            articleArrayList.add(article)
                        }
                    }
                    var context = requireContext()
                    val recyclerViewAdapter =
                        RecyclerViewAdapter(articleArrayList, ::onItemClick, ::onBookmarkArticle,context)
                    binding.rNewsList.adapter = recyclerViewAdapter
                    binding.progressBar.visibility=View.GONE

                }
            }.addOnFailureListener{
                binding.progressBar.visibility=View.GONE
                Log.e("firebase", "Error getting data", it)
            }
    }

    private fun getBookmarksNews(newsSource: String) {
        val userId = Firebase.auth.uid ?: return
        val bookmarkedArticles= mutableListOf<Article>()
        binding.progressBar.visibility=View.VISIBLE
        if (newsSource == "Saved"){
            Firebase.firestore.collection("Users").document(userId)
                .collection("UsersFavoriteArticles").get().addOnSuccessListener {
                    if (!it.isEmpty){
                        bookmarkedArticles.addAll(it.toObjects(Article::class.java))
                    }
                    var context = requireContext()
                    val recyclerViewAdapter =
                        RecyclerViewAdapter(bookmarkedArticles, ::onItemClick, ::onBookmarkArticle,context)
                    binding.rNewsList.adapter = recyclerViewAdapter
                    binding.progressBar.visibility=View.GONE

                }.addOnFailureListener {
                    binding.progressBar.visibility=View.GONE
                    Toast.makeText(requireContext(),it.localizedMessage,Toast.LENGTH_SHORT).show()
                }

        }else{
            Firebase.firestore.collection("Users").document(userId)
                .collection("UsersFavoriteArticles").whereEqualTo("source",newsSource).get().addOnSuccessListener {
                    if (!it.isEmpty){
                        bookmarkedArticles.addAll(it.toObjects(Article::class.java))
                    }
                    getNewsData(newsSource,bookmarkedArticles)

                }.addOnFailureListener {
                    binding.progressBar.visibility=View.GONE
                    Toast.makeText(requireContext(),it.localizedMessage,Toast.LENGTH_SHORT).show()
                }

        }


    }

    private fun onBookmarkArticle(article: Article) {
        val userId = Firebase.auth.uid ?: return
        val title = article.title ?: return
        if (article.isBookmarked) {
            Firebase.firestore.collection("Users").document(userId)
                .collection("UsersFavoriteArticles").document(title).set(article)
                .addOnSuccessListener {
                    Toast.makeText(
                        requireContext(),
                        "Articles successfully added to bookmarks",
                        Toast.LENGTH_SHORT
                    ).show()
                }.addOnFailureListener {
                Toast.makeText(
                    requireContext(),
                    it.localizedMessage,
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Firebase.firestore.collection("Users").document(userId)
                .collection("UsersFavoriteArticles").document(title).delete().addOnSuccessListener {
                Toast.makeText(
                    requireContext(),
                    "Articles successfully removed from bookmarks",
                    Toast.LENGTH_SHORT
                ).show()
            }.addOnFailureListener {
                Toast.makeText(
                    requireContext(),
                    it.localizedMessage,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


    }

    private fun onItemClick(url: String) {
        //on item click
        val action = ProfileFragmentDirections.actionProfileFragmentToArticleViewFragment(url)
        findNavController().navigate(action)
    }


    fun Map<*, *>.toArticle(source: String): Article {
        val v = this
        val title = v["title"] as String
        val summary = v["summary"] as String
        val contentHtml = v["content_html"] as String
        val datePublished = v["date_published"] as String
        val guid = v["guid"] as String
        val url = v["url"] as String

        return Article(
            contentHtml,
            datePublished,
            guid,
            summary,
            title,
            url,
            source,
            R.drawable.default_article
        )

    }


}