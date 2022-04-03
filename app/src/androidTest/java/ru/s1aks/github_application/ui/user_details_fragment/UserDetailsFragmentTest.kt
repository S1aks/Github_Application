package ru.s1aks.github_application.ui.user_details_fragment

import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import ru.s1aks.github_application.R

@RunWith(AndroidJUnit4::class)
class UserDetailsFragmentTest : TestCase() {

    private lateinit var scenario: FragmentScenario<UserDetailsFragment>

    @Before
    public override fun setUp() {
        val fragmentArgs = bundleOf("user_login" to "atmos")
        scenario = launchFragmentInContainer<UserDetailsFragment>(fragmentArgs)
        scenario.moveToState(Lifecycle.State.RESUMED)
    }

    @Test
    fun fragment_testBundle() {
        onView(withId(R.id.recycler_view))
            .perform(
                RecyclerViewActions.scrollTo<UserDetailsAdapter.ViewHolder>(
                    hasDescendant(withText("atmos.github.io"))
                )
            )
    }

    @Test
    fun recyclerView_PerformClickOnItem() {
        onView(withId(R.id.recycler_view))
            .perform(
                RecyclerViewActions.scrollTo<UserDetailsAdapter.ViewHolder>(
                    hasDescendant(withText("conveyor"))
                )
            )
        onView(withId(R.id.recycler_view))
            .perform(
                RecyclerViewActions.actionOnItem<UserDetailsAdapter.ViewHolder>(
                    hasDescendant(withText("conveyor")),
                    tapOnItemWithId(R.id.text_view_item)
                )
            )
    }

    private fun tapOnItemWithId(id: Int) = object : ViewAction {
        override fun getConstraints(): Matcher<View>? {
            return null
        }
        override fun getDescription(): String {
            return "Нажимаем на view с указанным id"
        }
        override fun perform(uiController: UiController, view: View) {
            val v = view.findViewById(id) as View
            v.performClick()
        }
    }

    @After
    public override fun tearDown() {
        scenario.close()
    }
}