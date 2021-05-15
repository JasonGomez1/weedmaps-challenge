package com.ngmatt.weedmapsandroidcodechallenge

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.jakewharton.espresso.OkHttp3IdlingResource
import com.ngmatt.weedmapsandroidcodechallenge.data.remote.YelpService
import com.ngmatt.weedmapsandroidcodechallenge.di.NetworkModule
import com.ngmatt.weedmapsandroidcodechallenge.ui.HelloWorldActivity
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@HiltAndroidTest
@UninstallModules(NetworkModule::class)
class HelloWorldActivityTest {

    @get:Rule(order = 0)
    val hiltAndroidRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var activityRule = ActivityScenarioRule(HelloWorldActivity::class.java)

    @BindValue
    @JvmField
    val okHttpClient: OkHttpClient = OkHttpClient()

    @BindValue
    @JvmField
    val yelpService: YelpService =
        Retrofit.Builder()
            .baseUrl("http://127.0.0.1:8080/")
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(YelpService::class.java)

    lateinit var server: MockWebServer

    @Before
    fun setUp() {
        hiltAndroidRule.inject()
        server = MockWebServer()
        server.start(8080)
        IdlingRegistry.getInstance().register(
            OkHttp3IdlingResource.create(
                "okhttp",
                okHttpClient
            )
        )
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun searchingForCity_displaysResults() {
        server.apply {
            // First call to the search endpoint
            enqueue(MockResponse().setBody(FileReader.readFile("reviews_response.json")))
            // First call to the business reviews endpoint
            enqueue(MockResponse().setBody(FileReader.readFile("search_response.json")))
        }

        onView(withId(R.id.editText))
            .perform(typeText("walmart"), click())
        onView(withId(R.id.recyclerView))
            .check(matches(isDisplayed()))
    }
}
