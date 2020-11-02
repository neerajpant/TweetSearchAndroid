package com.twitter.data

import com.twitter.tweets.network.TwitterApi
import com.twitter.tweets.model.ResponseTweet
import com.twitter.tweets.model.ResponseTweets

class FakeDataSource(val tweets:MutableList<ResponseTweet>?= mutableListOf()):
    TwitterApi {
    override suspend fun getTweets(): ResponseTweets {
        tweets?.let {
            return ResponseTweets(true,tweets)
        }
        return ResponseTweets(false, arrayListOf())

    }
}