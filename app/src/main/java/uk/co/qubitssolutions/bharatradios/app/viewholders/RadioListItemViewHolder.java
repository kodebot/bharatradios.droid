package uk.co.qubitssolutions.bharatradios.app.viewholders;

import android.app.Application;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import uk.co.qubitssolutions.bharatradios.BR;
import uk.co.qubitssolutions.bharatradios.app.BharatRadiosApplication;
import uk.co.qubitssolutions.bharatradios.model.Radio;
import uk.co.qubitssolutions.bharatradios.viewmodel.RadioViewModel;

public class RadioListItemViewHolder extends RecyclerView.ViewHolder {

    private ViewDataBinding viewDataBinding;
    private BharatRadiosApplication application;

    public RadioListItemViewHolder(View itemView, Application application) {
        super(itemView);
        viewDataBinding = DataBindingUtil.bind(itemView);
        this.application = (BharatRadiosApplication) application;
    }

    public void setData(Radio radio, boolean isSelected) {
        this.viewDataBinding.setVariable(BR.viewModel, new RadioViewModel(application, radio));
    }
}
