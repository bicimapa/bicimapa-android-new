package fr.ylecuyer.bicimapa.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FragmentById;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;
import fr.ylecuyer.bicimapa.AdjustMarkerToDensityTransformation;
import fr.ylecuyer.bicimapa.MyFilters_;
import fr.ylecuyer.bicimapa.MyPrefs_;
import fr.ylecuyer.bicimapa.MyRestClient;
import fr.ylecuyer.bicimapa.PicassoMarker;
import fr.ylecuyer.bicimapa.R;
import fr.ylecuyer.bicimapa.models.Line;
import fr.ylecuyer.bicimapa.models.LineList;
import fr.ylecuyer.bicimapa.models.Profile;
import fr.ylecuyer.bicimapa.models.Site;
import fr.ylecuyer.bicimapa.models.SiteList;
import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;

@OptionsMenu(R.menu.main_activity)
@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, OnLocationUpdatedListener, GoogleMap.OnCameraChangeListener, GoogleMap.OnMarkerClickListener, NavigationView.OnNavigationItemSelectedListener {

    private static final int FILTER_RESULT = 1;
    private static final int LOGIN_RESULT = 2;

    @FragmentById(R.id.map)
    SupportMapFragment mapFragment;

    private GoogleMap map;

    @RestService
    MyRestClient myRestClient;
    private SiteList sitesResult;

    BidiMap<Long, Marker> markers = new DualHashBidiMap<Long, Marker>();
    HashMap<Long, PicassoMarker> picassoMarkers = new HashMap<Long, PicassoMarker>();

    BidiMap<Long, Polyline> lines = new DualHashBidiMap<Long, Polyline>();

    @Pref
    MyPrefs_ prefs;

    @Pref
    MyFilters_ filters;


    @ViewById
    DrawerLayout drawerLayout;

    @ViewById
    NavigationView navigationView;

    @ViewById
    Toolbar toolbar;

    @OptionsMenuItem(R.id.menu_item_share)
    MenuItem share;

    @OptionsMenuItem(R.id.menu_item_search)
    MenuItem search;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private String strFilters;
    private LatLngBounds bounds;
    private LineList linesResult;
    private AdjustMarkerToDensityTransformation transform;
    private ShareActionProvider actionProvider;
    private Profile profile;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFiltersFromPreferences();

        transform = new AdjustMarkerToDensityTransformation(getResources().getDisplayMetrics().density);

    }

    void manageSearch(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.d("BICIMAPA", "query=" + query);

/*
            GeoApiContext context = new GeoApiContext().setApiKey("YOUR_GOOGLE_MAP_API_KEY");

            String locality = getCurrentLocality();
            String country = getCurrentCountry();


            GeocodingResult[] results;
            try {
                GeocodingApiRequest request = GeocodingApi.newRequest(context)
                        .address(query);

                request.components(ComponentFilter.locality(locality), ComponentFilter.country(country));


                results = request.await();

                if (results.length > 0) {
                    Log.d("BICIMAPA", "res=" + results[0].formattedAddress);

                    LatLng latLng = new LatLng(results[0].geometry.location.lat, results[0].geometry.location.lng);

                    CameraUpdate camera = CameraUpdateFactory.newLatLngZoom(latLng, 16);
                    map.animateCamera(camera);

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

*/
        }
    }

    @AfterViews
    void manageUrls() {
        Uri uri = getIntent().getData();

        if (uri == null)
            return;

        String path = uri.getPath();

        Log.d("BICIMAPA", "uri=" + uri + "; path=" + path);


        Pattern pattern = Pattern.compile("sites/([0-9]+)$");
        Matcher matcher = pattern.matcher(path);

        if (matcher.find()) {
            long id = Long.parseLong(matcher.group(1));
            Log.d("BICIMAPA", "id=" + id);

            showSiteActivity(id);
        }

    }

    @AfterViews
    void init() {
        mapFragment.getMapAsync(this);

        toolbar.setTitle("Bicimapa");
        setSupportActionBar(toolbar);

        SmartLocation.with(this).location()
                .oneFix()
                .start(this);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerLayout.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        drawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

    }

    @AfterViews
    void initProfile() {

        MenuItem item = navigationView.getMenu().getItem(1);

        if (!isLoggedIn()) {
            item.setTitle("Log in");

            Log.d("VIEW", "navigationView="+navigationView);

            displayNavigationView(null, null);

            return;
        }

        String token = prefs.user_token().get();

        Log.d("BICIMAPA", "savedToken=" + token);

        item.setTitle("Log out");

        downloadProfile(token);
    }

    void displayNavigationView(String avatar_url, String username) {

        CircleImageView circle_image = (CircleImageView) navigationView.getHeaderView(0).findViewById(R.id.circle_image);
        TextView username_view = (TextView) navigationView.getHeaderView(0).findViewById(R.id.username);

        if (avatar_url != null) {
            circle_image.setVisibility(View.VISIBLE);
            Picasso.with(this).load(profile.getAvatar_url()).into(circle_image);
        }
        else {
            circle_image.setVisibility(View.INVISIBLE);
        }

        if (username != null) {
            username_view.setVisibility(View.VISIBLE);
            username_view.setText(profile.getLast_name() + " " + profile.getFirst_name());
        }
        else {
            username_view.setVisibility(View.INVISIBLE);
        }
    }

    @Background
    void downloadProfile(String token) {
        profile = myRestClient.getProfile(token);
        Log.d("BICIMAPA", "profile=" + profile.toString());
        showProfile();
    }

    @UiThread
    void showProfile() {
        displayNavigationView(profile.getAvatar_url(), profile.getLast_name() + " " + profile.getFirst_name());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        Log.d("LOCATION", "mapReady");

        Location lastLocation = SmartLocation.with(this).location().getLastLocation();
        if (lastLocation != null) {
            Log.d("LOCATION", "LastLocation="+lastLocation);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude()), 15));
        }

        map.setMyLocationEnabled(true);
        map.setOnCameraChangeListener(this);
        map.setOnMarkerClickListener(this);
    }

    @Override
    public void onLocationUpdated(Location location) {
        Log.d("LOCATION", "locationUpdated="+location);
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15));
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        bounds = map.getProjection().getVisibleRegion().latLngBounds;
        actionProvider.setShareIntent(createShareIntent());
        downloadSitesAndUpdateMarkersOnMap();
        downloadLinesAndUpdateLinesOnMap();
    }

    @Background
    void downloadLinesAndUpdateLinesOnMap() {

        Log.d("BICIMAPA", "downloadLinesAndUpdateLinesOnMap");

        double lat_ne = bounds.northeast.latitude;
        double lon_ne = bounds.northeast.longitude;
        double lat_sw = bounds.southwest.latitude;
        double lon_sw = bounds.southwest.longitude;

        linesResult = myRestClient.getLines(lat_ne, lon_ne, lat_sw, lon_sw, strFilters);
        updateLinesOnMap();
    }

    void setFiltersFromPreferences() {

        Log.d("BICIMAPA", "setFiltersFromPreferences");


        StringBuilder builder = new StringBuilder();

        Map<String, ?> ids = filters.getSharedPreferences().getAll();

        for (Map.Entry<String, ?> entry : ids.entrySet()) {

            if (entry.getKey().startsWith("SIT") || entry.getKey().startsWith("LIN")) {

                if (entry.getValue().equals(true)) {
                    builder.append("," + entry.getKey().substring(3));
                }

            }

        }

        Log.d("BICIMAPA", "builder=" + builder.toString());

        strFilters = builder.toString();

        if (strFilters.length() > 0) {
            strFilters = strFilters.substring(1);
        }
    }

    @Background
    void downloadSitesAndUpdateMarkersOnMap() {

        Log.d("BICIMAPA", "downloadSitesAndUpdateMarkersOnMap");

        double lat_ne = bounds.northeast.latitude;
        double lon_ne = bounds.northeast.longitude;
        double lat_sw = bounds.southwest.latitude;
        double lon_sw = bounds.southwest.longitude;

        sitesResult = myRestClient.getSites(lat_ne, lon_ne, lat_sw, lon_sw, strFilters);
        updateMarkersOnMap();
    }

    void clearAllMarkers() {

        Log.d("BICIMAPA", "clearAllMarkers");

        Iterator<Map.Entry<Long, Marker>> it = markers.entrySet().iterator();

        while (it.hasNext()) {
            Marker marker = it.next().getValue();
            marker.remove();
            it.remove();
        }
    }

    void clearAllLines() {

        Log.d("BICIMAPA", "clearAllLines");

        Iterator<Map.Entry<Long, Polyline>> it = lines.entrySet().iterator();

        while (it.hasNext()) {
            Polyline line = it.next().getValue();
            line.remove();
            it.remove();
        }
    }

    @UiThread
    void updateMarkersOnMap() {

        Log.d("BICIMAPA", "updateMarkersOnMap");

        Log.v("BICIMAPA", "markers.size=" + markers.size());


        if (markers.size() >= prefs.max_markers_before_GC().get()) {
            GC_sites();
        }


        Picasso picasso = Picasso.with(this);

        for (Site site : sitesResult) {

            if (!markers.containsKey(site.getId())) {

                MarkerOptions markerOptions = new MarkerOptions()
                        .position(new LatLng(site.getLatitude(), site.getLongitude()))
                        .icon(BitmapDescriptorFactory.fromBitmap(Bitmap.createBitmap(1, 1, Bitmap.Config.ALPHA_8)))
                        .title(site.getName());
                final Marker marker = map.addMarker(markerOptions);

                markers.put(site.getId(), marker);


                PicassoMarker picassoMarker = new PicassoMarker(marker);

                Log.d("CACHE", "loading=" + "http://bicimapa.com" + site.getIconUrl());


                picasso.load("http://bicimapa.com" + site.getIconUrl())
                        .transform(transform)
                        .into(picassoMarker);

                // TODO remove on GC_sites
                picassoMarkers.put(site.getId(), picassoMarker);

            }
        }
    }

    @UiThread
    void updateLinesOnMap() {

        Log.d("BICIMAPA", "updateLinesOnMap");

        Log.v("BICIMAPA", "lines.size=" + lines.size());


        if (lines.size() >= prefs.max_lines_before_GC().get()) {
            GC_lines();
        }

        for (Line line : linesResult) {

            if (!lines.containsKey(line.getId())) {

                PolylineOptions polylineOptions = new PolylineOptions()
                        .color(Color.parseColor(line.getColor()));

                for (ArrayList<Double> point : line.getPath()) {
                    polylineOptions.add(new LatLng(point.get(0), point.get(1)));
                }

                Polyline polyline = map.addPolyline(polylineOptions);
                lines.put(line.getId(), polyline);

            }
        }
    }

    private void GC_lines() {
        Log.v("BICIMAPA", "Lines before GC: " + lines.size());

        LatLngBounds bounds = map.getProjection().getVisibleRegion().latLngBounds;

        Iterator<Map.Entry<Long, Polyline>> it = lines.entrySet().iterator();

        while (it.hasNext()) {
            Polyline polyline = it.next().getValue();

            boolean is_inside = false;

            for (LatLng point : polyline.getPoints()) {
                if (bounds.contains(point)) {
                    is_inside = true;
                    break;
                }
            }

            if (!is_inside) {
                polyline.remove();
                it.remove();
            }
        }

        Log.v("BICIMAPA", "Lines after GC: " + lines.size());
    }

    private void GC_sites() {
        Log.v("BICIMAPA", "Markers before GC: " + markers.size());

        LatLngBounds bounds = map.getProjection().getVisibleRegion().latLngBounds;

        Iterator<Map.Entry<Long, Marker>> it = markers.entrySet().iterator();

        while (it.hasNext()) {
            Marker marker = it.next().getValue();
            if (!bounds.contains(marker.getPosition())) {
                marker.remove();
                it.remove();
            }
        }

        Log.v("BICIMAPA", "Markers after GC: " + markers.size());
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        showSiteActivity(markers.getKey(marker), marker.getPosition());
        return true;
    }

    private void showSiteActivity(long siteId) {
        showSiteActivity(siteId, null);
    }

    private void showSiteActivity(long siteId, LatLng position) {
        ViewSiteActivity_.intent(this).extra("siteId", siteId).extra("latlng", position).start();
    }

    Intent createShareIntent() {
        CameraPosition camera = map.getCameraPosition();
        double latitude = camera.target.latitude;
        double longitude = camera.target.longitude;
        int zoom = (int) camera.zoom;

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "https://bicimapa.com/?pos=" + latitude + "," + longitude + "&zoom=" + zoom + "&categories=" + strFilters + "&reports=0");
        sendIntent.setType("text/plain");

        return sendIntent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        actionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(share);


        actionProvider.setShareIntent(createShareIntent());


        //Search
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) search.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(true); // Do not iconify the widget; expand it by default

        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        manageSearch(intent);
    }

    @Click(R.id.fab)
    void showFilterActivity() {
        FilterActivity_.intent(this).startForResult(FILTER_RESULT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FILTER_RESULT) {
            setFiltersFromPreferences();
            actionProvider.setShareIntent(createShareIntent());
            clearAllMarkers();
            downloadSitesAndUpdateMarkersOnMap();
            clearAllLines();
            downloadLinesAndUpdateLinesOnMap();
        }

        if (requestCode == LOGIN_RESULT) {
            initProfile();
        }

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.navigation_item_login) {

            if (isLoggedIn()) {
                prefs.edit().user_token().put(null).apply();
                LoginManager.getInstance().logOut();
                initProfile();
            } else {
                LoginActivity_.intent(this).startForResult(LOGIN_RESULT);
            }
        }

        return false;
    }

    private boolean isLoggedIn() {

        String token = prefs.user_token().get();

        if (token == null || token.equalsIgnoreCase("")) {
            return false;
        }

        return true;
    }

}
