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


public class RadioListRecyclerAdapter extends RecyclerView.Adapter<RadioListItemViewHolder> {
    private BharatRadiosApplication application;
    private RadioListItemViewHolder.ActionListener viewHolderActionListener;
    private LayoutInflater layoutInflater;
    private List<Radio> radios;
    private int selectedItem = -1;

    public RadioListRecyclerAdapter(
            Application application,
            Context context,
            RadioListItemViewHolder.ActionListener viewHolderActionListener) {
        this.application = (BharatRadiosApplication) application;
        this.viewHolderActionListener = viewHolderActionListener;
        this.layoutInflater = LayoutInflater.from(context);

        if (((BharatRadiosApplication) application).getToolBarData().getIsFavoriteOnly()) {
            this.radios = ((BharatRadiosApplication) application)
                    .getRadioData().getFavoriteRadios();
        } else {
            this.radios = ((BharatRadiosApplication) application)
                    .getRadioData().getRadios();
        }

    }

    @Override
    public RadioListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = this.layoutInflater.inflate(R.layout.list_item_radio, parent, false);
        return new RadioListItemViewHolder(view, application, viewHolderActionListener);
    }

    @Override
    public void onBindViewHolder(RadioListItemViewHolder holder, final int position) {
        holder.setData(radios.get(position),
                new SelectionChangeListener(){
                    public void onChange(int position){
                        selectedItem = position;
                    }
                },
                position == selectedItem);
    }

    @Override
    public void onViewAttachedToWindow(RadioListItemViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        holder.updataSelection(selectedItem);
    }

    @Override
    public int getItemCount() {
        return radios.size();
    }

    public interface SelectionChangeListener{
        void onChange(int position);
    }
}
