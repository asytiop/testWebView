package io.symbyoz.testwebview.webservice

import android.util.Log
import com.parse.ParseException
import com.parse.ParseObject
import com.parse.ParseQuery
import io.symbyoz.testwebview.model.UserData
import io.symbyoz.testwebview.model.UserMedia


class ParseAPI {

    private lateinit var objectId: String

    companion object
    {
        /*
        Send user info
         */
        fun sendUserInfo(userId: String, userName: String, media_count: String)
        {
            val userData = UserData()

            userData.client_id = userId
            userData.username = userName
            userData.media_count = media_count

            Log.d("sendUserInfos()", userData.getString("client_id"))

            userData.saveInBackground { exception: ParseException? ->

                if(exception == null)
                {
                    Log.d("ParseAPI", "sendUserInfos() :: saved successful")
                }
                else
                {

                    Log.d("ParseAPI", "sendUserInfos() :: error (${exception.code}) = ${exception.localizedMessage}")
                }
            }
        }

        /*
        Send UserMedia to Parse
         */
        fun sendUserMedia(mediaId: String, userName: String, timestamp: String, caption: String, mediaURL: String)
        {
            val userMedia: ParseObject = UserMedia()

            userMedia.put("media_id", mediaId)
            userMedia.put("username", userName)
            userMedia.put("timestamp", timestamp)
            userMedia.put("caption", caption)
            userMedia.put("media_url", mediaURL)

            userMedia.saveInBackground()

            if(userMedia.objectId != null)
            {
                Log.d("sendUserMedia",userMedia.objectId)
            } else
            {
                Log.d("sendUserData", "ObjectId empty")
            }
        }
    }
}