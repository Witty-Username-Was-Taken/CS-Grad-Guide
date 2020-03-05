package edu.uga.cs.msproject.gradhelper.locationFinder

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import edu.uga.cs.msproject.gradhelper.R

class LocationListActivity : AppCompatActivity() {

    private var locationListFragment : LocationListFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_location_list)

        locationListFragment = supportFragmentManager.findFragmentById(R.id.location_list_fragment)
        as LocationListFragment

        Log.d(TAG, "LocationListFragment is Null: " + (locationListFragment == null).toString() )

    }

    companion object {
        private const val TAG = "LOCATION_LIST_ACTIVITY"
    }

}