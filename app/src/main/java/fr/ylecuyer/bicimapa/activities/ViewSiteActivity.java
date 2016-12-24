package fr.ylecuyer.bicimapa.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ActionProvider;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.model.LatLng;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.FragmentById;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.ArrayList;

import fr.ylecuyer.bicimapa.MyRestClient;
import fr.ylecuyer.bicimapa.R;
import fr.ylecuyer.bicimapa.adapters.ArrayFragmentPagerAdapter;
import fr.ylecuyer.bicimapa.fragments.site.SiteCommentsFragment_;
import fr.ylecuyer.bicimapa.fragments.site.SiteDetailsFragment;
import fr.ylecuyer.bicimapa.fragments.site.SiteDetailsFragment_;
import fr.ylecuyer.bicimapa.fragments.site.SitePicturesFragment_;
import fr.ylecuyer.bicimapa.models.SiteStats;

@OptionsMenu(R.menu.site_detail)
@EActivity(R.layout.activity_view_site)
public class ViewSiteActivity extends AppCompatActivity {

    @ViewById
    ViewPager pager;

    @ViewById(R.id.tabs)
    TabLayout tabLayout;

    @OptionsMenuItem(R.id.menu_item_share)
    MenuItem share;

    @Extra
    long siteId;

    @Extra
    LatLng latlng;

    @RestService
    MyRestClient myRestClient;
    private SiteStats stats;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @AfterViews
    void init() {

        ArrayList<Fragment> fragments = new ArrayList<Fragment>();
        ArrayList<String> names = new ArrayList<String>();

        fragments.add(new SiteDetailsFragment_().builder().arg("siteId", siteId).arg("latlng", latlng).build());
        names.add("Details");
        fragments.add(new SiteCommentsFragment_().builder().arg("siteId", siteId).build());
        names.add("Comments");
        fragments.add(new SitePicturesFragment_().builder().arg("siteId", siteId).build());
        names.add("Pictures");

        pager.setAdapter(new ArrayFragmentPagerAdapter(this.getSupportFragmentManager(), fragments, names));
        tabLayout.setupWithViewPager(pager);

        pager.requestTransparentRegion(pager); //http://void-developer.blogspot.com.co/2013/07/prevent-flickering-mapfragment-in.html


        downloadData();
    }

    @Background
    void downloadData() {
        Log.d("BICIMAPA", "download");

        stats = myRestClient.getSiteStats(siteId);
        Log.d("BICIMAPA", stats.toString());

        showData();
    }

    @UiThread
    void showData() {
        Log.d("BICIMAPA", "show");

        tabLayout.getTabAt(1).setCustomView(getCustomView("Comments", stats.getComments_count()));
        tabLayout.getTabAt(2).setCustomView(getCustomView("Pictures", stats.getPictures_count()));
    }

    private View getCustomView(String text, int badgeCount) {

        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View tabView = layoutInflater.inflate(R.layout.tab_view, tabLayout, false);
        TextView tv = (TextView) tabView.findViewById(R.id.textView2);
        tv.setText("" + badgeCount);

        if (badgeCount == 0) {
            tv.setVisibility(View.GONE);
        }

        tv = (TextView) tabView.findViewById(R.id.textView);
        tv.setText(text);


        return tabView;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        Log.d("BICIMAPA", "share=" + share);

        ShareActionProvider actionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(share);

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "https://bicimapa.com/sites/" + siteId);
        sendIntent.setType("text/plain");

        actionProvider.setShareIntent(sendIntent);

        return true;
    }
}
