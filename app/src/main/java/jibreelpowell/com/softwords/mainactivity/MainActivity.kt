package jibreelpowell.com.softwords.mainactivity

import androidx.databinding.BaseObservable
import androidx.databinding.DataBindingUtil
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import jibreelpowell.com.softwords.R
import jibreelpowell.com.softwords.databinding.ActivityMainBinding
import jibreelpowell.com.softwords.generator.Pattern

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityMainBinding = DataBindingUtil.setContentView(this,
            R.layout.activity_main
        )
        binding.presenter = MainActivityPresenter()
    }
}

class MainActivityPresenter: BaseObservable() {

    lateinit var sentence: String

    init {
        generateNewSentence()
    }

    fun generateNewSentence() {
        sentence = Pattern.random().sentence()
        notifyChange()
    }

}

