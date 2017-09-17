package com.github.bassaer.qiitaclient

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import com.github.bassaer.qiitaclient.model.ArticleClient
import io.reactivex.Observable
import org.hamcrest.Matcher
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*

/**
 * Created by nakayama on 2017/09/16.
 */
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @JvmField
    @Rule
    val activityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun all_views_should_be_shown_except_progress_bar() {
        onView(withId(R.id.list_view)).check(matches(isDisplayed()))
        onView(withId(R.id.query_edit_text)).check(matches(isDisplayed()))
        onView(withId(R.id.search_button)).check(matches(isDisplayed()))
        onView(withId(R.id.progress_bar)).check(matches(isNotDisplayed()))
    }

    @Test
    fun send_query_when_search_button_was_tapped() {
        val articleClient = mock(ArticleClient::class.java).apply {
            `when`(search("user:bassaer")).thenReturn(Observable.just(listOf()))
        }
        activityTestRule.activity.articleClient = articleClient

        onView(withId(R.id.query_edit_text)).perform(typeText("user:bassaer"))
        onView(withId(R.id.search_button)).perform(click())
        verify(articleClient).search("user:bassaer")
    }

    fun isNotDisplayed(): Matcher<View> = not(isDisplayed())
}