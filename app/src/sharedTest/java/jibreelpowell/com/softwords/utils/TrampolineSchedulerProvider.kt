package jibreelpowell.com.softwords.utils

import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

class TrampolineSchedulerProvider: SchedulerProvider {
    override val computation: Scheduler
        get() = Schedulers.trampoline()
    override val io: Scheduler
        get() = Schedulers.trampoline()
    override val ui: Scheduler
        get() = Schedulers.trampoline()
}