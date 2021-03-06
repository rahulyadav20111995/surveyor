package com.surveyapp.Fragments;

import android.app.backup.SharedPreferencesBackupHelper;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.surveyapp.Activities.ActivityLoginSignUp;
import com.surveyapp.Activities.LandingActivity;
import com.surveyapp.Constants;
import com.surveyapp.R;
import com.surveyapp.SharedPrefUtil;
import com.surveyapp.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created Rahul Yadav by on 31-01-2016.
 */
public class FragmentSignUp extends Fragment {

    private AutoCompleteTextView signUpUserNameInput;
    private AutoCompleteTextView signUpUserEmailInput;
    private AutoCompleteTextView signUpPasswordInput;
    private AutoCompleteTextView signUpConfirmPasswordInput;
    private TextInputLayout signUpUserNameInputLayout;
    private TextInputLayout signUpUserEmailInputLayout;
    private TextInputLayout signUpPasswordInputLayout;
    private TextInputLayout signUpConfirmPasswordInputLayout;

    private SharedPrefUtil sharedPrefUtil;

    private Button signUpButton;

    public FragmentSignUp() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.btn_sign_up);
        ActivityLoginSignUp.toolbar.setVisibility(View.VISIBLE);
        ActivityLoginSignUp.toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        this.setHasOptionsMenu(true);

        sharedPrefUtil = new SharedPrefUtil(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_signup,container,false);

        signUpUserNameInput = (AutoCompleteTextView) rootView.findViewById(R.id.signUpUserNameInput);
        signUpUserEmailInput = (AutoCompleteTextView) rootView.findViewById(R.id.signUpEmailInput);
        signUpPasswordInput = (AutoCompleteTextView) rootView.findViewById(R.id.signUpPasswordInput);
        signUpConfirmPasswordInput = (AutoCompleteTextView) rootView.findViewById(R.id.signUpConfirmPasswordInput);

        signUpUserNameInputLayout = (TextInputLayout) rootView.findViewById(R.id.signUpUserNameInputLayout);
        signUpUserEmailInputLayout = (TextInputLayout) rootView.findViewById(R.id.signUpUserEmailInputLayout);
        signUpPasswordInputLayout = (TextInputLayout) rootView.findViewById(R.id.signUpPasswordInputLayout);
        signUpConfirmPasswordInputLayout = (TextInputLayout) rootView.findViewById(R.id.signUpConfirmPasswordInputLayout);

        signUpButton = (Button) rootView.findViewById(R.id.signUpButton);


        signUpUserNameInput.addTextChangedListener(new MyTextWatcher(signUpUserNameInput));
        signUpUserEmailInput.addTextChangedListener(new MyTextWatcher(signUpUserEmailInput));
        signUpPasswordInput.addTextChangedListener(new MyTextWatcher(signUpPasswordInput));
        signUpConfirmPasswordInput.addTextChangedListener(new MyTextWatcher(signUpConfirmPasswordInput));

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSignUpProcess();
            }
        });

        return rootView;
    }

    private void startSignUpProcess(){

        if (!validateUserName(signUpUserNameInput.getText().toString())){
            return;
        }

        if (!validateEmail(signUpUserEmailInput.getText().toString())){
            return;
        }

        if (!validatePassword(signUpPasswordInput.getText().toString())){
            return;
        }

        if (!validateConfirmPassword(signUpPasswordInput.getText().toString(),signUpConfirmPasswordInput.getText().toString())){
            return;
        }

        //Do The Sign Up Stuff Here
        if(Utils.isNetworkAvailable(getActivity())){
            new SignUpProcess().execute(signUpUserNameInput.getText().toString(),signUpUserEmailInput.getText().toString(),signUpPasswordInput.getText().toString());
        }
        else{
            Toast.makeText(getActivity(), "Check Device Connection", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){

            View view = getActivity().getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }

            getActivity().getSupportFragmentManager().popBackStack();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.signUpEmailInput:
                    validateEmail(signUpUserEmailInput.getText().toString());
                    break;
                case R.id.signUpUserNameInput:
                    validateUserName(signUpUserNameInput.getText().toString());
                    break;
                case R.id.signUpPasswordInput:
                    validatePassword(signUpPasswordInput.getText().toString());
                    break;
                case R.id.signUpConfirmPasswordInput:
                    validateConfirmPassword(signUpPasswordInput.getText().toString() , signUpConfirmPasswordInput.getText().toString());
                    break;
            }
        }
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private boolean validateEmail(String email) {

        if (email.isEmpty() || !Utils.isValidEmail(email)) {
            signUpUserEmailInputLayout.setError(getString(R.string.err_email_input));
            requestFocus(signUpUserEmailInput);
            return false;
        } else {
            signUpUserEmailInputLayout.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePassword(String password) {
        if (password.trim().isEmpty()) {
            signUpPasswordInputLayout.setError(getString(R.string.err_password_input));
            requestFocus(signUpPasswordInput);
            return false;
        } else {
            signUpPasswordInputLayout.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateUserName(String userName){

        if (userName.isEmpty()){
            signUpUserNameInputLayout.setError(getString(R.string.err_name_input));
            requestFocus(signUpUserNameInput);
            return false;
        }
        else {
            signUpUserNameInputLayout.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateConfirmPassword(String actualPassword , String inputPassword){

        if (!inputPassword.matches(actualPassword)){
            signUpConfirmPasswordInputLayout.setError(getString(R.string.err_confirm_password_input));
            requestFocus(signUpConfirmPasswordInput);
            return false;
        }
        else {
            signUpConfirmPasswordInputLayout.setErrorEnabled(false);
        }

        return true;
    }



    private class SignUpProcess extends AsyncTask<String,Void,String[]>{

        @Override
        protected void onPostExecute(String[] s) {
            if(s!=null){
                sharedPrefUtil.createSession(0,s[0],s[2],s[1], Constants.TYPE_SERVER_LOGIN,null);

                Intent reachLandingActivity = new Intent(getActivity(), LandingActivity.class);
                reachLandingActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(reachLandingActivity);
            }
            else{
                signUpUserNameInputLayout.setError("User Name Already Taken");
                signUpUserNameInput.requestFocus();
            }
        }

        @Override
        protected String[] doInBackground(String... params){
            String username=params[0];
            String email =params[1];
            String password =params[2];
            String completeUrl = ""; //will be changed when web service is created!
            completeUrl.replace(" ", "%20");

            JSONObject jsonObject = new JSONObject();
            try{
                jsonObject.put("username",username);
                jsonObject.put("email",email);
                jsonObject.put("password",password);

            }catch (Exception e){
                Log.d("error","creating json in fragment sign up");
            }

            jsonObject=Utils.postJSONObject(completeUrl,jsonObject);




            try{

                int status = jsonObject.getInt("status");

                boolean isPosted = jsonObject.getInt("isSuccess")==1;

                if(status==800)
                {
                    return params;
                }
                else if(status==801){
                    return null;
                }
            }catch(JSONException exception){
                Log.d("error","exception in retrieving json");
            }

            return null;
        }
    }

}

