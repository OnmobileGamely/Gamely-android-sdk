![Onmobile: Logo](http://t0.gstatic.com/images?q=tbn:ANd9GcQ7a6C5baa2f_3KA2zVpouH29tMGgRfcCn1PGuubySgbFbKuMxg)


## SDK Quick Guide
Learn how to quickly integrate and start using the Gamely Android SDK

### SDK Specs and Prerequisites
a) prerequisite

    Support Android 21 API level and above
    SDK file size between 2-3 MB (approximately)

b) Permissions Required

    The following permissions are required by the RBT SDK:
    <uses-permission android:name="android.permission.INTERNET" />

## SDK Integration
The Gamely SDK library can be integrated into any Android project by following the steps mentioned in the sections below. SDK handles the artifacts remotely and resolves the dependencies at the build level in the integrating environment. This is JitPack-based private artifacts library.



### Add Authentication Token
The authentication token identifies the validity of dependent packages in the integrating environment. 
Add the following line in the gradle.properties file of your Android application to add a token:



 ```groovy
authToken= token_value // <onmobile team share you offline>
```

### Authenticate Maven Build Signatures
Add it to your root build.gradle at the end of repositories: authToken added in the gradle.properties

```groovy
allprojects {
    repositories {
        google()
        maven {
            url https://jitpack.io
            credentials { username authToken }
        }
     }
  }
```
Once you've updated your build.gradle file, make sure you have specified maven() and google() as a repositories in your project build.gradle and then sync your project in File -> Sync Project with Gradle Files.

The Android build environment will now validate the authentication token and download the dependencies.

### Installation 
We publish the SDK to mavenCentral as an AAR file. Just declare it as a dependency in your build.gradle file
```groovy
implementation 'org.bitbucket.onmobile-rbtsdk.gamelysdkandroid:gamelysdk:$version$'
```
Onmobile team share you the latest path/version details of the SDK


### Configurations Dependencies
Gamely SDK module has dependencies with the following third-party libraries.

```groovy
dependencies {
    implementation 'androidx.core:core-ktx:1.7.0'
    implementation 'androidx.appcompat:appcompat:1.4.2'
    implementation 'com.google.android.material:material:1.6.1'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
    implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.0'
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.3'
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.3'
    implementation 'com.github.bumptech.glide:glide:4.11.0'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.11.0'
}
```

**Note:**
There is no need to add the above dependencies.
In case the project also uses some or any of the above dependencies and libraries then exclude those by adding below lines along with 

**implementation 'org.bitbucket.onmobile-rbtsdk:latest_version'**
**{exclude group: ‘com.x.y’ , module: ‘module X’}**


*implementation 'org.bitbucket.onmobile-rbtsdk:onmo_dialer:latest_version' 
{exclude group: 'androidx.appcompat', module: ' appcompat'}*

This step is…  | If your app is…
-------------- | -------------
Required       | using a different version of AppCompat library.SDK will use the same version of library in the app.
Not required	|  not using AppCompat library.
Not required	| using the same version of AppCompat library. Android OS will keep only one version of library for both app and SDK.


## Initialize SDK
You can do SDK initialization in the application/activity class
```groovy
 val gamelySDKClient = GamelySdkClient.Builder(this)
            .setUserId("token value") //Mandatory
            .setApiKey("api key value")//Mandatory
            .setLocale(GamelyLocale.ENGLISH)//Optional
            .setUpEnvironment(Environment.STAGING)//Optional, default: Environment.PRODUCTION
            .setInitListener(iSdkInitListener)//Optional
            .build()

          

    // callback listener for sdk initialization
    val iSdkInitListener = object : ISdkInitListener {
        override fun onResponse(resultStatus: ResultStatus) {}
    }

```

### 1.0 Get Reward
Use the following lines to get a reward

```kotlin
 gamelySDKClient?.getReward(RequestOption.REWARD_NAME_INFO, "rule name value", iResponseListener, iEventListener) // For reward info by name
 gamelySDKClient?.getReward(RequestOption.REWARD_NAME, "rule name value", iResponseListener, iEventListener) // For reward by name

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
            //resultBundle?.bundle?.getString("NextPlayRuleName") // Next Rule Name, null value means there is no future rule available wrt ruleName passed

            //resultBundle?.bundle?.getInt("SpinsLeftCount")// Number of Spins left in case of SpinWheel
            //resultBundle?.bundle?.getInt("PointsWon")//Points won
            //resultBundle?.bundle?.getInt("CoinsWon")//Coins won
            //resultBundle?.bundle?.getStringArray("VouchersWon")// voucher codes won

            //val bottomSheetDialog = BottomSheetDialog(activity) // use this activity to open bottomsheet
            //if (activity != null) (activity as GamelySdkHomeActivity).completed()// use this to close sdk


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
                //resultBundle?.bundle?.getString("NextPlayRuleName") // Next Rule Name, null value means there is no future rule available wrt ruleName passed
                //if (activity != null) (activity as GamelySdkHomeActivity).completed()
            }
            ResultStatus.LOOSE -> {
                //resultBundle?.bundle?.getString("Text")
                //resultBundle?.bundle?.getLong("NextPlayTimeStamp")
                //resultBundle?.bundle?.getLong("NextPlayRemainingTimeStamp")
                //resultBundle?.bundle?.getString("NextPlayRuleName") // Next Rule Name, null value means there is no future rule available wrt ruleName passed
                //if (activity != null) (activity as GamelySdkHomeActivity).completed()
            }
            ResultStatus.REWARD_INFO -> {
                //resultBundle?.bundle?.getInt("SpinsLeftCount")
                //resultBundle?.bundle?.getLong("NextPlayTimeStamp")
                //resultBundle?.bundle?.getLong("NextPlayRemainingTimeStamp")
                //resultBundle?.bundle?.getString("NextPlayRuleName") // Next Rule Name, null value means there is no future rule available wrt ruleName passed
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
                //resultBundle?.bundle?.getString("NextPlayRuleName") // Next Rule Name, null value means there is no future rule available wrt ruleName passed
                //if (activity != null) (activity as GamelySdkHomeActivity).completed()
            }
        }
    }

//callback listener for media play/pause events
val iEventListener = object : IEventListener {
            override fun onReceiveEvent(gamelyEvent: GamelyEvent) {}

        }
    
```

### 1.1 Get LeaderBoard
Use the following lines to get leaderboard

```kotlin
 gamelySDKClient?.getLeaderBoard("rule name value")  
```

## Enums
Enums used in initialization and listeners

```kotlin
enum class GamelyLocale {
    ENGLISH,
    BANGLA;
}

enum class ResultStatus {
    UNAUTHORISED, //Invalid Package
    AUTH, //Invalid API Key
    UNKNOWNUSER,
    NOTINITIALIZED,
    ERRORHANDLED,//Error is handled by sdk
    NOTEMPLATE,// Error needs to be handled by client app
    WON,
    LOOSE,
    FAILURE,//Any Failure/NextPlayTime Failure
    TOKEN_EXPIRED,//Token has expired
    PLAY_COMPLETED,//Reward with LeaderBoard Played & Reward with Scheduling Played(When not won/loose)
    REWARD_INFO;//Information related to Reward like NextPlayTime, SpinsLeftCount
}

enum class GamelyEvent {
    MEDIAPLAY,
    MEDIAPAUSE;
}

enum class RequestOption {
    REWARD_NAME,
    REWARD_NAME_INFO
}
```

See the [`Gamely-android-sdk` project](https://github.com/OnmobileGamely/Gamely-android-sdk/) for more details.


#### Copyright

##### ©2022 OnMobile Global Limited All Rights Reserved.
