package com.twitter.tweets.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseTweets(@SerializedName("success") val responseStatus:Boolean,
                          @SerializedName("data")val data: List<ResponseTweet>) :Parcelable
{
}