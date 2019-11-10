package com.example.footballapp.view

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.v7.widget.RecyclerView
import android.widget.AutoCompleteTextView
import com.example.footballapp.R
import com.example.footballapp.utils.EspressoIdling
import org.hamcrest.CoreMatchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest{
    val match = "Man United vs Arsenal"
    val team = "Chelsea"

    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdling.idlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdling.idlingResource)
    }

    @Test
    fun searchMatchTest() {
        //memastikan recyclerView liga tampil
        onView(withId(R.id.rvLeague)).check(matches(isDisplayed()))

        //memastikan aksi klik pada recyclerView liga bekerja
        onView(withId(R.id.rvLeague)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        //memastikan activity desksripsi liga serta komponennya ditampilkan
        onView(withId(R.id.leagueDesc)).check(matches(isDisplayed()))
        Thread.sleep(3000)
        onView(withId(R.id.imgDesc)).check(matches(isDisplayed()))
        onView(withId(R.id.tvLeagueNameDesc)).check(matches(isDisplayed()))
        onView(withId(R.id.tvWebDesc)).check(matches(isDisplayed()))
        onView(withId(R.id.tvDesc)).check(matches(isDisplayed()))

        //memastikan tombol match pada deskripsi liga bekerja
        onView(withId(R.id.btn_match)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_match)).perform(click())

        //memastikan recyclerView tampil
        onView(withId(R.id.fragPrevMatch)).check(matches(isDisplayed()))

        //memastikan searchView tampil
        onView(withId(R.id.search)).check(matches(isDisplayed()))

        //memastikan searchView dapat di klik
        onView(withId(R.id.search)).perform(click())

        //memastikan searchView dapat diketik keyword pencarian "Man United vs Arsenal"
        onView(isAssignableFrom(AutoCompleteTextView::class.java)).perform(typeText(match), pressImeActionButton())

        //memastikan recyclerView hasil pencarian tampil
        onView(withId(R.id.fragPrevMatch)).check(matches(isDisplayed()))

        //melakukan klik pada item ke-2 pada recyclerView untuk masuk ke detail match
        onView(withId(R.id.fragPrevMatch)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(2, click()))

        //melakukan pengecekan pada detail match apakah home team = arsenal, dan away team = chelsea sesuai keyword pencarian
        onView(withId(R.id.tvHomeTeam)).check(matches(isDisplayed()))
        onView(withText("Man United")).check(matches(isDisplayed()))
        onView(withId(R.id.tvAwayTeam)).check(matches(isDisplayed()))
        onView(withText("Arsenal")).check(matches(isDisplayed()))

        //melakukan klik tombol kembali dan menutup software keyboard
        onView(isRoot()).perform(pressBack()).perform(closeSoftKeyboard())

        //melakukan penghapusan keyword pada searchView
        onView(isAssignableFrom(AutoCompleteTextView::class.java)).perform(clearText())

        //memastikan recyclerView tampil dan berisi data default
        onView(withId(R.id.fragPrevMatch)).check(matches(isDisplayed()))

        //melakukan klik pada item ke-5 pada recyclerView untuk masuk ke detail match
        onView(withId(R.id.fragPrevMatch)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(2, click()))

        //melakukan pengecekan pada detail match apakah home team bukan arsenal, dan away team bukan chelsea
        onView(withId(R.id.tvHomeTeam)).check(matches(isDisplayed()))
        onView(withId(R.id.tvHomeTeam)).check(matches(not(withText("Man United"))))
        onView(withId(R.id.tvAwayTeam)).check(matches(isDisplayed()))
        onView(withId(R.id.tvAwayTeam)).check(matches(not(withText("Arsenal"))))
    }

    @Test
    fun searchTeamTest(){
        //memastikan recyclerView liga tampil
        onView(withId(R.id.rvLeague)).check(matches(isDisplayed()))

        //memastikan aksi klik pada recyclerView liga bekerja
        onView(withId(R.id.rvLeague)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        //memastikan activity desksripsi liga serta komponennya ditampilkan
        onView(withId(R.id.leagueDesc)).check(matches(isDisplayed()))
        Thread.sleep(3000)
        onView(withId(R.id.imgDesc)).check(matches(isDisplayed()))
        onView(withId(R.id.tvLeagueNameDesc)).check(matches(isDisplayed()))
        onView(withId(R.id.tvWebDesc)).check(matches(isDisplayed()))
        onView(withId(R.id.tvDesc)).check(matches(isDisplayed()))

        //memastikan tombol match pada deskripsi liga bekerja
        onView(withId(R.id.btn_team)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_team)).perform(click())

        //memastikan recyclerView tampil
        onView(withId(R.id.rvListTeam)).check(matches(isDisplayed()))

        //memastikan searchView tampil
        onView(withId(R.id.searchTeam)).check(matches(isDisplayed()))

        //memastikan searchView dapat di klik
        onView(withId(R.id.searchTeam)).perform(click())

        //memastikan searchView dapat diketik keyword pencarian "Chelsea"
        onView(isAssignableFrom(AutoCompleteTextView::class.java)).perform(typeText(team), pressImeActionButton())

        //memastikan recyclerView hasil pencarian tampil
        onView(withId(R.id.rvListTeam)).check(matches(isDisplayed()))

        //melakukan klik pada item pada recyclerView untuk masuk ke detail match
        onView(withId(R.id.rvListTeam)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        //melakukan pengecekan pada detail team apakah team bernama Chelsea sesuai keyword pencarian
        onView(withId(R.id.teamName)).check(matches(isDisplayed()))
        onView(withText(team)).check(matches(isDisplayed()))

        //melakukan klik tombol kembali dan menutup software keyboard
        onView(isRoot()).perform(pressBack()).perform(closeSoftKeyboard())

        //melakukan penghapusan keyword pada searchView
        onView(isAssignableFrom(AutoCompleteTextView::class.java)).perform(clearText())

        //memastikan recyclerView tampil dan berisi data default
        onView(withId(R.id.rvListTeam)).check(matches(isDisplayed()))

        //melakukan klik pada item pada recyclerView untuk masuk ke detail match
        onView(withId(R.id.rvListTeam)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        //melakukan pengecekan pada detail team apakah team = Chelsea sesuai keyword pencarian
        onView(withId(R.id.teamName)).check(matches(isDisplayed()))
        onView(withId(R.id.teamName)).check(matches(not(withText(team))))
    }
}