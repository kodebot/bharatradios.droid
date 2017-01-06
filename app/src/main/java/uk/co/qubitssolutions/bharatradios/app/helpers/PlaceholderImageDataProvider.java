package uk.co.qubitssolutions.bharatradios.app.helpers;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;

import java.util.ArrayList;
import java.util.List;

import uk.co.qubitssolutions.bharatradios.R;
import uk.co.qubitssolutions.bharatradios.app.BharatRadiosApplication;

public class PlaceholderImageDataProvider {

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

    public static String getInitial(String name) {
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

    public static Drawable getAvatarImage(String name) {
        return ResourcesCompat.getDrawable(
                context.getResources(),
                avatarImages.get(name.length() % avatarImages.size()),
                null);
    }

}
