package io.atomic.sdk

import android.app.Application
import com.atomic.actioncards.sdk.AACSDK
import dagger.hilt.android.HiltAndroidApp


/** Entry point to our application. The SDK needs to do some init code [onCreate] */
@HiltAndroidApp
class BoilerPlateApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        AACSDK.init(this)
    }

}