package com.example.covid_19;

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
        Bundle arguments = new Bundle();
        String editEmailValue = editTextEmail.getText() + "";
        String editNameValue = editTextName.getText() + "";
        arguments.putString("name", editNameValue);
        arguments.putString("email", editEmailValue);
        fragment.setArguments(arguments);
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
        String editEmailValue = editTextEmail.getText() + "";
        String editNameValue = editTextName.getText() + "";
        if (editNameValue.length() < 3) {
            ((EditText) editTextName).setError("Please enter valid name");
            isValid = isValid && false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(editEmailValue).matches() && !Patterns.PHONE.matcher(editEmailValue).matches()) {
            ((EditText) editTextEmail).setError("Please enter valid email or phone");
            isValid = isValid && false;
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
