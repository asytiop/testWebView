package io.symbyoz.testwebview.application

import android.app.Application
import android.content.Context
import android.util.Log
import com.parse.Parse
import com.parse.ParseInstallation
import com.parse.ParseObject
import io.symbyoz.testwebview.core.PARSE
import io.symbyoz.testwebview.model.UserData
import io.symbyoz.testwebview.model.UserMedia

class TestApp: Application()
{

    companion object
    {
        const val TAG = "TestApp"
        var ctx: Context? = null
    }


    override fun onCreate()
    {
        super.onCreate()
        ctx = applicationContext

        initSDK()
    }


    private fun initSDK()
    {
        Log.d(TAG, "initSDK()")


        ParseObject.registerSubclass(UserData::class.java)
        ParseObject.registerSubclass(UserMedia::class.java)

        // PARSE
        Parse.initialize(
            Parse.Configuration.Builder(this)
                .applicationId(PARSE.APPLICATION_ID) // if desired
                .clientKey(PARSE.CLIENT_KEY)
                .server(PARSE.SERVER)
                .enableLocalDataStore()
                .build()
        )

        ParseInstallation.getCurrentInstallation().saveInBackground();


        //AppCore.init(ctx!!)
    }


}