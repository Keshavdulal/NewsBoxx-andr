package com.example.mapd726_group3_newsbox

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.PropertyName

//data class Article(var title: String,  var body: String, var source: String)
@IgnoreExtraProperties
data class Article(

    @PropertyName("content_html")
    @get:PropertyName("content_html")
    val body: String? = null,

    @PropertyName("date_published")
    @get:PropertyName("date_published")
    val date: String? = null,

    @PropertyName("guid")
    val guid: String? = null,

    @PropertyName("summary")
    val summary: String? = null,

    @PropertyName("title")
    val title: String? = null,

    @PropertyName("url")
    val url: String? = null,

    @PropertyName("source")
    var source:String?=null,

    @get:Exclude
    var image:Int=0,

    var isBookmarked:Boolean = false
/*var image: Int, var title: String,  var body: String, var source: String*/)
