package com.ngmatt.weedmapsandroidcodechallenge

import com.ngmatt.weedmapsandroidcodechallenge.data.remote.YelpService
import com.ngmatt.weedmapsandroidcodechallenge.di.NetworkModule
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@HiltAndroidTest
@UninstallModules(NetworkModule::class)
class HelloWorldActivityTest {

    @get:Rule
    val hiltAndroidRule = HiltAndroidRule(this)

    @BindValue
    @JvmField
    val mockWebServerService: YelpService =
        Retrofit.Builder()
            .baseUrl("http://127.0.0.1:8080/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(YelpService::class.java)

    lateinit var server: MockWebServer

    @Before
    fun setUp() {
        hiltAndroidRule.inject()
        server = MockWebServer()
        server.start(8080)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }
}
