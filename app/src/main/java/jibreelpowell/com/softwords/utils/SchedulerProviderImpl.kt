package jibreelpowell.com.softwords.utils

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

class SchedulerProviderImpl(): SchedulerProvider {
    override val computation: Scheduler
        get() = Schedulers.computation()
    override val io: Scheduler
        get() = Schedulers.io()
    override val ui: Scheduler
        get() = AndroidSchedulers.mainThread()
}