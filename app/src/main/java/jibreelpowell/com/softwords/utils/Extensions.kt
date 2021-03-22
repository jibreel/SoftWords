package jibreelpowell.com.softwords.utils

import android.app.Activity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import jibreelpowell.com.softwords.app.App

val Activity.app: App
    get() = application as App

fun <T> Flowable<T>.scheduleFlowableInBackground(schedulerProvider: SchedulerProvider): Flowable<T> {
    return this.subscribeOn(schedulerProvider.io)
        .observeOn(schedulerProvider.ui)
}

fun <T> Maybe<T>.scheduleMaybeInBackgroung(schedulerProvider: SchedulerProvider): Maybe<T> {
    return this.subscribeOn(schedulerProvider.io)
        .observeOn(schedulerProvider.ui)
}

fun Completable.scheduleCompletableInBackground(schedulerProvider: SchedulerProvider): Completable {
    return this.subscribeOn(schedulerProvider.io)
        .observeOn(schedulerProvider.ui)
}

fun <T> Single<T>.scheduleSingleInBackground(schedulerProvider: SchedulerProvider): Single<T> {
    return this.subscribeOn(schedulerProvider.io)
        .observeOn(schedulerProvider.ui)
}

fun Char.isVowel(): Boolean {
    return "aeiou".contains(this, ignoreCase = true)
}

