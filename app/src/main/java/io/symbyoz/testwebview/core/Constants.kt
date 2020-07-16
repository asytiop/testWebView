package io.symbyoz.testwebview.core

class INSTAGRAM_API
{
    companion object
    {
        const val CLIENT_ID = 3221018874586995
        const val CLIENT_SECRET = "1a33beed2a1ecbaad3f141b41687deea"
        const val REDIRECT_URL = "https://www.facebook.com/connect/login_success.html"
        const val SCOPE = "user_profile,user_media"
        const val REQUEST_URL = "https://api.instagram.com/oauth/authorize" +
        "?client_id=3221018874586995" +
        "&redirect_uri=https://www.facebook.com/connect/login_success.html" +
        "&scope=user_profile,user_media" +
        "&response_type=code"
    }
}

class PARSE
{
    companion object
    {
        const val APPLICATION_ID = "TzduTYi1Xwf3G9Mgy9I0KTnIJswegKtO90Jjg8Mz"
        const val CLIENT_KEY = "D2esLluvTRI2ldFqYyEpsej0QCLk1VRQfZ1ERgYF"
        const val SERVER = "https://parseapi.back4app.com/"
    }
}