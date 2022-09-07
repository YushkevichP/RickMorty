package com.example.hm7_cleanarchitecture.data.service

import android.Manifest
import android.content.Context
import android.location.Location
import android.os.Looper
import androidx.annotation.RequiresPermission
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

// должен быть интеррнал?
class LocationService(context: Context) {

    private val locationClient = LocationServices.getFusedLocationProviderClient(context)

    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    fun getLocationFlow() = callbackFlow {

        val locationRequest = LocationRequest.create().apply {
            interval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        val locationCallback = object : LocationCallback(){
            override fun onLocationResult(result: LocationResult) {
                trySend(result.lastLocation)
            }
        }

        //это мы подписываемся
        locationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )

        //тут отписываемся
        awaitClose {
            locationClient.removeLocationUpdates(locationCallback)
        }
    }

    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    suspend fun getCurrentLocation(): Location? = suspendCoroutine { continuation ->
        locationClient.lastLocation

            //если успешно
            .addOnSuccessListener { location ->
                continuation.resume(location)
            }

            //если отменится
            .addOnCanceledListener {
                continuation.resume(null)
            }

            //если фейл
            .addOnFailureListener {
                continuation.resume(null)
            }
    }
}