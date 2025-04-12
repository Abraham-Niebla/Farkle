package uabc.farkle.utils

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.annotation.RequiresPermission
import androidx.core.content.ContextCompat

@RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
fun networkAvailable(context: Context): Boolean {
    val connectivityManager = ContextCompat.getSystemService(
        context,
        ConnectivityManager::class.java
    ) ?: return false

    val network = connectivityManager.activeNetwork ?: return false
    val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

    return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}
