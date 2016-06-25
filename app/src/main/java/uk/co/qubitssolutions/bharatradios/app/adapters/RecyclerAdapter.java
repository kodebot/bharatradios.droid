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
import uk.co.qubitssolutions.bharatradios.app.viewholders.RadioListViewHolder;
import uk.co.qubitssolutions.bharatradios.model.Radio;


public class RecyclerAdapter extends RecyclerView.Adapter<RadioListViewHolder> {
    private BharatRadiosApplication application;
    private Context context;
    private RadioListViewHolder.ActionListener viewHolderActionListener;
    private LayoutInflater layoutInflater;
    private List<Radio> radios;

    public RecyclerAdapter(
            Application application,
            Context context,
            RadioListViewHolder.ActionListener viewHolderActionListener) {
        this.application = (BharatRadiosApplication) application;
        this.context = context;
        this.viewHolderActionListener = viewHolderActionListener;
        this.layoutInflater = LayoutInflater.from(context);
        if (((BharatRadiosApplication) application).getToolBarData().getIsFavoriteOnly()) {
            this.radios = ((BharatRadiosApplication) application).getRadioData().getFavoriteRadios();
        } else {
            this.radios = ((BharatRadiosApplication) application).getRadioData().getRadios();
        }
    }

    @Override
    public RadioListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = this.layoutInflater.inflate(R.layout.list_item_radio, parent, false);
        return new RadioListViewHolder(view, application, viewHolderActionListener);
    }

    @Override
    public void onBindViewHolder(RadioListViewHolder holder, int position) {
        holder.setData(radios.get(position));
    }

    @Override
    public int getItemCount() {
        return radios.size();
    }
}
