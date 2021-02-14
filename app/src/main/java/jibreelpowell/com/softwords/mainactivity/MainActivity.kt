package jibreelpowell.com.softwords.mainactivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomnavigation.BottomNavigationView
import jibreelpowell.com.softwords.R
import jibreelpowell.com.softwords.databinding.ActivityMainBinding
import jibreelpowell.com.softwords.generate.GenerateComponent
import jibreelpowell.com.softwords.utils.app

class MainActivity : AppCompatActivity() {

    lateinit var generateComponent: GenerateComponent

    val bottomNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_generate -> {
                    //TODO: create generate fragment
                    true
                }
                R.id.navigation_history -> {
                    //TODO: create history fragment
                    true
                }
                else -> false
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        app.component.generateComponent().create().inject(this)
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )

    }
}

