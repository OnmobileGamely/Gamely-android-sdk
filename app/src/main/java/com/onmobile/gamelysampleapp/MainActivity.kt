package com.onmobile.gamelysampleapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.onmobile.gamelysdk.sdkutil.ResultBundle
import com.onmobile.gamelysdk.sdkutil.enums.GamelyEvent
import com.onmobile.gamelysdk.sdkutil.enums.RequestOption
import com.onmobile.gamelysdk.sdkutil.enums.ResultStatus
import com.onmobile.gamelysdk.sdkutil.listeners.IEventListener
import com.onmobile.gamelysdk.sdkutil.listeners.IResponseListener
import com.onmobile.gamelysdk.sdkutil.listeners.ITokenExpiredListener
import com.onmobile.gamelysdk.view.activities.GamelySdkHomeActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //callback listener for response
        val iResponseListener = object : IResponseListener {
            override fun onResponse(
                resultStatus: ResultStatus,
                resultBundle: ResultBundle?,
                activity: AppCompatActivity?,
                tokenExpiredListener: ITokenExpiredListener?
            ) {
                //Incase of Win/Loose, response will have result bundle and activity
                //Incase of RewardInfo, response will have result bundle

                //resultBundle?.bundle?.getString("Text") // Result text
                //resultBundle?.bundle?.getLong("NextPlayTimeStamp") // Next Rule Start timestamp in millisecond
                //resultBundle?.bundle?.getLong("NextPlayRemainingTimeStamp")// Next Rule Remaining timestamp in millisecond
                //resultBundle?.bundle?.getString("NextPlayRuleName") // Next Rule Name

                //resultBundle?.bundle?.getInt("SpinsLeftCount")// Number of Spins left in case of SpinWheel
                //resultBundle?.bundle?.getInt("PointsWon")//Points won
                //resultBundle?.bundle?.getInt("CoinsWon")//Coins won
                ///resultBundle?.bundle?.getStringArray("VouchersWon")// voucher codes won

                //val bottomSheetDialog = BottomSheetDialog(activity) // use this activity to open bottomsheet
                //if (activity != null) (activity as GamelySdkHomeActivity).triviaCompleted()// use this to close sdk


                when (resultStatus) {
                    ResultStatus.UNAUTHORISED -> {}
                    ResultStatus.AUTH -> {}
                    ResultStatus.UNKNOWNUSER -> {}
                    ResultStatus.NOTINITIALIZED -> {}
                    ResultStatus.ERRORHANDLED -> {}
                    ResultStatus.NOTEMPLATE -> {}
                    ResultStatus.FAILURE -> {}
                    ResultStatus.WON -> {
                        //resultBundle?.bundle?.getInt("PointsWon")
                        //resultBundle?.bundle?.getInt("CoinsWon")
                        //resultBundle?.bundle?.getStringArray("VouchersWon")
                        //resultBundle?.bundle?.getString("Text")
                        //resultBundle?.bundle?.getLong("NextPlayTimeStamp")
                        //resultBundle?.bundle?.getLong("NextPlayRemainingTimeStamp")
                        //resultBundle?.bundle?.getString("NextPlayRuleName")
                        //if (activity != null) (activity as GamelySdkHomeActivity).triviaCompleted()
                    }
                    ResultStatus.LOOSE -> {
                        //resultBundle?.bundle?.getString("Text")
                        //resultBundle?.bundle?.getLong("NextPlayTimeStamp")
                        //resultBundle?.bundle?.getLong("NextPlayRemainingTimeStamp")
                        //resultBundle?.bundle?.getString("NextPlayRuleName")
                        //if (activity != null) (activity as GamelySdkHomeActivity).triviaCompleted()
                    }
                    ResultStatus.REWARD_INFO -> {
                        //resultBundle?.bundle?.getInt("SpinsLeftCount")
                        //resultBundle?.bundle?.getLong("NextPlayTimeStamp")
                        //resultBundle?.bundle?.getLong("NextPlayRemainingTimeStamp")
                        //resultBundle?.bundle?.getString("NextPlayRuleName")
                    }
                    ResultStatus.TOKEN_EXPIRED -> {
                        //Handle if token has expired
                        //generate new token and pass new token
                        //tokenExpiredListener?.retryRequest("%TOKEN_VALUE%")//To continue request pass new token
                        //tokenExpiredListener?.cancelRequest()// This will cancel request
                    }
                    ResultStatus.PLAY_COMPLETED -> {
                        //resultBundle?.bundle?.getLong("NextPlayTimeStamp")
                        //resultBundle?.bundle?.getLong("NextPlayRemainingTimeStamp")
                        //resultBundle?.bundle?.getString("NextPlayRuleName")
                        //if (activity != null) (activity as GamelySdkHomeActivity).triviaCompleted()
                    }
                }
            }
        }

        //callback listener for media play/pause events
        val iEventListener = object : IEventListener {
            override fun onReceiveEvent(gamelyEvent: GamelyEvent) {}

        }

        findViewById<AppCompatButton>(R.id.get_reward_info).setOnClickListener {
            val gamelySDKClient = (application as GamelySampleApplication).gamelySDKClient()
            gamelySDKClient?.getReward(
                RequestOption.REWARD_NAME_INFO, "rule name value", iResponseListener, iEventListener
            )
        }

        findViewById<AppCompatButton>(R.id.get_reward_name).setOnClickListener {
            val gamelySDKClient = (application as GamelySampleApplication).gamelySDKClient()
            gamelySDKClient?.getReward(RequestOption.REWARD_NAME, "rule name value", iResponseListener, iEventListener)
        }

        findViewById<AppCompatButton>(R.id.get_leaderboard).setOnClickListener {
            val gamelySDKClient = (application as GamelySampleApplication).gamelySDKClient()
            gamelySDKClient?.getLeaderBoard("rule name value")
        }
    }

}