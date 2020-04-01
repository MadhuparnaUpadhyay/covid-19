package com.example.covid_19;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegistrationProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistrationProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegistrationProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegistrationProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegistrationProfileFragment newInstance(String param1, String param2) {
        RegistrationProfileFragment fragment = new RegistrationProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();//declaring Fragment Manager
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction ();// declaring Fragment Transaction

        SharedPreferences sharedPref = getActivity().getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String defaultValue = sharedPref.getString("name", null);

        if(defaultValue != null) {
//            return inflater.inflate(R.layout.fragment_profile, container, false);
            ((SideBarActivity) getActivity()).getSupportActionBar().setTitle("Profile");
            Fragment fragment = new ProfileFragment();
            fragmentTransaction.replace(R.id.SecondFragment, fragment, fragment.toString());
            fragmentTransaction.addToBackStack(fragment.toString());
            fragmentTransaction.commit();
        }
        else {
//            return inflater.inflate(R.layout.fragment_registration, container, false);
            ((SideBarActivity) getActivity()).getSupportActionBar().setTitle("Registration");
            Fragment fragment = new RegistrationFragment();
            fragmentTransaction.replace(R.id.SecondFragment, fragment, fragment.toString());
            fragmentTransaction.addToBackStack(fragment.toString());
            fragmentTransaction.commit();
        }
        return inflater.inflate(R.layout.fragment_registration_profile, container, false);
    }

    @Override
    public void onStop() {
        super.onStop();
//        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();//declaring Fragment Manager
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction ();// declaring Fragment Transaction
//
//        SharedPreferences sharedPref = getActivity().getSharedPreferences(
//                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
//        String defaultValue = sharedPref.getString("name", null);
////
//        if(defaultValue != null) {
////            return inflater.inflate(R.layout.fragment_profile, container, false);
//            Fragment fragment = new CurrentLocation();
//            fragmentTransaction.remove(fragment);
//            fragmentTransaction.commit();
//        }
//        else {
////            return inflater.inflate(R.layout.fragment_registration, container, false);
//            Fragment fragment = new RegistrationFragment();
//            fragmentTransaction.remove(fragment);
//            fragmentTransaction.commit();
//        }
    }

    @Override
    public void onDestroy() {
        Toast.makeText(getActivity(), "dnfknsgklnsl", Toast.LENGTH_LONG).show();
        super.onDestroy();

    }

    @Override
    public void onResume() {
        super.onResume();
// Set title
//        ((SideBar) getActivity()).getActionBar().setTitle("getString(R.string.fragment_login)");
    }
}
