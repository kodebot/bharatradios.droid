package uk.co.qubitssolutions.bharatradios.app.adapters;


import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.co.qubitssolutions.bharatradios.BR;
import uk.co.qubitssolutions.bharatradios.R;
import uk.co.qubitssolutions.bharatradios.app.BharatRadiosApplication;
import uk.co.qubitssolutions.bharatradios.viewmodel.LanguageViewModel;

public class LanguageListAdapter extends RecyclerView.Adapter<LanguageListAdapter.ViewHolder> {

    private BharatRadiosApplication application;

    public LanguageListAdapter(BharatRadiosApplication application) {
        this.application = application;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_language, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.viewDataBinding.setVariable(BR.viewModel, new LanguageViewModel(application.getLanguageData().getLanguages().get(position)));
        holder.viewDataBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return application.getLanguageData().getLanguages().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding viewDataBinding;

        public ViewHolder(View itemView) {
            super(itemView);
            viewDataBinding = DataBindingUtil.bind(itemView);
        }

        public ViewDataBinding getViewDataBinding() {
            return viewDataBinding;
        }
    }

}
