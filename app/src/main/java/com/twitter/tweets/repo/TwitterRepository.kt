package com.twitter.tweets.repo


import com.twitter.tweets.network.TwitterApi
import com.twitter.tweets.model.ResponseTweets
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TwitterRepository@Inject constructor(private val twitterApi: TwitterApi) {

    suspend fun getTweets():ResponseTweets{

        val response=twitterApi.getTweets()
        println("response ${response.responseStatus}")

       return response
    }
}