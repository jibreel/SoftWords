package jibreelpowell.com.softwords.utils

import android.app.Activity
import jibreelpowell.com.softwords.activityutils.ActivityComponent
import jibreelpowell.com.softwords.app.App
import jibreelpowell.com.softwords.di.AppComponent

val Activity.app: App
    get() = application as App