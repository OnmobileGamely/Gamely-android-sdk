package com.onmobile.gamelysampleapp

import android.app.Application
import com.onmobile.gamelysdk.sdkutil.GamelySdkClient
import com.onmobile.gamelysdk.sdkutil.IGamelySdkClient
import com.onmobile.gamelysdk.sdkutil.enums.GamelyLocale
import com.onmobile.gamelysdk.sdkutil.enums.ResultStatus
import com.onmobile.gamelysdk.sdkutil.listeners.ISdkInitListener

class GamelySampleApplication : Application() {
    private var gamelySDKClient: IGamelySdkClient? = null
    override fun onCreate() {
        super.onCreate()

        gamelySDKClient = GamelySdkClient.Builder(this)
            .setUserId(%TOKEN_VALUE%) //Mandatory
            .setApiKey(%API_KEY%)//Mandatory
            .setLogEnabled(false) //false by default | Optional
            .setLocale(GamelyLocale.ENGLISH)//Optional
            .setInitListener(iSdkInitListener)//Optional
            .build()
    }

    //callback listener for sdk initialization
    private val iSdkInitListener = object : ISdkInitListener {
        override fun onResponse(resultStatus: ResultStatus) {}
    }

    fun gamelySDKClient(): IGamelySdkClient? = gamelySDKClient
}