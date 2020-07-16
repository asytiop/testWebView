package io.symbyoz.testwebview.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import io.symbyoz.testwebview.R
import io.symbyoz.testwebview.core.INSTAGRAM_API
import io.symbyoz.testwebview.model.UserData
import io.symbyoz.testwebview.webservice.ParseAPI
import io.symbyoz.testwebview.webservice.ParseAPI.Companion.sendUserInfo
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    private lateinit var requestUrl: String


    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        testSend()

        requestUrl = "https://www.facebook.com/v7.0/dialog/oauth?"
        "client_id=" + INSTAGRAM_API.CLIENT_ID +
                "&redirect_uri=" + INSTAGRAM_API.REDIRECT_URL +
                "&scope=" + INSTAGRAM_API.SCOPE +
                "&response_type=code"

        webView = findViewById(R.id.webview)
        webView.settings.javaScriptEnabled = true
        webView.loadUrl(INSTAGRAM_API.REQUEST_URL)

        val webViewClient: WebViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                if (url.isEmpty()) {
                    Log.d("Main", "Empty url")
                    return true
                }
                if (url.contains("?code=")) {
                    var accessCode: String = url.substring(url.lastIndexOf("=")+1, url.lastIndexOf("#"))
                    var token: String? = null
                    Log.d("Main", accessCode)
                    exchangeCodeToToken(accessCode)


                } else {
                    Log.d("Main", url)
                    view.loadUrl(url)
                    return true
                }
                return false
            }
        }
        webView.webViewClient = webViewClient

    }

    private fun testSend()
    {
        val userData = UserData()
        userData.put("id","1809809328")
        userData.put("username", "generic")
        userData.put("media_count", 3)
        userData.saveInBackground()
    }

    private fun getMediaData(token: String?, mediaId: String)
    {
        val fields: String = "id,username,timestamp,caption,media_url"
        val url = "https://graph.instagram.com/" + mediaId +
                "?fields=" + fields +
                "&access_token=" + token
        val queue = Volley.newRequestQueue(this)

        val stringRequest =
            StringRequest(Request.Method.GET, url, Response.Listener<String> { response ->

                //Log.d("getMediaData", response)
                val resJSON = JSONObject(response)

                Log.d("getMediaData", resJSON.toString())
                ParseAPI.sendUserMedia(resJSON.optString("media_id"),
                    resJSON.optString("username"),
                    resJSON.optString("timestamp"),
                    resJSON.optString("caption"),
                    resJSON.optString("media_url"))

            }, Response.ErrorListener {error ->  Log.d("getMediaData", error.toString())})

        queue.add(stringRequest)
    }
    private fun getUserData(token: String?, user_id: String, media_count: Int)
    {
        val fields = "id,username,media_count"
        val url = "https://graph.instagram.com/" + user_id + "/media" +
                "?fields=" + fields +
                "&access_token=" + token
        val queue = Volley.newRequestQueue(this)

        val stringRequest =
            StringRequest(Request.Method.GET, url, Response.Listener<String> { response ->

                val resJSON = JSONObject(response)
                val data = resJSON.optJSONArray("data")

                Log.d("getUserData", resJSON.toString())

                for(i in 0 until media_count)
                {
                    val media = data.optJSONObject(i)
                    val mediaId: String = media.optString("id")
                    Log.d("data user", mediaId)
                    getMediaData(token, mediaId)
                }

            }, Response.ErrorListener {error ->  Log.d("getUserName", error.toString())})

        queue.add(stringRequest)
    }

    private fun getUserName(token: String?) {
        val fields = "id,username,media_count"
        val url = "https://graph.instagram.com/me" +
                "?fields=" + fields +
                "&access_token=" + token
        val queue = Volley.newRequestQueue(this)

        val stringRequest =
            StringRequest(Request.Method.GET, url, Response.Listener<String> { response ->

                val resJSON = JSONObject(response)

                sendUserInfo(resJSON.optString("id"),
                        resJSON.optString("username"),
                        resJSON.optInt("media_count"))

                Log.d("getUserName", resJSON.toString())
                getUserData(token, resJSON.optString("id"), resJSON.optInt("media_count"))

            }, Response.ErrorListener {error ->  Log.d("getUserName", error.toString())})

        queue.add(stringRequest)
    }

    private fun exchangeCodeToToken(accessCode: String) {
        lateinit var token: String
        val requestQueue = Volley.newRequestQueue(this)
        val url = "https://api.instagram.com/oauth/access_token"

        val jsonObjRequest: StringRequest = object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->

                    token =  JSONObject(response).optString("access_token")
                    getUserName(token)},
                Response.ErrorListener { error ->
                    Log.d("volley", "Error: ${error.localizedMessage}")
                    error.printStackTrace()
                }) {

            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String>? {
                val params: MutableMap<String, String> =
                    HashMap()
                params["Content-Type"] = "application/x-www-form-urlencoded"
                return params
            }

            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["client_id"] = INSTAGRAM_API.CLIENT_ID.toString()
                params["client_secret"] =
                    INSTAGRAM_API.CLIENT_SECRET
                params["grant_type"] = "authorization_code"
                params["redirect_uri"] =
                    INSTAGRAM_API.REDIRECT_URL
                params["code"] = accessCode
                return params
            }
        }

        requestQueue.add(jsonObjRequest)

    }

}