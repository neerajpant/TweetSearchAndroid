package com.twitter.imagesearchapp.ui.tweets

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.twitter.data.FakeTestRepository
import com.twitter.data.MainCoroutineRule
import com.twitter.tweets.fragment.TweetsViewModel
import com.twitter.tweets.network.TwitterApi
import com.twitter.tweets.model.Resource
import com.twitter.tweets.model.ResponseTweet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers.not
import org.hamcrest.CoreMatchers.nullValue
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

class TweetsViewModelTest {
    //create a fake repository
    private lateinit var fakeRepository: FakeTestRepository

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    // Subject under test
    private lateinit var tasksViewModel: TweetsViewModel

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Inject
    lateinit var twitterApi: TwitterApi
    val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        //make a launcher type for the coroutine like viewmodel
        mainCoroutineRule.dispatcher.runBlockingTest {

        }
        fakeRepository = FakeTestRepository()
        //hiltRule.inject()
        val tweet1 = ResponseTweet("Tweet1", "@Drump", 20, 23, "", "hello test")
        val twet2 = ResponseTweet("Tweet2", "@Trump", 21, 24, "", "hello test")
        val tweet3 = ResponseTweet("Tweet3", "@Modi", 22, 25, "", "hello test")

        fakeRepository.addTweets(tweet1, twet2, tweet3)
        // tasksViewModel = TweetsViewModel(TwitterRepository(TwitterApi()))
    }

    @Test
    fun checkTweetsSuccess() {
        // assertEquals(fakeRepository.getResponseMessage(false),fakeRepository.getResponseMessage(false))
        //assertEquals(fakeRepository.getResponseMessage(false),(not(nullValue())))
        assertTrue(fakeRepository.getResponseMessage(false) != (not(nullValue())))
    }

    @Test
    fun checkTweetsFail() {
        assertEquals(
            fakeRepository.getResponseMessage(true), Resource.error(
                data = arrayListOf<ResponseTweet>(),
                "Error Occured"
            )
        )
        // assertEquals(fakeRepository.getResponseMessage(),(not(nullValue())))
    }



}