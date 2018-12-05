package hu.selester.seltransport.Helper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.location.Address
import android.location.Geocoder
import android.media.ExifInterface
import android.net.ConnectivityManager
import android.util.Log
import android.widget.Toast
import com.google.android.gms.maps.model.LatLng
import java.io.IOException
import java.lang.Exception

class HelperClass(){

    companion object {

        fun toast(context: Context?, text: String) {
            Toast.makeText(context, text, Toast.LENGTH_LONG).show()
        }

        fun isOnline(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = cm.activeNetworkInfo
            return netInfo != null && netInfo.isConnectedOrConnecting
        }

        fun getLatFromAddress(context: Context?, address: String): LatLng {
            if (context != null) {
                val latitude: Double
                val longitude: Double
                var x: LatLng

                var geocodeMatches: List<Address>? = null

                try {
                    geocodeMatches = Geocoder(context).getFromLocationName(address, 1)
                } catch (e: IOException) {
                    e.printStackTrace()
                }

                try {
                    if (geocodeMatches != null) {
                        latitude = geocodeMatches[0].latitude
                        longitude = geocodeMatches[0].longitude
                        x = LatLng(latitude, longitude)
                    } else {
                        x = LatLng(0.0, 0.0)
                    }
                } catch (e: Exception) {
                    x = LatLng(0.0, 0.0)
                }
                Log.i("TAG", x.toString())
                return x
            } else {
                return LatLng(0.0, 0.0)
            }

        }

        fun loadLocalImage(path: String, scaledSize: Int): Bitmap? {
            //Log.i("GU","loadLocalImage: "+path);
            return getPicCorrectOrientation(path, scaledSize)
        }

        fun getPicCorrectOrientation(filePath: String, scaledSize: Int): Bitmap? {
            val options = BitmapFactory.Options()
            options.inScaled = true
            options.inSampleSize = scaledSize
            val bitmap = BitmapFactory.decodeFile(filePath, options)
            val imageHeight = options.outHeight // 1024
            val imageWidth = options.outWidth // 860
            var ei: ExifInterface? = null
            try {
                ei = ExifInterface(filePath)
            } catch (e: IOException) {
                e.printStackTrace()
            }

            val orientation = ei!!.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED)
            var rotatedBitmap: Bitmap? = null
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> rotatedBitmap = rotateImage(bitmap, 90f)

                ExifInterface.ORIENTATION_ROTATE_180 -> rotatedBitmap = rotateImage(bitmap, 180f)

                ExifInterface.ORIENTATION_ROTATE_270 -> rotatedBitmap = rotateImage(bitmap, 270f)

                ExifInterface.ORIENTATION_NORMAL -> rotatedBitmap = bitmap
                else -> rotatedBitmap = bitmap
            }

            return rotatedBitmap
        }

        fun rotateImage(source: Bitmap, angle: Float): Bitmap {
            val matrix = Matrix()
            matrix.postRotate(angle)
            return Bitmap.createBitmap(source, 0, 0, source.width, source.height, matrix, true)
        }
    }


}