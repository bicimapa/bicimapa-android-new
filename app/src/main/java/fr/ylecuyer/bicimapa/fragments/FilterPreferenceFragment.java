package fr.ylecuyer.bicimapa.fragments;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.util.Log;

import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.ArrayList;

import fr.ylecuyer.bicimapa.AdjustMarkerToDensityTransformation;
import fr.ylecuyer.bicimapa.MyPrefs_;
import fr.ylecuyer.bicimapa.MyRestClient;
import fr.ylecuyer.bicimapa.PicassoPreference;
import fr.ylecuyer.bicimapa.R;
import fr.ylecuyer.bicimapa.models.Category;
import fr.ylecuyer.bicimapa.models.CategoryList;

@EFragment
public class FilterPreferenceFragment extends PreferenceFragment {

    private static final String PREF_NAME = "MyFilters";

    @RestService
    MyRestClient myRestClient;

    private CategoryList categories;

    ArrayList<PicassoPreference> picassoPreferences = new ArrayList<PicassoPreference>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceManager().setSharedPreferencesName(PREF_NAME);
    }

    @AfterInject
    void init() {
        downloadData();
    }

    @Background
    void downloadData() {
        categories = myRestClient.getCategories();
        showData();
    }

    @UiThread
    void showData() {

        if (isDetached()) {
            return;
        }

        PreferenceScreen screen = getPreferenceManager().createPreferenceScreen(getActivity());

        PreferenceCategory categoryRutas = new PreferenceCategory(getActivity());
        categoryRutas.setTitle("Rutas");

        screen.addPreference(categoryRutas);

        PreferenceCategory categorySitios = new PreferenceCategory(getActivity());
        categorySitios.setTitle("Sitios");

        screen.addPreference(categorySitios);





        for (Category category : categories) {
            CheckBoxPreference checkBoxPref = new CheckBoxPreference(getActivity());
            checkBoxPref.setTitle(category.getName());

            if (category.is_initial()) {
                checkBoxPref.setChecked(true);
            }

            if (category.getVariety().equalsIgnoreCase("SIT")) {
                checkBoxPref.setKey("SIT"+category.getId());

                categorySitios.addPreference(checkBoxPref);

                PicassoPreference picassoPreference = new PicassoPreference(checkBoxPref);

                picassoPreferences.add(picassoPreference);

                Picasso.with(getActivity()).load("http://bicimapa.com"+category.getIcon_path())
                        .transform(new AdjustMarkerToDensityTransformation(getResources().getDisplayMetrics().density))
                        .into(picassoPreference);
            }
            else if (category.getVariety().equalsIgnoreCase("LIN")) {

                checkBoxPref.setKey("LIN"+category.getId());

                Drawable icon = getResources().getDrawable(R.drawable.line);

                PorterDuffColorFilter porterDuffColorFilter = new PorterDuffColorFilter(Color.parseColor(category.getColor()), PorterDuff.Mode.SRC_ATOP);

                icon.setColorFilter(porterDuffColorFilter);

                checkBoxPref.setIcon(icon);

                categoryRutas.addPreference(checkBoxPref);

            }

        }



        /*PreferenceCategory categoryOtros = new PreferenceCategory(getActivity());
        categoryOtros.setTitle("Otros");
        screen.addPreference(categoryOtros);

        CheckBoxPreference checkBoxPrefReportes = new CheckBoxPreference(getActivity());
        checkBoxPrefReportes.setKey("reports");
        checkBoxPrefReportes.setTitle("Reportes");

        PicassoPreference picassoReportes = new PicassoPreference(checkBoxPrefReportes);

        picassoPreferences.add(picassoReportes);

        Picasso.with(getActivity()).load("http://bicimapa.com/assets/warn_pin-42c854a3ff0dab79ba9cffd1f5854dfa32b477955008c4d03d6e7085c3157f1d.png")
                .transform(new AdjustMarkerToDensityTransformation(getResources().getDisplayMetrics().density))
                .into(picassoReportes);

        categoryOtros.addPreference(checkBoxPrefReportes);*/


        setPreferenceScreen(screen);
    }
}
