package uk.co.qubitssolutions.bharatradios.app.helpers;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.AppCompatDrawableManager;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import java.util.ArrayList;
import java.util.List;

import uk.co.qubitssolutions.bharatradios.R;
import uk.co.qubitssolutions.bharatradios.app.BharatRadiosApplication;

public class ImageHelper {
    //http://stackoverflow.com/questions/23122088/colored-boxed-with-letters-a-la-gmail
    private final static List<Integer> avatarImages;
    private final static Context context = BharatRadiosApplication.getContext();

    static {
        avatarImages = new ArrayList<>();
        avatarImages.add(R.drawable.radio_item_avatar_blue);
        avatarImages.add(R.drawable.radio_item_avatar_blue_grey);
        avatarImages.add(R.drawable.radio_item_avatar_brown);
        avatarImages.add(R.drawable.radio_item_avatar_dark_purple);
        avatarImages.add(R.drawable.radio_item_avatar_deep_orange);
        avatarImages.add(R.drawable.radio_item_avatar_green);
        avatarImages.add(R.drawable.radio_item_avatar_indigo);
        avatarImages.add(R.drawable.radio_item_avatar_orange);
        avatarImages.add(R.drawable.radio_item_avatar_pink);
        avatarImages.add(R.drawable.radio_item_avatar_purple);
        avatarImages.add(R.drawable.radio_item_avatar_red);
        avatarImages.add(R.drawable.radio_item_avatar_teal);
    }

    public static Drawable getAvatarImage(String name) {
        int drawableId = avatarImages.get(name.length() % avatarImages.size());
        Drawable drawable = getDrawable(drawableId);
        return writeOnDrawable(drawable, getInitial(name));
    }

    public static Bitmap drawableToBitmap(int drawableId) {
        return drawableToBitmap(getDrawable(drawableId));
    }

    public static Drawable getDrawable(int drawableId) {

        // must use this to deal with the fact that pre-lollipop can't handle vector drawable on its own
        return AppCompatDrawableManager.get().getDrawable(context,drawableId);

//        return ResourcesCompat.getDrawable(
//                context.getResources(),
//                drawableId,
//                null);
    }

//    public static int getResourceId(String resourceName, String resourceType){
//       return  context.getApplicationContext().getResources()
//                .getIdentifier(resourceName, resourceType, context.getApplicationContext().getPackageName());
//    }

    public static Bitmap drawableToBitmap(Drawable drawable) {

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    private static BitmapDrawable writeOnDrawable(Drawable drawable, String text) {

        Bitmap bm = drawableToBitmap(drawable);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setAntiAlias(true);
        paint.setTextSize(getPixels(TypedValue.COMPLEX_UNIT_SP, 25));

        Canvas canvas = new Canvas(bm);
        float distToTextCenterFromBaseline = ((paint.descent() + paint.ascent()) / 2);

        canvas.drawText(text, bm.getWidth() / 2, (bm.getHeight() / 2) - (distToTextCenterFromBaseline), paint);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(context.getResources(), bm);
        return bitmapDrawable;
    }


    private static String getInitial(String name) {
        String result = "";
        String[] textParts = name.split(" ");
        for (String textPart : textParts) {
            result = result + textPart.charAt(0);
            if (result.length() == 2) {
                break;
            }
        }
        return result;
    }

    private static int getPixels(int unit, float size) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return (int) TypedValue.applyDimension(unit, size, metrics);
    }
}
