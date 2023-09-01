package com.onmobile.gamelysampleapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import com.google.firebase.dynamiclinks.PendingDynamicLinkData
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.ktx.Firebase
import com.onmobile.gamelysdk.sdkutil.GamizeBadge
import com.onmobile.gamelysdk.sdkutil.GamizeDailyStreakReward
import com.onmobile.gamelysdk.sdkutil.ResultBundle
import com.onmobile.gamelysdk.sdkutil.enums.GamelyEvent
import com.onmobile.gamelysdk.sdkutil.enums.RequestOption
import com.onmobile.gamelysdk.sdkutil.enums.ResultStatus
import com.onmobile.gamelysdk.sdkutil.listeners.IEventListener
import com.onmobile.gamelysdk.sdkutil.listeners.IResponseListener
import com.onmobile.gamelysdk.sdkutil.listeners.ITokenExpiredListener
import com.onmobile.gamelysdk.view.activities.GamelySdkHomeActivity


class MainActivity : AppCompatActivity() {

    //callback listener for response
    private val iResponseListener = object : IResponseListener {
        override fun onResponse(
            resultStatus: ResultStatus,
            resultBundle: ResultBundle?,
            activity: AppCompatActivity?,
            tokenExpiredListener: ITokenExpiredListener?
        ) {
            when (resultStatus) {
                ResultStatus.UNAUTHORISED -> {}
                ResultStatus.AUTH -> {}
                ResultStatus.UNKNOWNUSER -> {}
                ResultStatus.NOTINITIALIZED -> {}
                ResultStatus.ERRORHANDLED -> {}
                ResultStatus.NOTEMPLATE -> {}
                ResultStatus.FAILURE -> {}
                ResultStatus.WON -> {
                    resultBundle?.bundle?.getInt("PointsWon")
                    resultBundle?.bundle?.getInt("CoinsWon")
                    resultBundle?.bundle?.getStringArray("VouchersWon")
                    resultBundle?.bundle?.getString("Text")
                    resultBundle?.bundle?.getLong("NextPlayTimeStamp")
                    resultBundle?.bundle?.getLong("NextPlayRemainingTimeStamp")
                    resultBundle?.bundle?.getString("NextPlayRuleName") // Next Rule Name, null value means there is no future rule available wrt ruleName passed

                    // GamizeBadges callback starts
                    val gamizeBadges = resultBundle?.bundle?.getParcelableArray("GamizeBadges")
                    gamizeBadges?.let {
                        for (item in it) {
                            val gamizeBadge = item as GamizeBadge
                        }
                    }
                    // GamizeBadges callback ends

                    //GamizeDailyStreakRewards callback starts
                    val dailyStreakRewards =
                        resultBundle?.bundle?.getParcelableArray("GamizeDailyStreakRewards")
                    dailyStreakRewards?.let {
                        for (item in it) {
                            val dailyStreakReward = item as GamizeDailyStreakReward
                        }
                    }
                    //GamizeDailyStreakRewards callback ends

                    if (activity != null) (activity as GamelySdkHomeActivity).completed()
                }

                ResultStatus.LOOSE -> {
                    resultBundle?.bundle?.getString("Text")
                    resultBundle?.bundle?.getLong("NextPlayTimeStamp")
                    resultBundle?.bundle?.getLong("NextPlayRemainingTimeStamp")
                    resultBundle?.bundle?.getString("NextPlayRuleName") // Next Rule Name, null value means there is no future rule available wrt ruleName passed

                    // GamizeBadges callback starts
                    val gamizeBadges = resultBundle?.bundle?.getParcelableArray("GamizeBadges")
                    gamizeBadges?.let {
                        for (item in it) {
                            val gamizeBadge = item as GamizeBadge
                        }
                    }
                    // GamizeBadges callback ends

                    //GamizeDailyStreakRewards callback starts
                    val dailyStreakRewards =
                        resultBundle?.bundle?.getParcelableArray("GamizeDailyStreakRewards")
                    dailyStreakRewards?.let {
                        for (item in it) {
                            val dailyStreakReward = item as GamizeDailyStreakReward
                        }
                    }
                    //GamizeDailyStreakRewards callback ends

                    if (activity != null) (activity as GamelySdkHomeActivity).completed()
                }

                ResultStatus.REWARD_INFO -> {
                    resultBundle?.bundle?.getInt("SpinsLeftCount")
                    resultBundle?.bundle?.getLong("NextPlayTimeStamp")
                    resultBundle?.bundle?.getLong("NextPlayRemainingTimeStamp")
                    resultBundle?.bundle?.getString("NextPlayRuleName") // Next Rule Name, null value means there is no future rule available wrt ruleName passed
                }

                ResultStatus.TOKEN_EXPIRED -> {
                    //Handle if token has expired
                    //generate new token and pass new token
                    //tokenExpiredListener?.retryRequest("%TOKEN_VALUE%")//To continue request pass new token
                    //tokenExpiredListener?.cancelRequest()// This will cancel request
                }

                ResultStatus.PLAY_COMPLETED -> {
                    resultBundle?.bundle?.getLong("NextPlayTimeStamp")
                    resultBundle?.bundle?.getLong("NextPlayRemainingTimeStamp")
                    resultBundle?.bundle?.getString("NextPlayRuleName") // Next Rule Name, null value means there is no future rule available wrt ruleName passed

                    // GamizeBadges callback starts
                    val gamizeBadges = resultBundle?.bundle?.getParcelableArray("GamizeBadges")
                    gamizeBadges?.let {
                        for (item in it) {
                            val gamizeBadge = item as GamizeBadge
                        }
                    }
                    // GamizeBadges callback ends

                    //GamizeDailyStreakRewards callback starts
                    val dailyStreakRewards =
                        resultBundle?.bundle?.getParcelableArray("GamizeDailyStreakRewards")
                    dailyStreakRewards?.let {
                        for (item in it) {
                            val dailyStreakReward = item as GamizeDailyStreakReward
                        }
                    }
                    //GamizeDailyStreakRewards callback ends

                    if (activity != null) (activity as GamelySdkHomeActivity).completed()
                }

                ResultStatus.REFERRAL_SUCCESS -> {
                    //resultBundle?.bundle?.getString("ReferralText")
                }

                ResultStatus.BADGE_INFO -> {
                    val gamizeBadges = resultBundle?.bundle?.getParcelableArray("GamizeBadges")
                    gamizeBadges?.let {
                        for (item in it) {
                            val gamizeBadge = item as GamizeBadge
                        }
                    }
                }

                ResultStatus.NO_DAILY_STREAK -> {
                    //No Daily Streak Available
                }

                ResultStatus.DAILY_STREAK_REWARDS -> {
                    val dailyStreakRewards =
                        resultBundle?.bundle?.getParcelableArray("GamizeDailyStreakRewards")
                    dailyStreakRewards?.let {
                        for (item in it) {
                            val dailyStreakReward = item as GamizeDailyStreakReward
                        }
                    }
                }
            }
        }
    }

    //callback listener for media play/pause events
    private val iEventListener = object : IEventListener {
        override fun onReceiveEvent(gamelyEvent: GamelyEvent) {}

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gamelySDKClient = (application as GamelySampleApplication).gamelySDKClient()

        findViewById<AppCompatButton>(R.id.get_reward_info).setOnClickListener {
            gamelySDKClient?.getReward(
                RequestOption.REWARD_NAME_INFO, "rule name value", iResponseListener, iEventListener
            )
        }

        findViewById<AppCompatButton>(R.id.get_reward_name).setOnClickListener {
            gamelySDKClient?.getReward(
                RequestOption.REWARD_NAME, "rule name value", iResponseListener, iEventListener
            )
        }

        findViewById<AppCompatButton>(R.id.get_leaderboard).setOnClickListener {
            gamelySDKClient?.getLeaderBoard("rule name value")
        }

        findViewById<AppCompatButton>(R.id.get_daily_streak).setOnClickListener {
            gamelySDKClient?.getReward(
                requestOption = RequestOption.REWARD_DAILY_STREAK,
                rewardName = null,
                iResponseListener = iResponseListener,
                iEventListener = iEventListener,
            )
        }

        findViewById<AppCompatButton>(R.id.get_profile_badges).setOnClickListener {
            gamelySDKClient?.getProfileBadges(
                pageSize = pageSizeValue,
                pageNumber = pageNumberValue,
                iResponseListener
            )
        }

        findViewById<AppCompatButton>(R.id.register_user_btn).setOnClickListener {
            val referralCode =
                findViewById<AppCompatEditText>(R.id.referral_edit).text.toString().trim()
            if (referralCode.isNullOrEmpty()) return@setOnClickListener
            gamelySDKClient?.userRegistration(
                referralCode = referralCode,
                iResponseListener = iResponseListener
            )
        }

        checkFirebaseDynamicLink(intent)//required for referral only

    }

    private fun checkFirebaseDynamicLink(intent: Intent?) {
        Firebase.dynamicLinks
            .getDynamicLink(intent)
            .addOnSuccessListener(this) { pendingDynamicLinkData: PendingDynamicLinkData? ->
                pendingDynamicLinkData?.apply {
                    if (utmParameters.getString("utm_medium").equals("gamize-sdk")) {
                        findViewById<AppCompatEditText>(R.id.referral_edit).setText(
                            utmParameters.getString(
                                "utm_source"
                            )
                        )
                    }
                }
            }
    }
}