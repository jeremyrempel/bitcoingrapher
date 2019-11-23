package com.jeremyrempel.android.chartview.ui

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jeremyrempel.android.chartview.R
import com.jeremyrempel.android.chartview.presentation.ChartViewState
import com.jeremyrempel.android.chartview.presentation.Lce
import io.mockk.mockk
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(application = FakeApplication::class)
class ChartDetailFragmentTest {

    @Test
    fun `test view loading state`() {

        val frag = ChartDetailFragment(mockk(relaxed = true))
        val scenario = launchFragmentInContainer<ChartDetailFragment>(
            Bundle.EMPTY,
            R.style.Theme_AppCompat_Light_DarkActionBar,
            FakeFragFactory(frag)
        )

        scenario.onFragment {
            it.render(Lce.Loading())
        }

        onView(withId(R.id.view_loading)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        onView(withId(R.id.view_error)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
        onView(withId(R.id.view_content)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
    }

    @Test
    fun `test view error state`() {

        val errorMsg = "failwhale"
        val frag = ChartDetailFragment(mockk(relaxed = true))
        val scenario = launchFragmentInContainer<ChartDetailFragment>(
            Bundle.EMPTY,
            R.style.Theme_AppCompat_Light_DarkActionBar,
            FakeFragFactory(frag)
        )

        scenario.onFragment {
            it.render(Lce.Error(errorMsg))
        }

        onView(withId(R.id.view_loading)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
        onView(withId(R.id.view_content)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))

        onView(withId(R.id.view_error)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        onView(withId(R.id.txt_error_msg)).check(matches(withText(errorMsg)))
    }

    @Test
    fun `test view content state`() {

        val frag = ChartDetailFragment(mockk(relaxed = true))
        val scenario = launchFragmentInContainer<ChartDetailFragment>(
            Bundle.EMPTY,
            R.style.Theme_AppCompat_Light_DarkActionBar,
            FakeFragFactory(frag)
        )

        scenario.onFragment {
            it.render(
                Lce.Content(
                    ChartViewState(
                        "chart title",
                        listOf()
                    )
                )
            )
        }

        onView(withId(R.id.view_loading)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
        onView(withId(R.id.view_error)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
        onView(withId(R.id.view_content)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
    }
}

class FakeFragFactory(private val frag: Fragment) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String) = frag
}

class FakeApplication : Application()
