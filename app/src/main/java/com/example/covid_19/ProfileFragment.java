package com.example.covid_19;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class ProfileFragment extends Fragment {

    FusedLocationProviderClient mFusedLocationClient;
    TextView addressTextView, nameTextView, emailPhoneTextView;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addressTextView = view.findViewById(R.id.user_address);
        nameTextView = view.findViewById(R.id.user_name);
        emailPhoneTextView = view.findViewById(R.id.user_email_phone);

        SharedPreferences sharedPref = getActivity().getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String name = sharedPref.getString("name", null);
        String email = sharedPref.getString("email", null);
        String phone = sharedPref.getString("phone", null);
        String address = sharedPref.getString("Address", null);
        nameTextView.setText(name);
        String text = email.length() > 0 ? email : phone;
        emailPhoneTextView.setText(text);
        addressTextView.setText(address);
    }


    @Override
    public void onResume() {
        super.onResume();
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
