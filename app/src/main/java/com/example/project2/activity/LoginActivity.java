package com.example.project2.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.project2.R;
import com.example.project2.model.UserResponse;
import com.example.project2.viewmodel.LoginViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private String indexPattern = "[Rr][MmNn]-[0-9][0-9]-[0-9][0-9]";

    private LoginViewModel mLoginViewModel;

    @BindView(R.id.et_login_index)
    TextInputEditText mIndexIdEt;
    @BindView(R.id.et_login_name)
    TextInputEditText mUsernameEt;
    @BindView(R.id.btn_login)
    MaterialButton mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        init();
    }

    private void init(){
        initUi();
        initViewModel();
    }

    private void initUi(){
        mLoginButton.setOnClickListener(v -> {
            String name = mUsernameEt.getText().toString();
            String indexId = mIndexIdEt.getText().toString();

            Pattern pattern = Pattern.compile(indexPattern);
            Matcher matcher = pattern.matcher(indexId);
            boolean matches = matcher.matches();

            if(name.trim().isEmpty() || !matches){
                Toast.makeText(LoginActivity.this,"Input not valid",Toast.LENGTH_LONG).show();
            }else {
                mLoginButton.setEnabled(false);
                mLoginViewModel.logInUser(indexId,name);
            }
        });
    }

    private void initViewModel(){
        mLoginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        mLoginViewModel.getUserStoreLiveData().observe(this, new Observer<UserResponse>() {
            @Override
            public void onChanged(UserResponse userResponse) {
                if (userResponse.isSuccessful()) {
                    Log.e(TAG, "onChanged: user stored in FireBase db and shared pref" + userResponse.getUser().toString());
                    // We can either send user info through intent, or observe UserStoreLiveData in MainActivity
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    mLoginButton.setEnabled(true);
                    Toast.makeText(LoginActivity.this, "Log in failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
