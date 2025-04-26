package com.example.calmcode

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import retrofit2.Call
import java.util.Calendar
import retrofit2.Callback
import retrofit2.Response
import com.bumptech.glide.Glide
import com.example.calmcode.utils.NewsResponse
import com.example.calmcode.utils.RedditResponse
import com.example.calmcode.utils.RedditRetrofitInstance
import com.example.calmcode.utils.RetrofitInstance


class HomeFragment : Fragment() {

//    private lateinit var featuredImage: ImageView
//    private lateinit var featuredTitle: TextView
//    private lateinit var featuredButton: Button
//    private lateinit var featuredImage2: ImageView
//    private lateinit var featuredTitle2: TextView
//    private lateinit var featuredButton2: Button
//    private lateinit var featuredImage3: ImageView
//    private lateinit var featuredTitle3: TextView
//    private lateinit var featuredButton3: Button
    private var articleUrl: String? = null
    private lateinit var tvDailyQuote: TextView

    private val quotesList = listOf(
        "Believe you can and you're halfway there.",
        "You miss 100% of the shots you don’t take.",
        "No matter what happens. Keep Moving Forward",
        "Don't just watch the clock. Do what it does. Move Froward",
        "Everything you can imagine is real.",
        "Do something today that your future self will thank you for.",
        "He who has a why to live can bear almost any how.",
        "Happiness depends upon ourselves.",
        "The unexamined life is not worth living.",
        "You must be the change you wish to see in the world.",
        "Man is not worried by real problems so much as by his imagined anxieties.",
        "To live is the rarest thing in the world. Most people exist, that is all.",
        "The more you know, the more you realize you don't know.",
        "It is not length of life, but depth of life.",
        "We suffer more in imagination than in reality.",
        "Knowing yourself is the beginning of all wisdom.",
        "Life is really simple, but we insist on making it complicated.",
        "The only true wisdom is in knowing you know nothing.",
        "The mind is everything. What you think you become.",
        "Our life is what our thoughts make it.",
        "The soul becomes dyed with the color of its thoughts.",
        "Do not spoil what you have by desiring what you have not.",
        "Act without expectation.",
        "Be tolerant with others and strict with yourself.",
        "The obstacle is the way.",
        "Don’t explain your philosophy. Embody it."
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        to reset quotes
//        requireContext().getSharedPreferences("QuotesPrefs", Context.MODE_PRIVATE).edit().clear().apply()


        tvDailyQuote = view.findViewById(R.id.tvDailyQuote)
        handleQuoteChange(tvDailyQuote)

//        featuredImage = view.findViewById(R.id.featuredImagecard)
//        featuredTitle = view.findViewById(R.id.featuredTextcard)
//        featuredButton = view.findViewById(R.id.featuredButtoncard)
//
//        featuredImage2 = view.findViewById(R.id.featuredImagecard2)
//        featuredTitle2 = view.findViewById(R.id.featuredTextcard2)
//        featuredButton2 = view.findViewById(R.id.featuredButtoncard2)
//
//        featuredImage3 = view.findViewById(R.id.featuredImagecard3)
//        featuredTitle3 = view.findViewById(R.id.featuredTextcard3)
//        featuredButton3 = view.findViewById(R.id.featuredButtoncard3)

        fetchFeaturedArticles(view)

//        featuredButton.setOnClickListener {
//            articleUrl?.let {
//                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
//                startActivity(intent)
//            } ?: Toast.makeText(requireContext(), "No article to open", Toast.LENGTH_SHORT).show()
//        }

        val btnDaily = view.findViewById<Button>(R.id.btnDaily)
        val btnFavorite = view.findViewById<Button>(R.id.btnFavorite)
        val btnCourse = view.findViewById<Button>(R.id.btnCourse)
        val btnDownloads = view.findViewById<Button>(R.id.btnDownloads)
        val btnCommunity = view.findViewById<Button>(R.id.btnCommunity)
        val btnWorkshop = view.findViewById<Button>(R.id.btnWorkshop)
        val btnCoaching = view.findViewById<Button>(R.id.btnCoaching)
        val btnPlans = view.findViewById<Button>(R.id.btnPlans)

        val greeting = view.findViewById<TextView>(R.id.greeting)
        greeting.text = getGreetingMessage()

        loadStreak(requireContext(), view.findViewById(R.id.streakCounter))

        btnDaily.setOnClickListener {
            Toast.makeText(requireContext(), "Opening Genre Selection screen", Toast.LENGTH_SHORT).show()
            startActivity(Intent(requireContext(), MusicGenresActivity::class.java))
        }

        btnFavorite.setOnClickListener {
            Toast.makeText(requireContext(), "Going to Music Selection", Toast.LENGTH_SHORT).show()
            startActivity(Intent(requireContext(), MusicGenresActivity::class.java))
        }

        btnDownloads.setOnClickListener {
            Toast.makeText(requireContext(), "Going to Downloads Selection", Toast.LENGTH_SHORT).show()
            startActivity(Intent(requireContext(), MusicDownloadsActivity::class.java))
        }
    }
    private fun getGreetingMessage(): String {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)

        return when (hour) {
            in 1..11 -> "Good Morning"
            in 12..17 -> "Good Afternoon"
            in 18..24 -> "Good Evening"
            else -> "Good Day"
        }
    }

    private fun handleQuoteChange(tv: TextView) {
        val prefs = requireContext().getSharedPreferences("QuotesPrefs", Context.MODE_PRIVATE)

        val now = System.currentTimeMillis()
        val lastUpdateMillis = prefs.getLong("lastQuoteUpdateTime", 0L)
        val twoHoursInMillis = 2 * 60 * 60 * 1000
//        for debugging (comment out above code)
//        val twoMinsInMillis = 2 * 60 * 1000L // 2 minutes in milliseconds
//        val twoHoursInMillis = twoMinsInMillis

        // If not enough time has passed, show the last quote
        if (now - lastUpdateMillis < twoHoursInMillis) {
            val lastQuote = prefs.getString("lastQuote", "Keep Moving Forward!")
            tv.text = lastQuote
            return
        }

        // Load and update quote
        val quotesMap = loadQuotesMap(prefs)

        if (quotesMap.values.all { !it }) {
            quotesMap.keys.forEach { quotesMap[it] = true }
        }

        val availableQuotes = quotesMap.filterValues { it }.keys.toList()
        if (availableQuotes.isNotEmpty()) {
            val selectedQuote = availableQuotes.random()
            tv.text = selectedQuote

            quotesMap[selectedQuote] = false

            val rawString = quotesMap.entries.joinToString(";") { "${it.key}|${it.value}" }
            prefs.edit().apply {
                putString("quotesMap", rawString)
                putString("lastQuote", selectedQuote)
                putLong("lastQuoteUpdateTime", now) // <-- track update time
                apply()
            }
        } else {
            tv.text = "Stay inspired!"
        }
    }

    private fun loadQuotesMap(prefs: SharedPreferences): MutableMap<String, Boolean> {
        val rawString = prefs.getString("quotesMap", null)
        val storedMap = if (rawString != null) {
            rawString.split(";").mapNotNull {
                val parts = it.split("|")
                if (parts.size == 2) parts[0] to parts[1].toBoolean() else null
            }.toMap().toMutableMap()
        } else {
            mutableMapOf()
        }
        val storedKeys = storedMap.keys
        val currentKeys = quotesList.toSet()

        return if (storedKeys != currentKeys) {
            quotesList.associateWith { true }.toMutableMap()
        } else {
            storedMap
        }
    }
    fun loadStreak(context: Context, streakTextView: TextView) {
        val prefs = context.getSharedPreferences("StreakPrefs", Context.MODE_PRIVATE)
        val streakCount = prefs.getInt("streakCount", 0)
        streakTextView.text = streakCount.toString()
    }

//    private fun fetchFeaturedArticles(view: View) {
//        RetrofitInstance.api.getArticles().enqueue(object : Callback<List<Article>> {
//            override fun onResponse(call: Call<List<Article>>, response: Response<List<Article>>) {
//                if (response.isSuccessful && !response.body().isNullOrEmpty()) {
//                    val articles = response.body()!!
//                    val cards = listOf(
//                        Triple(
//                            view.findViewById<ImageView>(R.id.featuredImagecard),
//                            view.findViewById<TextView>(R.id.featuredTextcard),
//                            view.findViewById<Button>(R.id.featuredButtoncard)
//                        ),
//                        Triple(
//                            view.findViewById<ImageView>(R.id.featuredImagecard2),
//                            view.findViewById<TextView>(R.id.featuredTextcard2),
//                            view.findViewById<Button>(R.id.featuredButtoncard2)
//                        ),
//                        Triple(
//                            view.findViewById<ImageView>(R.id.featuredImagecard3),
//                            view.findViewById<TextView>(R.id.featuredTextcard3),
//                            view.findViewById<Button>(R.id.featuredButtoncard3)
//                        )
//                    )
//
//                    for (i in 0 until minOf(articles.size, cards.size)) {
//                        val article = articles[i]
//                        val (img, title, btn) = cards[i]
//
//                        title.text = article.title
//                        Glide.with(this@HomeFragment).load(article.imageUrl).into(img)
//                        btn.setOnClickListener {
//                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
//                            startActivity(intent)
//                        }
//                    }
//                } else {
//                    Toast.makeText(requireContext(), "No articles available", Toast.LENGTH_SHORT).show()
//                }
//            }
//
//            override fun onFailure(call: Call<List<Article>>, t: Throwable) {
//                Toast.makeText(requireContext(), "Failed to load articles", Toast.LENGTH_SHORT).show()
//            }
//        })
//    }
    private fun fetchFeaturedArticles(view: View) {
        val prefs = requireContext().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        val useReddit = prefs.getBoolean("useReddit", false)

        if (useReddit) {
            fetchFromReddit(view)
        } else {
            fetchFromNewsApi(view)
        }
    }
    override fun onResume() {
        super.onResume()
        if (isAdded) {
            fetchFeaturedArticles(requireView())
        }
    }
    private fun fetchFromNewsApi(view: View) {
        val apiKey = getString(R.string.news_api_key)

        RetrofitInstance.api.getMeditationArticles(apiKey = apiKey)
            .enqueue(object : Callback<NewsResponse> {
                override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                    if (!isAdded) return

                    try {
                        val body = response.body()
                        if (!response.isSuccessful || body?.articles.isNullOrEmpty()) {
                            Toast.makeText(requireContext(), "No articles available", Toast.LENGTH_SHORT).show()
                            return
                        }

                        val articles = body!!.articles
                        val cards = listOf(
                            Triple(
                                view.findViewById<ImageView>(R.id.featuredImagecard),
                                view.findViewById<TextView>(R.id.featuredTextcard),
                                view.findViewById<Button>(R.id.featuredButtoncard)
                            ),
                            Triple(
                                view.findViewById<ImageView>(R.id.featuredImagecard2),
                                view.findViewById<TextView>(R.id.featuredTextcard2),
                                view.findViewById<Button>(R.id.featuredButtoncard2)
                            ),
                            Triple(
                                view.findViewById<ImageView>(R.id.featuredImagecard3),
                                view.findViewById<TextView>(R.id.featuredTextcard3),
                                view.findViewById<Button>(R.id.featuredButtoncard3)
                            )
                        )

                        for (i in 0 until minOf(articles.size, cards.size)) {
                            val art = articles[i]
                            val (img, title, btn) = cards[i]

                            title.text = art.title

                            if (isAdded) {
                                Glide.with(this@HomeFragment)
                                    .load(art.imageUrl)
                                    .placeholder(R.drawable.ic_launcher_foreground)
                                    .error(R.drawable.ic_launcher_foreground)
                                    .into(img)
                            }


                            btn.setOnClickListener {
                                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(art.url)))
                            }
                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                        Toast.makeText(requireContext(), "Error loading articles", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    if (!isAdded) return
                    Toast.makeText(requireContext(), "Failed to load articles", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun fetchFromReddit(view: View) {
        RedditRetrofitInstance.api.getTopMeditationPosts().enqueue(object : Callback<RedditResponse> {
            override fun onResponse(call: Call<RedditResponse>, response: Response<RedditResponse>) {
                if (!response.isSuccessful || response.body()?.data?.children.isNullOrEmpty()) {
                    Toast.makeText(requireContext(), "No Reddit posts available", Toast.LENGTH_SHORT).show()
                    return
                }

                val posts = response.body()!!.data.children.map { it.data }
                val cards = listOf(
                    Triple(
                        view.findViewById<ImageView>(R.id.featuredImagecard),
                        view.findViewById<TextView>(R.id.featuredTextcard),
                        view.findViewById<Button>(R.id.featuredButtoncard)
                    ),
                    Triple(
                        view.findViewById<ImageView>(R.id.featuredImagecard2),
                        view.findViewById<TextView>(R.id.featuredTextcard2),
                        view.findViewById<Button>(R.id.featuredButtoncard2)
                    ),
                    Triple(
                        view.findViewById<ImageView>(R.id.featuredImagecard3),
                        view.findViewById<TextView>(R.id.featuredTextcard3),
                        view.findViewById<Button>(R.id.featuredButtoncard3)
                    )
                )

                for (i in 0 until minOf(posts.size, cards.size)) {
                    val post = posts[i]
                    val (img, title, btn) = cards[i]

                    title.text = post.title
                    val thumbnailUrl = if (post.thumbnail.startsWith("http")) post.thumbnail else null

                    Glide.with(this@HomeFragment)
                        .load(thumbnailUrl ?: R.drawable.ic_launcher_foreground)
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .error(R.drawable.ic_launcher_foreground)
                        .into(img)

                    btn.setOnClickListener {
                        val redditUrl = "https://www.reddit.com${post.permalink}"
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(redditUrl)))
                    }
                }
            }

            override fun onFailure(call: Call<RedditResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Failed to load Reddit posts", Toast.LENGTH_SHORT).show()
            }
        })
    }
//    private fun fetchFromNewsApi(view: View) {
//        val apiKey = getString(R.string.news_api_key)
//        RetrofitInstance.api
//            .getMeditationArticles(apiKey = apiKey)
//            .enqueue(object: Callback<NewsResponse> {
//                override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
//                    if (!response.isSuccessful || response.body()?.articles.isNullOrEmpty()) {
//                        Toast.makeText(requireContext(), "No articles available", Toast.LENGTH_SHORT).show()
//                        return
//                    }
//                    val articles = response.body()!!.articles
//                    val cards = listOf(
//                        Triple(
//                            view.findViewById<ImageView>(R.id.featuredImagecard),
//                            view.findViewById<TextView>(R.id.featuredTextcard),
//                            view.findViewById<Button>(R.id.featuredButtoncard)
//                        ),
//                        Triple(
//                            view.findViewById<ImageView>(R.id.featuredImagecard2),
//                            view.findViewById<TextView>(R.id.featuredTextcard2),
//                            view.findViewById<Button>(R.id.featuredButtoncard2)
//                        ),
//                        Triple(
//                            view.findViewById<ImageView>(R.id.featuredImagecard3),
//                            view.findViewById<TextView>(R.id.featuredTextcard3),
//                            view.findViewById<Button>(R.id.featuredButtoncard3)
//                        )
//                    )
//
//                    for (i in 0 until minOf(articles.size, cards.size)) {
//                        val art = articles[i]
//                        val (img, title, btn) = cards[i]
//
//                        title.text = art.title
//                        Glide.with(this@HomeFragment)
//                            .load(art.imageUrl)
//                            .placeholder(R.drawable.ic_launcher_foreground)
//                            .error(R.drawable.ic_launcher_foreground)
//                            .into(img)
//
//                        btn.setOnClickListener {
//                            startActivity(
//                                Intent(Intent.ACTION_VIEW, Uri.parse(art.url))
//                            )
//                        }
//                    }
//                }
//
//                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
//                    Toast.makeText(requireContext(), "Failed to load articles", Toast.LENGTH_SHORT).show()
//                }
//            })
//    }
}

