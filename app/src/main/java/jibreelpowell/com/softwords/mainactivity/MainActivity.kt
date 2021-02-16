package jibreelpowell.com.softwords.mainactivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.google.android.material.bottomnavigation.BottomNavigationView
import jibreelpowell.com.softwords.R
import jibreelpowell.com.softwords.databinding.ActivityMainBinding
import jibreelpowell.com.softwords.generate.GenerateComponent
import jibreelpowell.com.softwords.generate.GenerateFragment
import jibreelpowell.com.softwords.history.HistoryFragment
import jibreelpowell.com.softwords.utils.app

class MainActivity : AppCompatActivity() {

    lateinit var generateComponent: GenerateComponent

    val itemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_generate -> {
                    supportFragmentManager.commit {
                        replace<GenerateFragment>(R.id.main_fragment_container)
                        setReorderingAllowed(true)
                        addToBackStack(null)
                    }
                    true
                }
                R.id.navigation_history -> {
                    supportFragmentManager.commit {
                        replace<HistoryFragment>(R.id.main_fragment_container)
                        setReorderingAllowed(true)
                        addToBackStack(null)
                    }

                    true
                }
                else -> false
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        generateComponent = app.component.generateComponent().create()
        generateComponent.inject(this)
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )
        binding.navView.setOnNavigationItemSelectedListener(itemSelectedListener)
    }
}

