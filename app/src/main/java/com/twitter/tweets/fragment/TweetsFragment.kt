package com.twitter.tweets.fragment

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.twitter.tweets.R

import com.twitter.tweets.model.ResponseTweet
import com.twitter.tweets.model.ResponseTweets
import com.twitter.tweets.model.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_tweet_test.*
import kotlinx.android.synthetic.main.fragment_tweets.tweets_recycler_view

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TweetsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class TweetsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val viewModel by viewModels<TweetsViewModel>()
    private lateinit var adapter: TweetsAdapter
   private lateinit var query:String
    private lateinit var responseTweet:List<ResponseTweet>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tweet_test, container, false)
        // return inflater.inflate(R.layout.fragment_tweets, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setView()
        setUpObserver()

    }

    private fun setView() {
        adapter =
            TweetsAdapter(arrayListOf())
        tweets_recycler_view.setHasFixedSize(true)
        tweets_recycler_view.addItemDecoration(
            DividerItemDecoration(
                tweets_recycler_view.context,
                (tweets_recycler_view.layoutManager as LinearLayoutManager).orientation
            )
        )
        tweets_recycler_view.adapter = adapter
        search_imageView.setOnClickListener {
            println("SerarchButtonOnClick")
            text_search.text.toString().let {
                if(it.isNotEmpty())
                    query=it
            }
            if(this::query.isInitialized && query.isNotEmpty())
                adapter.filter.filter(query)
        }

        text_clear.setOnClickListener {
            error_image.visibility = View.GONE
            println("textClear Call")
            text_search.text?.let {
                  it.clear()

            }
            adapter.apply {
                addTweets(responseTweet)
                notifyDataSetChanged()
            }
        }

        // setHasOptionsMenu(true)
    }

    private fun setUpObserver() {
        viewModel.getTweets().observe(viewLifecycleOwner, Observer {
            println("observerCalled")
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        println("Success Call")
                        tweets_recycler_view.visibility = View.VISIBLE
                        progress_bar.visibility = View.GONE
                        error_image.visibility = View.GONE
                        resource.data?.let { tweets -> retrieveList(tweets) }
                    }
                    Status.ERROR -> {
                        println("TweetFragmentError ${it.message}")
                       tweets_recycler_view.visibility = View.VISIBLE
                        progress_bar.visibility = View.GONE
                        error_image.visibility = View.VISIBLE

                    }
                    Status.LOADING -> {

                        progress_bar.visibility = View.VISIBLE
                        tweets_recycler_view.visibility = View.GONE
                    }
                }
            }

        })


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.menu_gallery, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                if (query != null) {
                    tweets_recycler_view.scrollToPosition(0)
                    //  viewModel.searchPhotos(query)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return true
            }
        })
    }

    private fun retrieveList(response: ResponseTweets) {
        println("size ${response.data.size}")
        responseTweet=response.data
        adapter.apply {

            addTweets(response.data)
            notifyDataSetChanged()
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TweetsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TweetsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}