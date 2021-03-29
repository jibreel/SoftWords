package jibreelpowell.com.softwords.utils

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler

class UiAwareTestSchedulerProvider(private val schedulerProvider: SchedulerProvider): SchedulerProvider {
    override val computation: Scheduler
        get() = schedulerProvider.computation
    override val io: Scheduler
        get() = schedulerProvider.io
    override val ui: Scheduler
        get() = AndroidSchedulers.mainThread()
}