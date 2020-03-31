package com.example.covid_19;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
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

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Optional;
import com.mobsandgeeks.saripaar.annotation.Or;

import java.util.List;

public class RegistrationFragment extends Fragment implements View.OnClickListener, Validator.ValidationListener {

    @NotEmpty
    @Length(min = 3, max = 15)
    private EditText editTextName;

    @Optional
    @Or
    @Email
    private EditText editTextEmail;

    @Optional
    @Or
    @Length(min = 10)
    private EditText editTextMobile;

    private Button buttonSubmit;
    private Validator validator;

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
        validator.setValidationListener(this);
        buttonSubmit.setOnClickListener(this);

        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        Fragment fragmentC = new MapsFragment();
//        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
//        transaction.add(R.id.map, fragmentC ).commit();
    }

    private void submitForm() {
        validator.validate();
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

    @Override
    public void onValidationSucceeded() {
        Context context = getActivity();
        String editMobileVlaue = editTextMobile.getText() + "";
        String editEmailVlaue = editTextEmail.getText() + "";
        String editNameVlaue = editTextName.getText() + "";
        if(editMobileVlaue != null || editEmailVlaue != null) {
            SharedPreferences sharedPref = context.getSharedPreferences(
                    getString(R.string.preference_file_key), Context.MODE_PRIVATE);
            SharedPreferences.Editor myEdit = sharedPref.edit();
            myEdit.putString("name", editNameVlaue);
            myEdit.putString("email", editEmailVlaue);
            myEdit.putString("phone", editMobileVlaue);
            myEdit.commit();
            Toast.makeText(getActivity(), "Registration Successfull", Toast.LENGTH_LONG).show();
            swapFragment();
        } else {
            Toast.makeText(context, "Enter either email or phone", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getActivity());
            // Display error messages
            if (view instanceof EditText) {
//                Toast.makeText(getActivity(), view.getId() + "bjfh" + R.id.editTextEmail + "Registration Successfull", Toast.LENGTH_LONG).show();
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
