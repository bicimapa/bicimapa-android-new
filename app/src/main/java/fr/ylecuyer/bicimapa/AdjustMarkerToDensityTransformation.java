package fr.ylecuyer.bicimapa;

import android.graphics.Bitmap;
import android.util.Log;

import com.squareup.picasso.Transformation;

public class AdjustMarkerToDensityTransformation implements Transformation {

    private float density;

    public AdjustMarkerToDensityTransformation(float density) {
        Log.d("TRANSFORM", "density="+density);
        this.density = density;
    }

    @Override
    public Bitmap transform(Bitmap source) {

        Log.d("TRANSFORM", "density="+density);

        int width = (int)(source.getWidth() * density);
        int height = (int)(source.getHeight() * density);

        Bitmap result = Bitmap.createScaledBitmap(source, width, height, true);

        if (result != source) {
            // Same bitmap is returned if sizes are the same
            source.recycle();
        }

        return result;
    }

    @Override
    public String key() {
        return "AdjustMarkerToDensityTransformation"+density;
    }
}
