package jibreelpowell.com.softwords.mainactivity

import androidx.databinding.DataBindingUtil
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import jibreelpowell.com.softwords.R
import jibreelpowell.com.softwords.databinding.ActivityMainBinding
import jibreelpowell.com.softwords.generate.GenerateComponent
import jibreelpowell.com.softwords.utils.app

class MainActivity : AppCompatActivity() {

    lateinit var generateComponent: GenerateComponent
    
//    val bottomNavigationItemSelectedListener: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        app.component.generateComponent().create().inject(this)
        super.onCreate(savedInstanceState)
        val binding : ActivityMainBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )

    }
}

