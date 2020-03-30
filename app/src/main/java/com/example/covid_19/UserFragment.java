package com.example.covid_19;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

public class UserFragment extends Fragment implements View.OnClickListener {

    //The view objects
    private EditText editTextName, editTextEmail, editTextMobile;

    private Button buttonSubmit;

    //defining AwesomeValidation object
    private AwesomeValidation awesomeValidation;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        //initializing awesomevalidation object
        /*
         * The library provides 3 types of validation
         * BASIC
         * COLORATION
         * UNDERLABEL
         * */
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        //initializing view objects
        editTextName = (EditText) view.findViewById(R.id.editTextName);
        editTextEmail = (EditText) view.findViewById(R.id.editTextEmail);
        editTextMobile = (EditText) view.findViewById(R.id.editTextMobile);

        buttonSubmit = (Button) view.findViewById(R.id.buttonSubmit);


        //adding validation to edittexts
        awesomeValidation.addValidation(getActivity(), R.id.editTextName, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(getActivity(), R.id.editTextEmail, Patterns.EMAIL_ADDRESS, R.string.emailerror);
        awesomeValidation.addValidation(getActivity(), R.id.editTextMobile, "^[2-9]{2}[0-9]{8}$", R.string.mobileerror);


        buttonSubmit.setOnClickListener(this);

        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void submitForm() {
        Context context = getActivity();
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        // Creating an Editor object
        // to edit(write to the file)
        SharedPreferences.Editor myEdit = sharedPref.edit();

        // Storing the key and its value
        // as the data fetched from edittext
        myEdit.putString("name", editTextName.getText().toString());
        myEdit.putString("email", editTextEmail.getText().toString());
        myEdit.putString("mobile", editTextMobile.getText().toString());

        // Once the changes have been made,
        // we need to commit to apply those changes made,
        // otherwise, it will throw an error
        myEdit.commit();
        //first validate the form then move ahead
        //if this becomes true that means validation is successfull
//        if (awesomeValidation.validate()) {
            Toast.makeText(getActivity(), "Registration Successfull", Toast.LENGTH_LONG).show();
            //process the data further
        swapFragment();
//        }

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
        if (view == buttonSubmit) {
            submitForm();
        }
    }
}
