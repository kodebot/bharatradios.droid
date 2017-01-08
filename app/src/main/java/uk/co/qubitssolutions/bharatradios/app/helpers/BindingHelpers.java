package uk.co.qubitssolutions.bharatradios.app.helpers;

import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import uk.co.qubitssolutions.bharatradios.app.views.WaveVisualizerView;

public class BindingHelpers {
    @BindingAdapter("bind:updateVisualizer")
    public static void updateVisualizer(View view, byte[] visualizerData) {

        WaveVisualizerView visualizerView = (WaveVisualizerView) view;
        visualizerView.updateVisualizer(visualizerData);
    }

    @BindingAdapter({"bind:loadImage", "bind:errorInitialText"})
    public static void loadImage(View view, String url, String errorInitialText) {
        ImageView imageView = (ImageView) view;
        if(imageView == null){
            return;
        }

        if(errorInitialText == null || errorInitialText.equals("")){
            errorInitialText = "NA";
        }

        /**
         * Glide
         .with(myFragment)
         .load(url)
         .centerCrop()
         .placeholder(R.drawable.loading_spinner)
         .crossFade()
         .into(myImageView);

         *
         */
        Glide.with(view.getContext())

                .load(url)
                .error(ImageHelper.getAvatarImage(errorInitialText))
                //    .load("http://assets.zoftcdn.com/branding/lankasrifm/f_fm_200_b.png")
                .override(200,200)
                .into(imageView);
//        return ResourcesCompat.getDrawable(
//                application.getResources(),
//                avatarImages.get(getName().length() % avatarImages.size()),
//                null);
    }
}
