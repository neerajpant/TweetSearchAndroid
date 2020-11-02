package com.twitter.data

import com.twitter.tweets.network.TwitterApi
import com.twitter.tweets.model.Resource
import com.twitter.tweets.model.ResponseTweet
import com.twitter.tweets.model.ResponseTweets
import com.twitter.tweets.repo.TwitterRepository
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import javax.inject.Inject

class FakeTestRepository : TwitterApi {
    var tweetList = arrayListOf<ResponseTweet>()

    @Inject
    lateinit var twitterApi: TwitterApi

//    @get:Rule
//    var hiltRule = HiltAndroidRule(this)
    lateinit var responseTweet: ResponseTweet
    private var shouldReturnError = false
    lateinit var twitterRepository: TwitterRepository

    override suspend fun getTweets(): ResponseTweets {
        return ResponseTweets(true, tweetList)
    }

    @Before
    fun initialise() {
        twitterRepository = TwitterRepository(twitterApi)
    }

    fun getResponseMessage( errorState:Boolean): Resource<ArrayList<ResponseTweet>> {
      shouldReturnError=  errorState
        if (shouldReturnError) {
            return Resource.error(data = arrayListOf(), message = "Error Occured")
        }
        tweetList.let {
            return Resource.success(data = it)
        }
        return Resource.error(data = arrayListOf(), message = "Could not Find Tweets")
    }

    fun addTweets(vararg tweets: ResponseTweet) {
        for (tweet in tweets) {
            tweetList.add(tweet)
        }
    }

    @Test
    fun testCoroutine() {
        runBlockingTest {
            twitterRepository.getTweets()
        }
    }

}