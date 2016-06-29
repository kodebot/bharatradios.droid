package uk.co.qubitssolutions.bharatradios.app.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import uk.co.qubitssolutions.bharatradios.app.BharatRadiosApplication;
import uk.co.qubitssolutions.bharatradios.app.fragments.ContentListRadioFragment;
import uk.co.qubitssolutions.bharatradios.model.Language;

public class RadioListViewPagerAdapter extends FragmentPagerAdapter {

    private BharatRadiosApplication application;

    public RadioListViewPagerAdapter(FragmentManager fragmentManager, BharatRadiosApplication application) {
        super(fragmentManager);
        this.application = application;
    }

    @Override
    public Fragment getItem(int position) {
        Language selectedLanguage = this.application.getLanguageData().getFavLanguages().get(position);
        return new ContentListRadioFragment(selectedLanguage);
    }


    @Override
    public int getCount() {
        return this.application.getLanguageData().getFavLanguages().size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return this.application.getLanguageData().getFavLanguages().get(position).getName();
    }
}
