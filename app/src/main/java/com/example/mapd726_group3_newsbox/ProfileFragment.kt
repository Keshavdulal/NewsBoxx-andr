package com.example.mapd726_group3_newsbox

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mapd726_group3_newsbox.adapter.RecyclerViewAdapter
import com.example.mapd726_group3_newsbox.databinding.FrgamentProfileBinding
import com.google.firebase.firestore.FirebaseFirestore

class ProfileFragment : Fragment() {

    private lateinit var articleArrayList: ArrayList<Article>
    private var _binding: FrgamentProfileBinding?=null
    val binding get() = _binding !!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        articleArrayList = ArrayList()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FrgamentProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val arrayAdapter = ArrayAdapter.createFromResource(requireContext(),R.array.news_sources,android.R.layout.simple_spinner_item)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = arrayAdapter

        binding.spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val selectedNewsSource= p0?.getItemAtPosition(p2) as String
                getNewsData(selectedNewsSource)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

        binding.spinner.setSelection(0)

        val layoutManager= LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false)
        binding.rNewsList.layoutManager= layoutManager

    }


    private fun getNewsData(newsSource:String)
    {
        val database= FirebaseFirestore.getInstance()
        articleArrayList.clear()
        database.collection("ScrapedFeed").document(newsSource)
            .get().addOnSuccessListener {
                if (it.exists()) {
                    val data = it.data
                    data?.let {
                        for ((key, value) in data){
                            val map = value as Map<*,*>
                            articleArrayList.add(map.toArticle(newsSource))
                        }
                    }
                    val recyclerViewAdapter = RecyclerViewAdapter(articleArrayList){url->
                        //on item click
                        val intent= Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse(url)
                        startActivity(intent)
                    }
                    binding.rNewsList.adapter = recyclerViewAdapter

                }
            }.addOnFailureListener{
                Log.e("firebase", "Error getting data", it)
            }
    }


    fun Map<*,*>.toArticle(source: String):Article{
        val v = this
        val title = v["title"] as String
        val summary = v["summary"] as String
        val contentHtml = v["content_html"] as String
        val datePublished = v["date_published"] as String
        val guid = v["guid"] as String
        val url = v["url"] as String

        return Article(contentHtml,datePublished,guid,summary,title,url,source,R.drawable.default_article)

    }


}