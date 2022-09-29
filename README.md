# Gamely Android SDKs

![Onmobile: Logo](http://t0.gstatic.com/images?q=tbn:ANd9GcQ7a6C5baa2f_3KA2zVpouH29tMGgRfcCn1PGuubySgbFbKuMxg)

  


## SDK quick Guide
Learn how to quickly integrate and start using the Gamely Android SDK

### SDK Specs and Prerequisites
a) prerequisite

    Support Android 21 API level and above
    SDK file size between 2-3 MB (approximately)

b) Permissions Required

    The following permissions are required by the RBT SDK:
    <uses-permission android:name="android.permission.INTERNET" />

## Sdk Integration
The Gamely SDK library can be integrated in any Android project by following steps mentioned in the sections below. SDK handles the artifacts remotely and resolves the dependencies at build level in integrating environment. This is JitPack based private artifacts library.



### Add Authentication Token
The authentication token identifies the validity of dependent packages in the integrating environment. 
Add the following line in the gradle.properties file of your Android application to add token:

```groovy
authToken= token_value // <onmobile team share you offline>
```

### Authenticate Maven Build Signatures
Add it in your root build.gradle at the end of repositories: authToken added in the gradle.propeties

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
We publish the SDK to mavenCentral as an AAR file. Just declare it as dependency in your build.gradle file
```groovy
implementation 'org.bitbucket.onmobile-rbtsdk:gamelysdkandroid:$version$' 
'Onmobile team share you the latest path/vertsion details of the SDK'
```
Alternatively, you can download and add the AAR file included in this repo in your Module libs directory and tell gradle to install it like this:
```groovy
implementation(files("libs/<sdkfilename>.aar")) 
'Onmobile team share you the latest path/vertsion details of the SDK'
```

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

Example: To exclude 'androidx.appcompat:appcompat:1.4.2'

*implementation 'org.bitbucket.onmobile-rbtsdk:onmo_dialer:latest_version' 
{exclude group: 'androidx.appcompat', module: ' appcompat'}*

This step is…  | If your app is…
-------------- | -------------
Required       | using a different version of AppCompat library.SDK will use the same version of library in the app.
Not required	|  not using AppCompat library.
Not required	| using same version of AppCompat library. Android OS will keep only one version of library for both app and SDK.


## Initialize SDK
You can do sdk intialisation in application/activity class
```groovy
try {
           val gamelySDKClient = GamelySdkClient.Builder(context)
                .setUserId("user id value") 
                //Mandatory <Onmobile team will share these details>
                .setApiKey("api key value")
                //Mandatory <Onmobile team will share these details>
                .setLogEnabled(false)
                //false by default | Optional
                .build()
        } catch (gamelySdkInitialisationException: GamelySdkInitialisationException) {
            //Catch the exception here
            Toast.makeText(this, gamelySdkInitialisationException.message, Toast.LENGTH_SHORT)
                .show()
            //gamelySdkInitialisationException.printStackTrace()
        }
```

### 1.0 Get Template
Use following lines to get template

```kotlin
//Use only activity context here
gamelySDKClient?.getTemplate(activityContext, TemplateType, object : IResponseListener {
            override fun onSuccess() {
                   //handle the success use case from the host application 
            }
            override fun onFailure(message: String) {
             //handle the failure use case from the host application 
            }
        })
```

TemplateType  | Description
------------- | -------------
Template.SPIN_WHEEL | Spin Wheel
Template.OPINION_POLL | Opinion Poll
Template.SCRATCH_CARD | Scratch Card
Template.SLOT_MACHINE | Slot Machine
Template.GET_REWARD | Get Reward



### 1.2 Get Reward
Use following lines to get reward

```kotlin
//Use only activity context here
 gamelySDKClient?.getReward(this@MainActivity, object : IResponseListener {
                override fun onSuccess() {
                    //handle the success use case from the host application 
                }
                override fun onFailure(message: String) {
                    //handle the failure use case from the host application 
                }
            })
```





See the [`Gamely-android-sdk` project](https://github.com/OnmobileGamely/Gamely-android-sdk/) for more details.



#### Copyright

##### ©2022 OnMobile Global Limited All Rights Reserved.
