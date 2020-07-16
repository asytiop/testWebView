package io.symbyoz.testwebview.model

import com.parse.ParseClassName
import com.parse.ParseObject
import com.parse.ktx.delegates.stringAttribute

@ParseClassName("UserMedia")
class UserMedia : ParseObject(){
    val media_id: String by stringAttribute()
    val username: String by stringAttribute()
    val timestamp: String by stringAttribute()
    val caption: String by stringAttribute()
    val media_url: String by stringAttribute()
}