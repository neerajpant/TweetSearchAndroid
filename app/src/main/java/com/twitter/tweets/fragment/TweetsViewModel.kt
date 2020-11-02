package com.twitter.tweets.fragment

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.twitter.tweets.model.Resource
import com.twitter.tweets.repo.TwitterRepository
import kotlinx.coroutines.Dispatchers

class TweetsViewModel @ViewModelInject constructor(
    private val repository: TwitterRepository,
   /* @Assisted state: SavedStateHandle*/
) : ViewModel() {

    init {
        println("initGetTweets")
       // getTweets()
    }

     fun getTweets() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
               println("before serverCall")
                val response=repository.getTweets()
            println("ViewModel ${response.responseStatus}")

            emit(Resource.success(data =response))
        } catch (exception: Exception) {
            println("exception ${exception.message}")
            println("cause ${exception.cause.toString()}")
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }


    }



}