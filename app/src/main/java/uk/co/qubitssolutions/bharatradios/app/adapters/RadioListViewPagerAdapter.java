package uk.co.qubitssolutions.bharatradios.app.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import uk.co.qubitssolutions.bharatradios.app.BharatRadiosApplication;
import uk.co.qubitssolutions.bharatradios.app.fragments.ContentListRadioFragment;
import uk.co.qubitssolutions.bharatradios.model.Language;
import uk.co.qubitssolutions.bharatradios.services.data.favorite.FavoriteUtils;

public class RadioListViewPagerAdapter extends FragmentPagerAdapter {

    private BharatRadiosApplication application;

    public RadioListViewPagerAdapter(FragmentManager fragmentManager, BharatRadiosApplication application) {
        super(fragmentManager);
        this.application = application;
    }

    @Override
    public Fragment getItem(int position) {
        List<Language> favLanguages = FavoriteUtils.getFavLanguages(
                this.application,
                this.application.getLanguages());
        Language selectedLanguage = favLanguages.get(position);
        return new ContentListRadioFragment(selectedLanguage);
    }

    @Override
    public int getCount() {
        return FavoriteUtils.getFavLanguages(this.application, this.application.getLanguages())
                .size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return FavoriteUtils.getFavLanguages(this.application, this.application.getLanguages())
                .get(position)
                .getName();
    }
}
