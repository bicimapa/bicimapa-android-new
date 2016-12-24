package fr.ylecuyer.bicimapa.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;

import fr.ylecuyer.bicimapa.MyPrefs_;
import fr.ylecuyer.bicimapa.MyRestClient;
import fr.ylecuyer.bicimapa.R;
import fr.ylecuyer.bicimapa.models.Token;

@EActivity(R.layout.activity_login)
public class LoginActivity extends AppCompatActivity implements FacebookCallback<LoginResult> {

    CallbackManager callbackManager;

    @ViewById
    EditText input_email;

    @ViewById
    EditText input_password;

    @Pref
    MyPrefs_ prefs;

    @RestService
    MyRestClient myRestClient;

    @AfterViews
    void initFB() {
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager, this);
    }

    @Click(R.id.btn_login)
    void login() {
        downloadToken();
    }

    @Background
    void downloadToken() {

        Token token = myRestClient.getToken(input_email.getText().toString(), input_password.getText().toString());

        Log.d("BICIMAPA", "token="+token);

        prefs.edit().user_token().put(token.getToken()).apply();

        quit();
    }

    @Override
    public void onSuccess(LoginResult loginResult) {

        AccessToken accessToken = loginResult.getAccessToken();

        String userId = accessToken.getUserId();

        Log.d("BICIMAPA", "userId="+userId);


        downloadTokenFB(userId);
    }

    @Background
    void downloadTokenFB(String userId) {
        Token token = myRestClient.getToken(userId);

        Log.d("BICIMAPA", "token="+token);

        prefs.edit().user_token().put(token.getToken()).apply();

        quit();
    }

    @Override
    public void onCancel() {
        Log.d("BICIMAPA", "Cancel");
    }

    @Override
    public void onError(FacebookException error) {

        Log.d("BICIMAPA", "FB on error " + error.toString());

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @UiThread
    void quit() {
        finish();
    }

}
