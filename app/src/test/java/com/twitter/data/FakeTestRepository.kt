package com.twitter.data

import com.twitter.tweets.network.TwitterApi
import com.twitter.tweets.model.Resource
import com.twitter.tweets.model.ResponseTweet
import com.twitter.tweets.model.ResponseTweets
import com.twitter.tweets.repo.TwitterRepository
import junit.framework.Assert.assertNotNull
import junit.framework.Assert.assertNull
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.junit.Before
import org.junit.Test
import javax.inject.Inject

class FakeTestRepository : TwitterApi {
    var tweetList = arrayListOf<ResponseTweet>()

    @Inject
    lateinit var twitterApi: TwitterApi


    lateinit var responseTweet: ResponseTweet
    private var shouldReturnError = false
    lateinit var twitterRepository: FakeDataSource

    override suspend fun getTweets(): ResponseTweets {
        return ResponseTweets(true, tweetList)
    }

    @Before
    fun initialise() {
        val tweet1 = ResponseTweet("Tweet1", "@Drump", 20, 23, "", "hello test")
        val tweet2 = ResponseTweet("Tweet2", "@Trump", 21, 24, "", "hello test")
        val tweet3 = ResponseTweet("Tweet3", "@Modi", 22, 25, "", "hello test")
        tweetList.add(tweet1)
        tweetList.add(tweet2)
        tweetList.add(tweet3)
        twitterRepository = FakeDataSource(tweetList)
    }

    fun getResponseMessage(errorState: Boolean): Resource<ArrayList<ResponseTweet>> {
        shouldReturnError = errorState
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
        //runBlockingTest :skip
        runBlockingTest {
             //when
            val responseTweet = twitterRepository.getTweets()
            //then
            assertNotNull("Response is Not Null", responseTweet)
        }
    }

}