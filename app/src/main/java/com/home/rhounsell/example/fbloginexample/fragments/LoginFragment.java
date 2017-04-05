package com.home.rhounsell.example.fbloginexample.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.home.rhounsell.example.fbloginexample.R;
import com.home.rhounsell.example.fbloginexample.datasource.UserDataSource;
import com.home.rhounsell.example.fbloginexample.model.User;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginFragment extends Fragment {
    private CallbackManager callbackManager;
    private UserDataSource uDs;
    private Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(context);
        callbackManager = CallbackManager.Factory.create();

        View view = inflater.inflate(R.layout.fragment_login_layout,container, false);
        uDs=new UserDataSource(getActivity());
        uDs.open();

        LoginButton loginButton = (LoginButton) view.findViewById(R.id.login_button);
        loginButton.setText("Login");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Fragment fragment = null;
                        Class loginFragment = HelloFragment.class;

                        try {
                            User user = uDs.createUser(object.getString("name"), "", object.getString("id"));
                            Log.i("TESTE", user.getName());
                            Bundle userArgs = new Bundle();
                            userArgs.putLong("user_name", user.getUserId());
                            fragment = (Fragment) loginFragment.newInstance();
                            fragment.setArguments(userArgs);

                        } catch (java.lang.InstantiationException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        FragmentManager fm = getFragmentManager();
                        fm.beginTransaction().replace(R.id.replace, fragment).commit();

                    }
                });
                Bundle params = new Bundle();
                params.putString("fields", "id,name,link");
                request.setParameters(params);
                request.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

}
