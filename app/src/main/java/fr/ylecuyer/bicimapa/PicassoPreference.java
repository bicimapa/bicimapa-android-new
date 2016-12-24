package fr.ylecuyer.bicimapa;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.preference.Preference;
import android.util.Log;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class PicassoPreference implements Target {

    Preference preference;

    public PicassoPreference(Preference preference) {
        this.preference = preference;
    }


    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        preference.setIcon(new BitmapDrawable(bitmap));
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {
        //NOP
    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {
        //NOP
    }
}