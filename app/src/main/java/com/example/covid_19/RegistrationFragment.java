package com.example.covid_19;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.mobsandgeeks.saripaar.Validator;

import java.io.IOException;
import java.util.List;

public class RegistrationFragment extends Fragment implements View.OnClickListener, OnMapReadyCallback {

    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextMobile;
    private Button buttonSubmit;
    private Validator validator;

    private MapView mapView;
    private Context mContext;
    private SupportMapFragment supportMapFragment;
    private GoogleMap mMap;
    private MarkerOptions currentPositionMarker = null;
    private Marker currentLocationMarker;
    int PERMISSION_ID = 44;
    FusedLocationProviderClient mFusedLocationClient;
    TextView addressTextView, nameTextView, emailTextView, phoneTextView;
    List<android.location.Address> geocodeMatches = null;
    private String Address;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registration, container, false);

        //initializing view objects
        editTextName = (EditText) view.findViewById(R.id.editTextName);
        editTextEmail = (EditText) view.findViewById(R.id.editTextEmail);
        editTextMobile = (EditText) view.findViewById(R.id.editTextMobile);

        buttonSubmit = (Button) view.findViewById(R.id.buttonSubmit);

        validator = new Validator(this);
        buttonSubmit.setOnClickListener(this);

        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mContext = getActivity();

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mContext);
        FragmentManager fm = getActivity().getSupportFragmentManager();/// getChildFragmentManager();
        supportMapFragment = (SupportMapFragment) fm.findFragmentById(R.id.map);
        if (supportMapFragment == null) {
            supportMapFragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.map, supportMapFragment).commit();
        }
        supportMapFragment.getMapAsync(this);
    }


    private void swapFragment(){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();//declaring Fragment Manager
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction ();
        Fragment fragment = new CurrentLocation();
        fragmentTransaction.replace(R.id.SecondFragment, fragment, fragment.toString());
        fragmentTransaction.addToBackStack(fragment.toString());
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View view) {
        if (view == buttonSubmit && isValid()) {
            swapFragment();
        }
    }

    public boolean isValid() {
        boolean isValid = true;
        String editMobileValue = editTextMobile.getText() + "";
        String editEmailValue = editTextEmail.getText() + "";
        String editNameValue = editTextName.getText() + "";
        if(editNameValue.length() < 3){
            ((EditText) editTextName).setError("Please enter valid name");
            isValid = isValid && false;
        }
        if(editMobileValue.length() == 0 && !Patterns.EMAIL_ADDRESS.matcher(editEmailValue).matches()){
            ((EditText) editTextEmail).setError("Please enter valid email");
            isValid = isValid && false;
        }
        if(editEmailValue.length() == 0 && !Patterns.PHONE.matcher(editMobileValue).matches()){
            ((EditText) editTextMobile).setError("Please enter valid phone");
            isValid = isValid && false;
        }
        if(isValid) {
            SharedPreferences sharedPref = getActivity().getSharedPreferences(
                    getString(R.string.preference_file_key), Context.MODE_PRIVATE);
            SharedPreferences.Editor myEdit = sharedPref.edit();
            myEdit.putString("name", editNameValue);
            myEdit.putString("email", editEmailValue);
            myEdit.putString("phone", editMobileValue);
            myEdit.commit();
            Toast.makeText(getContext(), "Registration Successfull", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getContext(), "Enter either email or phone", Toast.LENGTH_SHORT).show();
        }
        return isValid;
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        getLastLocation();
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                if (location == null) {
                                    requestNewLocationData();
                                } else {
                                    SaveLocation(location);
                                }
                            }
                        }
                );
            } else {
                Toast.makeText(getContext(), "Turn on location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }


    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );

    }

    public void SaveLocation(Location location) {
        LatLng current = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions a = new MarkerOptions()
                .position(current);
        Marker m = mMap.addMarker(a);
        m.setPosition(current);
        m.setTitle("Current Location");
        mMap.moveCamera(CameraUpdateFactory.newLatLng(current));

        SharedPreferences sharedPref = getContext().getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPref.edit();

        System.out.println(location.describeContents() + "");
        // Storing the key and its value
        // as the data fetched from edittext
        myEdit.putString("lat", location.getLatitude() + "");
        myEdit.putString("long", location.getLongitude() + "");

        try {
            geocodeMatches =
                    new Geocoder(getContext()).getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (!geocodeMatches.isEmpty())
            {
                Address = geocodeMatches.get(0).getAddressLine(0);
//                Address2 = geocodeMatches.get(0).getAddressLine(1);
//                State = geocodeMatches.get(0).getAdminArea();
//                Zipcode = geocodeMatches.get(0).getPostalCode();
//                Country = geocodeMatches.get(0).getCountryName();
                myEdit.putString("Address", geocodeMatches.get(0).getAddressLine(0) + "");
                myEdit.putString("Address2", geocodeMatches.get(0).getAddressLine(1) + "");
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        // Once the changes have been made,
        // we need to commit to apply those changes made,
        // otherwise, it will throw an error
        myEdit.commit();
    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            SaveLocation(mLastLocation);
        }
    };

    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                getActivity(),
                new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }

    }
    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
