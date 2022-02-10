package jibreelpowell.com.softwords.utils

import io.reactivex.rxjava3.core.Scheduler
interface SchedulerProvider {
    abstract val computation : Scheduler
    abstract val io: Scheduler
    abstract val ui: Scheduler
}