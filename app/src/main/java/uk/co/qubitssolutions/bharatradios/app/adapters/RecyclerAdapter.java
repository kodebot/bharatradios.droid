package uk.co.qubitssolutions.bharatradios.app.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import uk.co.qubitssolutions.bharatradios.R;
import uk.co.qubitssolutions.bharatradios.app.viewholders.RadioListViewHolder;
import uk.co.qubitssolutions.bharatradios.model.Radio;


public class RecyclerAdapter extends RecyclerView.Adapter<RadioListViewHolder> {

    private LayoutInflater layoutInflater;
    private List<Radio> radios;

    public RecyclerAdapter(Context context, List<Radio> data) {
        this.layoutInflater = LayoutInflater.from(context);
        this.radios = data;
    }

    @Override
    public RadioListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = this.layoutInflater.inflate(R.layout.list_item_radio, parent, false);
        return new RadioListViewHolder(view);
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
