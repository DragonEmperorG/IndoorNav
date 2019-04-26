package cn.edu.whu.lmars.indoornavigation

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource


class MainActivity : AppCompatActivity() {

    private var mapView: MapView? = null
    private val TAG: String = "Mapbox    "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Mapbox access token is configured here. This needs to be called either in your application
        // object or in the same activity which contains the mapview.
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token))

        // This contains the MapView in XML and needs to be called after the access token is configured.
        setContentView(R.layout.activity_main)

        mapView = findViewById(R.id.mapView)
        mapView?.onCreate(savedInstanceState)
        mapView?.getMapAsync { mapboxMap ->
            mapboxMap.setStyle(Style.Builder().fromUrl("asset://gaode.json")) {
                // Custom map style has been loaded and map is now ready
                    style: Style ->
                kotlin.run {
                    val indoor3DMap: GeoJsonSource =
                        GeoJsonSource("room-data", loadJsonFromAsset("indoor-3d-map.geojson"))
                    style.addSource()
                }
            }
        }
    }

    // Add the mapView lifecycle to the activity's lifecycle methods
    public override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    public override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView?.onSaveInstanceState(outState)
    }

    private fun loadJsonFromAsset(fileName: String) {
        // Using this method to load in GeoJSON files from the assets folder.

        try {
            val `is` = assets.open(fileName)
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            return String(buffer, "UTF-8")
        } catch (ex: NumberFormatException) {
            ex.printStackTrace()
            return null
        }

    }
}
