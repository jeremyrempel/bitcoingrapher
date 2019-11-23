package com.jeremyrempel.android.bitcoingrapher

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jeremyrempel.android.chartview.ui.ChartDetailFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MyApplication).apply {
            supportFragmentManager.fragmentFactory = dagger.fragFactory()
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val frag = supportFragmentManager.fragmentFactory.instantiate(
                classLoader, ChartDetailFragment::class.java.canonicalName!!
            )

            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container_fragment, frag)
                .commitNow()
        }
    }
}
