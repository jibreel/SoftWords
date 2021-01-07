package jibreelpowell.com.softwords.utils

import android.app.Activity
import jibreelpowell.com.softwords.activityutils.ActivityComponent
import jibreelpowell.com.softwords.app.App

fun Activity.createActivityComponent(): ActivityComponent {
    return (application as App).component.activityComponent().create(this)
}