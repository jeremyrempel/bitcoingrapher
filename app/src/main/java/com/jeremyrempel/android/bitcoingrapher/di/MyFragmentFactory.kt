package com.jeremyrempel.android.bitcoingrapher.di

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.jeremyrempel.android.chartview.ui.ChartDetailFragment
import javax.inject.Inject
import javax.inject.Provider

class MyFragmentFactory @Inject constructor(
    private val chartDetailFragProvider: Provider<ChartDetailFragment>
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            ChartDetailFragment::class.java.canonicalName -> chartDetailFragProvider.get()
            else -> TODO("Missing dagger provider, add $className to MyFragmentFactory")
        }
    }
}
