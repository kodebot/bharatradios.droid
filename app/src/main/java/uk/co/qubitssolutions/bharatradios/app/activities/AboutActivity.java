package uk.co.qubitssolutions.bharatradios.app.activities;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import uk.co.qubitssolutions.bharatradios.R;
import uk.co.qubitssolutions.bharatradios.viewmodel.AboutViewModel;
import uk.co.qubitssolutions.bharatradios.databinding.ActivityAboutBinding;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       ActivityAboutBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_about);
        binding.setVm(new AboutViewModel());
    }
}
