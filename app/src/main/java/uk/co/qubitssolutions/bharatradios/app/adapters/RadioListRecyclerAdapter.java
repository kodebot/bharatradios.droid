package uk.co.qubitssolutions.bharatradios.app.adapters;

import android.app.Application;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import uk.co.qubitssolutions.bharatradios.R;
import uk.co.qubitssolutions.bharatradios.app.BharatRadiosApplication;
import uk.co.qubitssolutions.bharatradios.app.viewholders.RadioListItemViewHolder;
import uk.co.qubitssolutions.bharatradios.model.Radio;
import uk.co.qubitssolutions.bharatradios.services.data.favorite.FavoriteUtils;

public class RadioListRecyclerAdapter extends RecyclerView.Adapter<RadioListItemViewHolder> {
    private BharatRadiosApplication application;
    private LayoutInflater layoutInflater;
    private List<Radio> radios;

    public RadioListRecyclerAdapter(
            Application application) {
        this.application = (BharatRadiosApplication) application;
        this.layoutInflater = LayoutInflater.from(application);

        if (((BharatRadiosApplication) application).getToolbarViewModel().isFavoriteOnly()) {
            List<Radio> radios = this.application.getRadios(this.application.getCurrentLanguageId());
            this.radios = FavoriteUtils.getFavRadios(application, radios);
        } else {
            this.radios = this.application.getRadios(this.application.getCurrentLanguageId());
        }
    }

    @Override
    public RadioListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        try {
            View view = this.layoutInflater.inflate(R.layout.list_item_radio, parent, false);
            return new RadioListItemViewHolder(view, application);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RadioListItemViewHolder holder, final int position) {
        holder.setData(radios.get(position), false);
    }

    @Override
    public void onViewAttachedToWindow(RadioListItemViewHolder holder) {
        super.onViewAttachedToWindow(holder);
       // this is where view is shown on the screen - set the highlight color for selected item
    }

    @Override
    public int getItemCount() {
        return radios.size();
    }

}
