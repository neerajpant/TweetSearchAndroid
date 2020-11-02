package com.twitter.tweets.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseTweet(
    @SerializedName("name")
     val name: String,
    @SerializedName("handle")
     val handle: String,
    @SerializedName("retweetCount")
     val retweetCount: Int,
    @SerializedName("favoriteCount")
     val favoriteCount: Int,
    @SerializedName("profileImageUrl")
     val profileImageUrl: String,
    @SerializedName("text")
     val text: String

) : Parcelable {


}