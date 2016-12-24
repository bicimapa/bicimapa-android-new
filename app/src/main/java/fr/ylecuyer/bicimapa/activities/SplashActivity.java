package fr.ylecuyer.bicimapa.activities;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.common.api.GoogleApiClient;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;

import fr.ylecuyer.bicimapa.AdjustMarkerToDensityTransformation;
import fr.ylecuyer.bicimapa.MyFilters_;
import fr.ylecuyer.bicimapa.MyRestClient;
import fr.ylecuyer.bicimapa.models.Category;
import fr.ylecuyer.bicimapa.models.CategoryList;
import okhttp3.OkHttpClient;

@EActivity
public class SplashActivity extends AppCompatActivity {

    @Pref
    MyFilters_ myFilters;

    @RestService
    MyRestClient myRestClient;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFacebookSDK();
        initCategories();
    }

    void initFacebookSDK() {
        FacebookSdk.sdkInitialize(this);
        AppEventsLogger.activateApp(this);
    }

    @Background
    void initCategories() {

        SharedPreferences prefs = myFilters.getSharedPreferences();
        SharedPreferences.Editor editor = prefs.edit();

        boolean must_init_filters = prefs.getAll().isEmpty();

        final CategoryList categories = myRestClient.getCategories();

        for (final Category category : categories) {

            if (category.getVariety().equalsIgnoreCase("SIT")) {
                Log.d("CACHE", "fetching="+"http://bicimapa.com"+category.getIcon_path());


                Picasso.with(this).load("http://bicimapa.com"+category.getIcon_path()).transform(new AdjustMarkerToDensityTransformation(getResources().getDisplayMetrics().density))
                        .fetch(new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.d("CACHE", "onSuccess " + category.getIcon_path());
                    }

                    @Override
                    public void onError() {
                        Log.d("CACHE", "onError " + category.getIcon_path());
                    }
                });
            }

            if (must_init_filters) {
                editor.putBoolean(category.getVariety() + category.getId(), category.is_initial());
            }
        }

        editor.commit();

        launchMainActivity();
    }

    @UiThread
    void fetchCategoryIcon(String iconUrl) {

    }

    private void launchMainActivity() {
        Log.d("BICIMAPA", "Launching main activity");
        MainActivity_.intent(this).start();
        finish();
    }

}
