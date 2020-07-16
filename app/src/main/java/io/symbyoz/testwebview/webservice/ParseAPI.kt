package io.symbyoz.testwebview.webservice

import android.util.Log
import com.parse.ParseObject
import io.symbyoz.testwebview.model.UserData
import io.symbyoz.testwebview.model.UserMedia

class ParseAPI {

    companion object
    {
        fun sendUserInfo(userId: String, userName: String, media_count: Int)
        {
            val userData: ParseObject =
                UserData()

            userData.put("id",userId)
            userData.put("username", userName)
            userData.put("media_count", media_count)
            Log.d("Testtmtc", userData.getString("id"))
            userData.saveInBackground()
        }

        fun sendUserMedia(mediaId: String, userName: String, timestamp: String, caption: String, mediaURL: String)
        {
            val userMedia: ParseObject =
                UserMedia()

            userMedia.put("id", mediaId)
            userMedia.put("username", userName)
            userMedia.put("timestamp", timestamp)
            userMedia.put("caption", caption)
            userMedia.put("media_url", mediaURL)

            userMedia.saveInBackground()
        }
    }
}