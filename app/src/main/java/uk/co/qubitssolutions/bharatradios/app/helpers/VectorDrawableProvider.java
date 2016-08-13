package uk.co.qubitssolutions.bharatradios.app.helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.AppCompatDrawableManager;

import uk.co.qubitssolutions.bharatradios.app.BharatRadiosApplication;

public class VectorDrawableProvider {

    public static Drawable getCompatibleDrawable(@DrawableRes int resourceId) {
        Context context = BharatRadiosApplication.getContext();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            context.getDrawable(resourceId);
        }
        return new BitmapDrawable(context.getResources(), vectorToBitmap(context, resourceId));
    }

    private static Bitmap vectorToBitmap(Context context, @DrawableRes int resourceId) {
        Drawable drawable = ContextCompat.getDrawable(context, resourceId);
        Bitmap b = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        drawable.setBounds(0, 0, c.getWidth(), c.getHeight());
        drawable.draw(c);
        return b;
    }

}
