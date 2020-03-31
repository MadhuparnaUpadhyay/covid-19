package com.example.covid_19;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class RegistrationFragment extends Fragment implements View.OnClickListener {

    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextMobile;
    private Button buttonSubmit;

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

        buttonSubmit.setOnClickListener(this);

        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void swapFragment() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();//declaring Fragment Manager
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = new MapsFragment();
        fragmentTransaction.replace(R.id.registration, fragment, fragment.toString());
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
        if (editNameValue.length() < 3) {
            ((EditText) editTextName).setError("Please enter valid name");
            isValid = isValid && false;
        }
        if (editMobileValue.length() == 0 && !Patterns.EMAIL_ADDRESS.matcher(editEmailValue).matches()) {
            ((EditText) editTextEmail).setError("Please enter valid email");
            isValid = isValid && false;
        }
        if (editEmailValue.length() == 0 && !Patterns.PHONE.matcher(editMobileValue).matches()) {
            ((EditText) editTextMobile).setError("Please enter valid phone");
            isValid = isValid && false;
        }
        if (isValid) {
            SharedPreferences sharedPref = getActivity().getSharedPreferences(
                    getString(R.string.preference_file_key), Context.MODE_PRIVATE);
            SharedPreferences.Editor myEdit = sharedPref.edit();
            myEdit.putString("name", editNameValue);
            myEdit.putString("email", editEmailValue);
            myEdit.putString("phone", editMobileValue);
            myEdit.commit();
//            Toast.makeText(getContext(), "Registration Successfull", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getContext(), "Enter either email or phone", Toast.LENGTH_SHORT).show();
        }
        return isValid;
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
