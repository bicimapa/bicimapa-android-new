package fr.ylecuyer.bicimapa.fragments.site;



import android.content.Context;
import android.graphics.Bitmap;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;

import fr.ylecuyer.bicimapa.AdjustMarkerToDensityTransformation;
import fr.ylecuyer.bicimapa.MyRestClient;
import fr.ylecuyer.bicimapa.PicassoMarker;
import fr.ylecuyer.bicimapa.R;
import fr.ylecuyer.bicimapa.models.SiteDetail;

@EFragment(R.layout.fragment_site_details)
public class SiteDetailsFragment extends Fragment implements OnMapReadyCallback {

    @FragmentArg
    long siteId;

    @FragmentArg
    LatLng latlng;

    SupportMapFragment mapFragment;

    @ViewById
    TextView description;

    @RestService
    MyRestClient myRestClient;
    private SiteDetail siteDetail;
    private GoogleMap map;
    private PicassoMarker picassoMarker;
    private Marker marker;

    @AfterViews
    void init() {
        Log.d("BICIMAPA", "SiteID="+siteId);

        mapFragment = new SupportMapFragment();
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.replace(R.id.map, mapFragment).commit();

        mapFragment.getMapAsync(this);

        downloadData();
    }

    @Background
    void downloadData() {
        siteDetail = myRestClient.getSite(siteId);
        showData();
    }

    @UiThread
    void showData() {

        if (isDetached()) {
            return;
        }

        getActivity().setTitle(siteDetail.getName());

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(siteDetail.getLatitude(), siteDetail.getLongitude()), 17));

        MarkerOptions markerOptions = new MarkerOptions()
                .position(new LatLng(siteDetail.getLatitude(), siteDetail.getLongitude()))
                .icon(BitmapDescriptorFactory.fromBitmap(Bitmap.createBitmap(1, 1, Bitmap.Config.ALPHA_8)))
                .title(siteDetail.getName());

        marker = map.addMarker(markerOptions);
        picassoMarker = new PicassoMarker(marker);
        Log.d("BICIMAPA", "url="+"http://bicimapa.com/"+siteDetail.getCustom_icon_url());

        Picasso picasso = Picasso.with(getActivity().getBaseContext());
        picasso.load("http://bicimapa.com" + siteDetail.getIconUrl()).transform(new AdjustMarkerToDensityTransformation(getResources().getDisplayMetrics().density)).into(picassoMarker);

        description.setText(siteDetail.getDescription());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMyLocationEnabled(true);

        Log.d("BICIMAPA", "latlng="+latlng);

        if (latlng != null) {
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 17));
        }
    }
}
