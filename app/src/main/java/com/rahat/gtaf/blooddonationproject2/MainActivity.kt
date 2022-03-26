package com.rahat.gtaf.blooddonationproject2

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.rahat.gtaf.blooddonationproject2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mref: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth = FirebaseAuth.getInstance()
        mref = FirebaseDatabase.getInstance().getReference(Const.user_path)
        Dexter.withContext(this)
            .withPermissions(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest?>?,
                    token: PermissionToken?
                ) {
                }
            }).check()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        locationRequest = LocationRequest.create()
        locationRequest.interval = 50000
        locationRequest.fastestInterval = 20000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY;
        getCurrentLocations()

        val navigationController = findNavController(R.id.container_fragment)
        setDrawerLayout(navigationController)

        navigationController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.splashScreenFragment || destination.id == R.id.accountSetUpFragment
                || destination.id == R.id.signInFragment || destination.id == R.id.signUpFragment
            ) {

                binding.toolbar.visibility = View.GONE
            } else {
                binding.toolbar.visibility = View.VISIBLE
            }

            when (destination.id) {
                R.id.homeFragment -> binding.navigation.setCheckedItem(R.id.home)
                R.id.donorLiveMapFragment -> binding.navigation.setCheckedItem(R.id.liveMap)
            }

        }

    }

    private fun getCurrentLocations() {
        if (checkPermission()) {

            if (isLocataionEnabled()) {

                var loccall: LocationCallback = object : LocationCallback() {
                    override fun onLocationResult(p0: LocationResult) {
                        super.onLocationResult(p0)
                        val locations = p0.locations
                        if (locations != null) {
                            val location = locations[0]

                            Log.d(
                                "TAG",
                                "onLocationResult:  ${location.latitude}  ${location.longitude}"
                            )

                            uploadTheLocaiton(location)

                        }
                    }
                }

                fusedLocationClient.requestLocationUpdates(
                    locationRequest,
                    loccall,
                    Looper.getMainLooper()
                )

            } else {
                "Turn On Location".toast(this)
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }

        } else {
            requestPermission()
        }
    }

    private fun uploadTheLocaiton(location: Location) {
        val user = mAuth.currentUser
        if (user != null) {
            //upload the user
            val hashMap: HashMap<String, Any> = HashMap()
            hashMap["lat"] = location.latitude
            hashMap["lon"] = location.longitude
            mref.child(user.uid.toString()).updateChildren(hashMap)

        }
    }

    private fun isLocataionEnabled(): Boolean {
        val locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this, arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ), 100
        )
    }


    private fun checkPermission(): Boolean {
        return (ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
                == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
                == PackageManager.PERMISSION_GRANTED)

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocations()
            } else {
                "Please Activate Permission".toast(this)
            }
        }
    }


    private fun setDrawerLayout(navigationController: NavController) {

        binding.toolbar.setNavigationOnClickListener {

            if (binding.drawer.isDrawerOpen(GravityCompat.START)) {
                binding.drawer.closeDrawer(GravityCompat.START)
            } else {
                binding.drawer.openDrawer(GravityCompat.START)
            }

        }

//        val header: View = binding.navigationView.getHeaderView(0)
//        val imageViewonDrawerLayoputLayout =
//            header.findViewById<View>(com.google.android.gms.location.R.id.imageONdrawerLaoout) as CircleImageView
//        val name = header.findViewById<View>(com.google.android.gms.location.R.id.nameOndrawerLayout) as TextView
//        val mobile = header.findViewById<View>(com.google.android.gms.location.R.id.mobileNumberOnHeader) as TextView
//        val addressOnHeader = header.findViewById<View>(com.google.android.gms.location.R.id.addressOnHeader) as TextView

//        addressOnHeader.text = model?.address
//        name.text = model?.name
//        mobile.text = model?.ph
//
//        Glide.with(applicationContext)
//            .load(model?.pp)
//            .error(com.google.android.gms.location.R.drawable.placeholder)
//            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
//            .into(imageViewonDrawerLayoputLayout)
//        //  addresss = header.findViewById<View>(R.id.addressOndrawerLayout) as TextView


        binding.navigation.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    // handle click
                    binding.drawer.closeDrawer(GravityCompat.START)
                    val handler = Handler()
                    handler.postDelayed({ navigationController.navigate(R.id.homeFragment) }, 300)
                    true
                }

                R.id.log_out -> {
                    // handle click
                    mAuth.signOut()
                    navigationController.navigate(
                        R.id.signInFragment, null, NavOptions.Builder()
                            .setPopUpTo(R.id.splashScreenFragment, true)
                            .build()
                    )

                    true
                }
                R.id.liveMap -> {
                    // handle click
                    binding.drawer.closeDrawer(GravityCompat.START)
                    val handler = Handler()
                    handler.postDelayed(object : Runnable {
                        override fun run() {
                            navigationController.navigate(R.id.donorLiveMapFragment)
                        }
                    }, 300)


                    true
                }


                else -> {
                    true
                }
            }
        }
    }
}