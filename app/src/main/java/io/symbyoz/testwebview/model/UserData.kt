package io.symbyoz.testwebview.model

import com.parse.ParseClassName
import com.parse.ParseObject
import com.parse.ktx.delegates.intAttribute
import com.parse.ktx.delegates.stringAttribute

@ParseClassName("UserData")
class UserData : ParseObject() {
    var id: String by stringAttribute()
    var username: String by stringAttribute()
    var media_count: Int by intAttribute()
}