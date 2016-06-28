package uk.co.qubitssolutions.bharatradios.viewmodel;


import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;
import android.widget.Toast;

import uk.co.qubitssolutions.bharatradios.BR;
import uk.co.qubitssolutions.bharatradios.model.Language;

public class LanguageViewModel extends BaseObservable {

    private String name;
    private boolean selected;

    public LanguageViewModel(Language language) {
        setName(language.getName());
        setSelected(false);
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public boolean getSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        notifyPropertyChanged(BR.selected);
    }

    public void onClick(View view) {
        this.setSelected(!this.getSelected());
        Toast.makeText(view.getContext(), "Clicked " + name, Toast.LENGTH_SHORT).show();

    }
}
