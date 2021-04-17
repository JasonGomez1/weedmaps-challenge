package com.ngmatt.weedmapsandroidcodechallenge.utils

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import androidx.core.content.ContextCompat
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.function.Consumer
import kotlin.coroutines.resume

/*@SuppressLint("MissingPermission")
fun Context.locationSharedFlow() = callbackFlow {
    val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
    val locationListener = LocationListener { location ->
        try {
            offer(location)
        } catch (e: Throwable) {
            // Handle when channel is closed
        }
    }

    locationManager.requestLocationUpdates(
        LocationManager.GPS_PROVIDER,
        3000,
        4000f,
        locationListener,
        Looper.getMainLooper()
    )

    awaitClose {
        locationManager.removeUpdates(locationListener)
    }
}.shareIn(
    ProcessLifecycleOwner
        .get()
        .lifecycleScope,
    replay = 1,
    started = SharingStarted.WhileSubscribed()
)*/

@SuppressLint("MissingPermission")
suspend fun Context.awaitLastLocation() = suspendCancellableCoroutine<Location> { continuation ->
    val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
    val locationConsumer = Consumer<Location> { location ->
        continuation.resume(location)
    }
    locationManager.getCurrentLocation(
        LocationManager.GPS_PROVIDER,
        null,
        ContextCompat.getMainExecutor(applicationContext),
        locationConsumer
    )
}
