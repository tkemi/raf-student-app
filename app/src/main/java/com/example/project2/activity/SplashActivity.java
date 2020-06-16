package com.example.project2.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import com.example.project2.R;
import com.example.project2.model.UserResponse;
import com.example.project2.viewmodel.SplashViewModel;
import com.squareup.picasso.Picasso;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG="Splash Activity";
    private static final int DELLAY_MILLIS = 3000;
    private static final String URL = "https://picsum.photos/1080/1920/?random";

    @BindView(R.id.iv_splash_pic)
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        init();
    }

    private void init(){
        Picasso.get().load(URL).into(imageView);
        initViewModel();
    }

    private void initViewModel(){
        SplashViewModel viewModel = ViewModelProviders.of(this).get(SplashViewModel.class);
        viewModel.getLoggedInUserLiveData().observe(this, new Observer<UserResponse>() {
            @Override
            public void onChanged(UserResponse userResponse) {
                if(userResponse.isSuccessful()){
                    Log.e(TAG, "onChanged: user is logged in " + userResponse.getUser().toString() + " start main activity");
                    startActivityDelayed(MainActivity.class);
                } else {
                    Log.e(TAG, "onChanged: user is not logged in, start LogIn activity");
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivityDelayed(LoginActivity.class);
                }
            }
        });
    }

    private void startActivityDelayed(final Class cls){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this,cls);
                startActivity(intent);
                finish();
            }
        },DELLAY_MILLIS);
    }
}
