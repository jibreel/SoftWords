package jibreelpowell.com.softwords.utils

import io.reactivex.rxjava3.schedulers.TestScheduler

class TestSchedulerProvider(testScheduler: TestScheduler): SchedulerProvider {
    override val computation = testScheduler
    override val io = testScheduler
    override val ui = testScheduler
}