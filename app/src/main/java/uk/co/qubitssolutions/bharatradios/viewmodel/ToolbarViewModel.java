package uk.co.qubitssolutions.bharatradios.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import uk.co.qubitssolutions.bharatradios.BR;

public class ToolbarViewModel extends BaseObservable {
    private boolean favoriteOnly;

    @Bindable
    public boolean isFavoriteOnly() {
        return favoriteOnly;
    }

    public void setFavoriteOnly(boolean favoriteOnly) {
        this.favoriteOnly = favoriteOnly;
        notifyPropertyChanged(BR.favoriteOnly);
    }
}
