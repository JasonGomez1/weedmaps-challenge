package com.ngmatt.weedmapsandroidcodechallenge.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.ngmatt.weedmapsandroidcodechallenge.R
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.function.Consumer
import kotlin.coroutines.resume

@SuppressLint("MissingPermission")
suspend fun Context.awaitLastLocation() = suspendCancellableCoroutine<Location> { continuation ->
    val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
    val criteria = Criteria().apply {
        accuracy = Criteria.ACCURACY_FINE
    }
    val provider = locationManager.getBestProvider(criteria, true)
    val locationConsumer = Consumer<Location> { location ->
        continuation.resume(location)
    }
    provider?.let {
        locationManager.getCurrentLocation(
            it,
            null,
            ContextCompat.getMainExecutor(applicationContext),
            locationConsumer
        )
    }
}

fun AppCompatActivity.checkLocationPermission(block: () -> Unit) {
    val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                block()
            } else {
                val dialog = AlertDialog.Builder(this).apply {
                    setNeutralButton(R.string.ok) { _, _ ->

                    }
                    setMessage(
                        "Your location is needed to display the closest businesses for your search"
                    )
                }.create()
                dialog.show()
            }
        }

    when {
        ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED -> {
            block()
        }
        shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
            val dialog = AlertDialog.Builder(this).apply {
                setPositiveButton(
                    R.string.ok
                ) { _, _ ->
                    requestPermissionLauncher.launch(
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                }
                setNegativeButton(
                    R.string.no_thanks
                ) { _, _ ->
                    // Handle negative response here
                }
                setMessage(
                    "Your location is needed to display the closest businesses for your search"
                )
                setTitle("Requesting location permission")
            }.create()
            dialog.show()
        }
        else -> {
            requestPermissionLauncher.launch(
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }
    }
}
