package com.twitter.tweets.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.twitter.tweets.R

import com.twitter.tweets.model.ResponseTweet


class TweetsAdapter(private val tweets: ArrayList<ResponseTweet>) :
    RecyclerView.Adapter<TweetsAdapter.ViewHolder>() ,Filterable{
     var searchTweets= arrayListOf<ResponseTweet>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(
                parent.context
            ).inflate(R.layout.layout_tweet_second, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return tweets.size
    }

    fun addTweets(response: List<ResponseTweet>) {
        println("TweetsAdapter Response Size ${response.size}")
        this.tweets.apply {
            clear()
             addAll(response)

        }
        println("TweetsAdapter Size ${response.size}")
        //get a copy of list and pass to the empty list
        searchTweets= ArrayList(tweets)
       /* this.searchTweets.apply {
            clear()
            addAll(tweets)

        }*/



    }

    override fun getFilter(): Filter {
        return  tweetsFilter
    }
    //filtering execute in background thread
    private val tweetsFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val filteredList = arrayListOf<ResponseTweet>()
            if (constraint == null || constraint.isEmpty()) {
                filteredList.addAll(searchTweets)
            } else {
                val filterPattern =
                    constraint.toString().toLowerCase().trim { it <= ' ' }
                for (item in searchTweets) {

                    if (item.text.toLowerCase().contains(filterPattern)||
                        item.name.toLowerCase().contains(filterPattern)||
                        item.handle.toLowerCase().contains(filterPattern)
                            )
                    {
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(
            constraint: CharSequence,
            results: FilterResults
        ) {
            tweets.clear()
            tweets.addAll(results.values as ArrayList<ResponseTweet>)
            notifyDataSetChanged()
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tweet = tweets[position]

        holder.tweetName.text = tweet.name
        holder.tweetHandle.text = tweet.handle
        holder.retweetCount.text = tweet.retweetCount.toString()
        holder.tweetFavouriteCount.text = tweet.favoriteCount.toString()
        Glide.with(holder.itemView)
            .load(tweet.profileImageUrl)
            .centerCrop()
            .transition(DrawableTransitionOptions.withCrossFade())
            .error(R.drawable.ic_error)
            .into(holder.tweetProfileImage)

        holder.tweetText.text = tweet.text
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tweetName: TextView = itemView.findViewById(R.id.tweet_creator_name)
        val tweetText: TextView = itemView.findViewById(R.id.tweet_text)
        val tweetHandle: TextView = itemView.findViewById(R.id.tweeter_handle)
        val tweetProfileImage: ImageView = itemView.findViewById(R.id.tweeter_image)
        val tweetFavouriteCount: TextView = itemView.findViewById(R.id.tweet_love_count)
        val retweetCount: TextView = itemView.findViewById(R.id.tweet_retweet_count)


    }


}