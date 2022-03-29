package com.example.mapd726_group3_newsbox

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mapd726_group3_newsbox.databinding.FragmentArticleViewBinding
import com.example.mapd726_group3_newsbox.databinding.FragmentHomeBinding

class ArticleViewFragment : Fragment() {


    private var _binding: FragmentArticleViewBinding? = null
    private val binding get() = _binding!!

    private val args:ArticleViewFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticleViewBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val url = args.url
        val webClient= WebViewClient()
        binding.webView.webViewClient =webClient
        binding.webView.loadUrl(url)

        val handler = Handler(Looper.getMainLooper())
        handler.post(object : Runnable {
            override fun run() {
                binding.let {
                    if (it.webView.progress < 100) {
                        it.progressBar2.progress = it.webView.progress
                        handler.postDelayed(this, 100)
                    } else {
                        it.progressBar2.visibility = View.INVISIBLE
                    }
                }
            }

        })

    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }
}