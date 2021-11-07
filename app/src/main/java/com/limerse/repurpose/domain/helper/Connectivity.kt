
package com.limerse.repurpose.domain.helper

import android.content.Context
import kotlin.Throws
import android.net.NetworkInfo
import android.net.ConnectivityManager
import android.telephony.TelephonyManager
import android.net.TrafficStats
import android.net.wifi.WifiManager
import android.os.Vibrator
import android.os.Build
import java.io.BufferedInputStream
import java.io.ByteArrayOutputStream
import java.io.FileInputStream
import java.io.IOException
import java.lang.Exception
import java.lang.StringBuilder
import java.lang.reflect.Method
import java.net.InetAddress
import java.net.NetworkInterface
import java.util.*

/**
 * Check device's network connectivity and speed
 *
 * @author emil http://stackoverflow.com/users/220710/emil
 */
object Connectivity {
    /**
     * Get the network info
     *
     * @param context
     * @return
     */
    fun getNetworkInfo(context: Context): NetworkInfo? {
        val cm = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (null != cm) cm.activeNetworkInfo else null
    }

    /**
     * Check if there is any connectivity
     *
     * @param context
     * @return
     */
    @JvmStatic
    fun isConnected(context: Context): Boolean {
        val info = getNetworkInfo(context)
        return info != null && info.isConnected
    }

    /**
     * Check if there is any connectivity to a Wifi network
     *
     * @param context
     * @param type
     * @return
     */
    fun isConnectedWifi(context: Context): Boolean {
        val info = getNetworkInfo(context)
        return info != null && info.isConnected && info.type == ConnectivityManager.TYPE_WIFI
    }

    /**
     * Check if there is any connectivity to a mobile network
     *
     * @param context
     * @param type
     * @return
     */
    fun isConnectedMobile(context: Context): Boolean {
        val info = getNetworkInfo(context)
        return info != null && info.isConnected && info.type == ConnectivityManager.TYPE_MOBILE
    }

    /**
     * Check if there is fast connectivity
     *
     * @param context
     * @return
     */
    fun isConnectedFast(context: Context): Boolean {
        val info = getNetworkInfo(context)
        return info != null && info.isConnected && isConnectionFast(info.type, info.subtype)
    }

    /**
     * Check if the connection is fast
     *
     * @param type
     * @param subType
     * @return
     */
    fun isConnectionFast(type: Int, subType: Int): Boolean {
        return if (type == ConnectivityManager.TYPE_WIFI) {
            true
        } else if (type == ConnectivityManager.TYPE_MOBILE) {
            when (subType) {
                TelephonyManager.NETWORK_TYPE_1xRTT -> false // ~ 50-100 kbps
                TelephonyManager.NETWORK_TYPE_CDMA -> false // ~ 14-64 kbps
                TelephonyManager.NETWORK_TYPE_EDGE -> false // ~ 50-100 kbps
                TelephonyManager.NETWORK_TYPE_EVDO_0 -> true // ~ 400-1000 kbps
                TelephonyManager.NETWORK_TYPE_EVDO_A -> true // ~ 600-1400 kbps
                TelephonyManager.NETWORK_TYPE_GPRS -> false // ~ 100 kbps
                TelephonyManager.NETWORK_TYPE_HSDPA -> true // ~ 2-14 Mbps
                TelephonyManager.NETWORK_TYPE_HSPA -> true // ~ 700-1700 kbps
                TelephonyManager.NETWORK_TYPE_HSUPA -> true // ~ 1-23 Mbps
                TelephonyManager.NETWORK_TYPE_UMTS -> true // ~ 400-7000 kbps
                TelephonyManager.NETWORK_TYPE_EHRPD -> true // ~ 1-2 Mbps
                TelephonyManager.NETWORK_TYPE_EVDO_B -> true // ~ 5 Mbps
                TelephonyManager.NETWORK_TYPE_HSPAP -> true // ~ 10-20 Mbps
                TelephonyManager.NETWORK_TYPE_IDEN -> false // ~25 kbps
                TelephonyManager.NETWORK_TYPE_LTE -> true // ~ 10+ Mbps
                TelephonyManager.NETWORK_TYPE_UNKNOWN -> false
                else -> false
            }
        } else {
            false
        }
    }

    /**
     * Convert byte array to hex string
     *
     * @param bytes
     * @return
     */
    fun bytesToHex(bytes: ByteArray): String {
        val sbuf = StringBuilder()
        for (idx in bytes.indices) {
//            val intVal: Int = bytes[idx] and 0xff
//            if (intVal < 0x10) sbuf.append("0")
//            sbuf.append(Integer.toHexString(intVal).toUpperCase())
        }
        return sbuf.toString()
    }

    /**
     * Get utf8 byte array.
     *
     * @param str
     * @return array of NULL if error was found
     */
    fun getUTF8Bytes(str: String): ByteArray? {
        return try {
            str.toByteArray(charset("UTF-8"))
        } catch (ex: Exception) {
            null
        }
    }

    /**
     * Load UTF8withBOM or any ansi text file.
     *
     * @param filename
     * @return
     * @throws java.io.IOException
     */
    @Throws(IOException::class)
    fun loadFileAsString(filename: String?): String {
        val BUFLEN = 1024
        val `is` = BufferedInputStream(
            FileInputStream(
                filename
            ), BUFLEN
        )
        return try {
            val baos = ByteArrayOutputStream(BUFLEN)
            val bytes = ByteArray(BUFLEN)
            var isUTF8 = false
            var read: Int
            var count = 0
            while (`is`.read(bytes).also { read = it } != -1) {
                if (count == 0 && bytes[0] == 0xEF.toByte() && bytes[1] == 0xBB.toByte() && bytes[2] == 0xBF.toByte()) {
                    isUTF8 = true
                    baos.write(bytes, 3, read - 3) // drop UTF8 bom marker
                } else {
                    baos.write(bytes, 0, read)
                }
                count += read
            }
            if (isUTF8) String(baos.toByteArray(), Charsets.UTF_8) else String(baos.toByteArray())
        } finally {
            try {
                `is`.close()
            } catch (ex: Exception) {
            }
        }
    }

    /**
     * Returns MAC address of the given interface name.
     *
     * @param interfaceName eth0, wlan0 or NULL=use first interface
     * @return mac address or empty string
     */
    fun getMACAddress(interfaceName: String?): String {
        try {
            val interfaces: List<NetworkInterface> = Collections
                .list(NetworkInterface.getNetworkInterfaces())
            for (intf in interfaces) {
                if (interfaceName != null) {
                    if (!intf.name.equals(interfaceName, ignoreCase = true)) continue
                }
                val mac = intf.hardwareAddress ?: return ""
                val buf = StringBuilder()
                for (idx in mac.indices) buf.append(String.format("%02X:", mac[idx]))
                if (buf.length > 0) buf.deleteCharAt(buf.length - 1)
                return buf.toString()
            }
        } catch (ex: Exception) {
        } // for now eat exceptions
        return ""
        /*
		 * try { // this is so Linux hack return
		 * loadFileAsString("/sys/class/net/" +interfaceName +
		 * "/address").toUpperCase().trim(); } catch (IOException ex) { return
		 * null; }
		 */
    }

    /**
     * Get IP address from first non-localhost interface
     *
     * @param ipv4 true=return ipv4, false=return ipv6
     * @return address or empty string
     */
    fun getIPAddress(useIPv4: Boolean): String {
        try {
            val interfaces: List<NetworkInterface> = Collections
                .list(NetworkInterface.getNetworkInterfaces())
            for (intf in interfaces) {
                val addrs: List<InetAddress> = Collections.list(
                    intf
                        .inetAddresses
                )
                for (addr in addrs) {
                    if (!addr.isLoopbackAddress) {
                        val sAddr = addr.hostAddress
                        // boolean isIPv4 =
                        // InetAddressUtils.isIPv4Address(sAddr);
                        val isIPv4 = sAddr.indexOf(':') < 0
                        if (useIPv4) {
                            if (isIPv4) return sAddr
                        } else {
                            if (!isIPv4) {
                                val delim = sAddr.indexOf('%') // drop ip6 zone
                                // suffix
                                return if (delim < 0) sAddr.toUpperCase() else sAddr
                                    .substring(0, delim).toUpperCase()
                            }
                        }
                    }
                }
            }
        } catch (ex: Exception) {
        } // for now eat exceptions
        return ""
    }

    fun getNetworkClass(context: Context): String {
        val cm = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = cm.activeNetworkInfo
        if (info == null || !info.isConnected) return "-" // not connected
        if (info.type == ConnectivityManager.TYPE_WIFI) return "WIFI"
        if (info.type == ConnectivityManager.TYPE_MOBILE) {
            val networkType = info.subtype
            return when (networkType) {
                TelephonyManager.NETWORK_TYPE_GPRS, TelephonyManager.NETWORK_TYPE_EDGE, TelephonyManager.NETWORK_TYPE_CDMA, TelephonyManager.NETWORK_TYPE_1xRTT, TelephonyManager.NETWORK_TYPE_IDEN -> "2G"
                TelephonyManager.NETWORK_TYPE_UMTS, TelephonyManager.NETWORK_TYPE_EVDO_0, TelephonyManager.NETWORK_TYPE_EVDO_A, TelephonyManager.NETWORK_TYPE_HSDPA, TelephonyManager.NETWORK_TYPE_HSUPA, TelephonyManager.NETWORK_TYPE_HSPA, TelephonyManager.NETWORK_TYPE_EVDO_B, TelephonyManager.NETWORK_TYPE_EHRPD, TelephonyManager.NETWORK_TYPE_HSPAP -> "3G"
                TelephonyManager.NETWORK_TYPE_LTE -> "4G"
                else -> "?"
            }
        }
        return "?"
    }

    val bytesRecieved: String
        get() = if (TrafficStats.UNSUPPORTED.toLong() != TrafficStats.getTotalRxBytes()) {
            """${TrafficStats.getTotalRxBytes()}
 (${bytesHoomanRedable(TrafficStats.getTotalRxBytes(), true)})"""
        } else {
            "Not supported by this device"
        }
    val bytesTransmitted: String
        get() = if (TrafficStats.UNSUPPORTED.toLong() != TrafficStats.getTotalTxBytes()) {
            """${TrafficStats.getTotalTxBytes()}
 (${bytesHoomanRedable(TrafficStats.getTotalTxBytes(), true)})"""
        } else {
            "Not supported by this device"
        }

    fun bytesHoomanRedable(bytes: Long, showInByte: Boolean): String {
        return if (!showInByte) {
            val bits = bytes * 8
            val Kbit: Long = 1024
            val Mbit = Kbit * 1024
            val Gbit = Mbit * 1024
            if (bits < Kbit) return "$bits bit"
            if (bits > Kbit && bits < Mbit) return (bits / Kbit).toString() + " Kilobit"
            if (bits > Mbit && bits < Gbit) return (bits / Mbit).toString() + " Megabit"
            if (bits > Gbit) (bits / Gbit).toString() + " Gigabit" else "???"
        } else {
            val Kbit: Long = 1024
            val Mbit = Kbit * 1024
            val Gbit = Mbit * 1024
            if (bytes < Kbit) return "$bytes Byte"
            if (bytes > Kbit && bytes < Mbit) return (bytes / Kbit).toString() + " KiloByte"
            if (bytes > Mbit && bytes < Gbit) return (bytes / Mbit).toString() + " MegaByte"
            if (bytes > Gbit) (bytes / Gbit).toString() + " GigaByte" else "???"
        }
    }

    fun toggleWifiConnection(
        context: Context,
        toggleWIFI: Boolean
    ): Boolean {
        vibrate(context)
        return (context.getSystemService(Context.WIFI_SERVICE) as WifiManager)
            .setWifiEnabled(toggleWIFI)
    }

    fun isWifiEnable(context: Context): Boolean {
        return (context.getSystemService(Context.WIFI_SERVICE) as WifiManager)
            .isWifiEnabled
    }

    fun vibrate(context: Context) {
        // Get instance of Vibrator from current Context and Vibrate for 400
        // milliseconds
        (context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator)
            .vibrate(100)
    }

    fun toggleDataConnection(
        toggleData: Boolean,
        context: Context
    ): Boolean {
        return try {
            vibrate(context)
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.FROYO) {
                val dataConnSwitchmethod: Method
                val telephonyManagerClass: Class<*>
                val ITelephonyStub: Any
                val ITelephonyClass: Class<*>
                val telephonyManager = context
                    .getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                telephonyManagerClass = Class.forName(
                    telephonyManager
                        .javaClass.name
                )
                val getITelephonyMethod = telephonyManagerClass
                    .getDeclaredMethod("getITelephony")
                getITelephonyMethod.isAccessible = true
                ITelephonyStub = getITelephonyMethod.invoke(telephonyManager)
                ITelephonyClass = Class.forName(
                    ITelephonyStub.javaClass
                        .name
                )
                dataConnSwitchmethod = if (toggleData) {
                    ITelephonyClass
                        .getDeclaredMethod("enableDataConnectivity")
                } else {
                    ITelephonyClass
                        .getDeclaredMethod("disableDataConnectivity")
                }
                dataConnSwitchmethod.isAccessible = true
                dataConnSwitchmethod.invoke(ITelephonyStub)
            } else {
                // log.i("App running on Ginger bread+");
                val conman = context
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val conmanClass = Class.forName(
                    conman.javaClass
                        .name
                )
                val iConnectivityManagerField = conmanClass
                    .getDeclaredField("mService")
                iConnectivityManagerField.isAccessible = true
                val iConnectivityManager = iConnectivityManagerField[conman]
                val iConnectivityManagerClass = Class
                    .forName(iConnectivityManager.javaClass.name)
                val setMobileDataEnabledMethod = iConnectivityManagerClass
                    .getDeclaredMethod("setMobileDataEnabled", java.lang.Boolean.TYPE)
                setMobileDataEnabledMethod.isAccessible = true
                setMobileDataEnabledMethod.invoke(
                    iConnectivityManager,
                    toggleData
                )
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun isMobileDataEnabled(context: Context): Boolean {
        var mobileDataEnabled = false // Assume disabled
        val cm = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        try {
            val cmClass = Class.forName(cm.javaClass.name)
            val method = cmClass.getDeclaredMethod("getMobileDataEnabled")
            method.isAccessible = true // Make the method callable
            // get the setting for "mobile data"
            mobileDataEnabled = method.invoke(cm) as Boolean
        } catch (e: Exception) {
            // Some problem accessible private API
            // TODO do whatever error handling you want here
        }
        return mobileDataEnabled
    }
}