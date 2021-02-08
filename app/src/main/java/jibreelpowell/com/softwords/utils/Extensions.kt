package jibreelpowell.com.softwords.utils

import android.app.Activity
import jibreelpowell.com.softwords.app.App

val Activity.app: App
    get() = application as App