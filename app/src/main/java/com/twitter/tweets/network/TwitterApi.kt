package com.twitter.tweets.network


import com.twitter.tweets.model.ResponseTweets
import retrofit2.http.GET


interface TwitterApi {
    companion object {
        const val BASE_URL = "https://6f8a2fec-1605-4dc7-a081-a8521fad389a.mock.pstmn.io/"

        const val CLIENT_ID = "C7eDfRauLM0c3QerpjD56ftM7LD71NcdaPb2xce8phg"
    }


    @GET("tweets")
    suspend fun getTweets(): ResponseTweets
}