package com.home.rhounsell.example.fbloginexample;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.home.rhounsell.example.fbloginexample.datasource.UserDataSource;
import com.home.rhounsell.example.fbloginexample.fragments.HelloFragment;
import com.home.rhounsell.example.fbloginexample.fragments.LoginFragment;
import com.home.rhounsell.example.fbloginexample.model.User;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private static String FB_TAG="FB";
    private UserDataSource uDS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        uDS = new UserDataSource(this);
        uDS.open();
        Fragment fragment = null;
        Class fragmentClass;
        try {
            String tag = null;

            if(uDS.countUsers() <= 0) {
                fragmentClass = LoginFragment.class;
                tag = FB_TAG;
            }else{
                fragmentClass = HelloFragment.class;
            }
            Bundle bundle = new Bundle();
            bundle.putLong("user_id", 1);
            fragment = (Fragment) fragmentClass.newInstance();
            fragment.setArguments(bundle);
            startFragment(fragment, tag);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    private void startFragment(Fragment fragment, @Nullable String nullTag){
        FragmentManager fragmentManager = getSupportFragmentManager();

        if(nullTag!=null) {
            fragmentManager.beginTransaction().add(fragment, nullTag);
        }
        fragmentManager.beginTransaction().replace(R.id.replace, fragment).commit();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(FB_TAG);
        fragment.onActivityResult(requestCode,resultCode,data);
    }
}
