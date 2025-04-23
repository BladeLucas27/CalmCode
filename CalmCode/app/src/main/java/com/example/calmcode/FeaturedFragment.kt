//package com.example.calmcode
//
//import android.content.Intent
//import android.net.Uri
//import android.os.Bundle
//import android.util.Log
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.*
//import com.example.calmcode.R
//import com.example.calmcode.Article
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//import com.bumptech.glide.Glide
//
//class FeaturedFragment : Fragment() {
//
//    private lateinit var featuredImage: ImageView
//    private lateinit var featuredTitle: TextView
//    private lateinit var openButton: Button
//    private var articleUrl: String? = null
//
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return inflater.inflate(R.layout.featured_fragment, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        featuredImage = view.findViewById(R.id.featuredImagecard)
//        featuredTitle = view.findViewById(R.id.featuredTextcard)
//        openButton = view.findViewById(R.id.featuredButtoncard)
//
//        RetrofitInstance.api.getArticles().enqueue(object : Callback<List<Article>> {
//            override fun onResponse(call: Call<List<Article>>, response: Response<List<Article>>) {
//                if (response.isSuccessful && !response.body().isNullOrEmpty()) {
//                    val article = response.body()!!.first() // or use .random()
//                    displayArticle(article)
//                } else {
//                    Toast.makeText(requireContext(), "No articles available", Toast.LENGTH_SHORT).show()
//                }
//            }
//
//            override fun onFailure(call: Call<List<Article>>, t: Throwable) {
//                Toast.makeText(requireContext(), "Failed to load article", Toast.LENGTH_SHORT).show()
//            }
//        })
//    }
//
//    private fun displayArticle(article: Article) {
//        featuredTitle.text = article.title
//
//        Glide.with(this)
//            .load(article.imageUrl)
//            .into(featuredImage)
//
//        openButton.setOnClickListener {
//            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
//            startActivity(intent)
//        }
//    }
//
//
//}
