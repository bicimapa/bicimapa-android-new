package fr.ylecuyer.bicimapa.fragments.site;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.FragmentById;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;

import fr.ylecuyer.bicimapa.MyRestClient;
import fr.ylecuyer.bicimapa.R;
import fr.ylecuyer.bicimapa.models.SitePicturesDetail;

@EFragment(R.layout.fragment_site_pictures)
public class SitePicturesFragment extends Fragment implements OnStreetViewPanoramaReadyCallback {

    @FragmentArg
    long siteId;

    SupportStreetViewPanoramaFragment streetviewpanorama;

    @RestService
    MyRestClient myRestClient;

    private StreetViewPanorama panorama;
    private SitePicturesDetail sitePicturesDetail;

    @ViewById
    ImageView imageView;

    @ViewById
    ImageView imageView2;
    @ViewById

    ImageView imageView3;

    @AfterViews
    void init() {

        streetviewpanorama = new SupportStreetViewPanoramaFragment();
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.replace(R.id.streetviewpanorama, streetviewpanorama).commit();

        streetviewpanorama.getStreetViewPanoramaAsync(this);

        downloadData();
    }

    @Background
    void downloadData() {
        sitePicturesDetail = myRestClient.getSitePicturesDetail(siteId);
        showData();
    }

    @UiThread
    void showData() {

        if (isDetached()) {
            return;
        }

        Log.d("BICIMAPA", "data="+sitePicturesDetail);

        panorama.setPosition(new LatLng(sitePicturesDetail.getStreetview_lat(), sitePicturesDetail.getStreetview_lon()));


        String pictureUrl = sitePicturesDetail.getPictures().get(0);
        Log.d("BICIMAPA", "pictureUrl0="+pictureUrl);
        if (pictureUrl != null) {
            Picasso.with(getContext()).load("https://bicimapa.com"+pictureUrl).placeholder(R.drawable.progress_animation ).into(imageView);
        }

        pictureUrl = sitePicturesDetail.getPictures().get(1);
        Log.d("BICIMAPA", "pictureUrl1="+pictureUrl);
        if (pictureUrl != null) {
            Picasso.with(getContext()).load("https://bicimapa.com"+pictureUrl).placeholder(R.drawable.progress_animation ).into(imageView2);
        }

        pictureUrl = sitePicturesDetail.getPictures().get(2);
        Log.d("BICIMAPA", "pictureUrl2="+pictureUrl);
        if (pictureUrl != null) {
            Picasso.with(getContext()).load("https://bicimapa.com"+pictureUrl).placeholder(R.drawable.progress_animation ).into(imageView3);
        }
    }

    @Override
    public void onStreetViewPanoramaReady(StreetViewPanorama streetViewPanorama) {
        panorama = streetViewPanorama;
    }
}
